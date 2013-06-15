/*
 * Copyright (C) 2013  Christian Autermann, Jan Alexander Wirwahn,
 *                     Arne De Wall, Dustin Demuth, Saqib Rasheed
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.car.server.rest.encoding;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.vividsolutions.jts.geom.Geometry;

import io.car.server.core.entities.Measurement;
import io.car.server.core.entities.MeasurementValue;
import io.car.server.core.entities.Sensor;
import io.car.server.core.entities.User;
import io.car.server.core.util.GeoJSONConstants;
import io.car.server.rest.JSONConstants;
import io.car.server.rest.MediaTypes;
import io.car.server.rest.rights.AccessRights;

/**
 * @author Arne de Wall <a.dewall@52north.org>
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MeasurementEncoder extends AbstractEntityEncoder<Measurement> {
    private final EntityEncoder<Geometry> geometryEncoder;
    private final EntityEncoder<User> userProvider;
    private final EntityEncoder<Sensor> sensorProvider;

    @Inject
    public MeasurementEncoder(EntityEncoder<Geometry> geometryEncoder,
                              EntityEncoder<User> userProvider,
                              EntityEncoder<Sensor> sensorProvider) {
        super(Measurement.class);
        this.geometryEncoder = geometryEncoder;
        this.userProvider = userProvider;
        this.sensorProvider = sensorProvider;
    }

    @Override
    public ObjectNode encode(Measurement t, AccessRights rights,
                             MediaType mediaType) {
        ObjectNode measurement = getJsonFactory().objectNode();
        measurement.put(GeoJSONConstants.TYPE_KEY,
                        GeoJSONConstants.FEATURE_TYPE);
        if (t.hasGeometry() && rights.canSeeGeometryOf(t)) {
            measurement.put(JSONConstants.GEOMETRY_KEY,
                            geometryEncoder
                    .encode(t.getGeometry(), rights, mediaType));
        }

        ObjectNode properties = measurement
                .putObject(GeoJSONConstants.PROPERTIES_KEY);
        if (t.hasIdentifier()) {
            properties.put(JSONConstants.IDENTIFIER_KEY, t.getIdentifier());
        }
        if (t.hasTime() && rights.canSeeTimeOf(t)) {
            properties.put(JSONConstants.TIME_KEY,
                           getDateTimeFormat().print(t.getTime()));
        }

        if (!mediaType.equals(MediaTypes.TRACK_TYPE)) {
            if (t.hasSensor() && rights.canSeeSensorOf(t)) {
                properties.put(JSONConstants.SENSOR_KEY,
                               sensorProvider
                        .encode(t.getSensor(), rights, mediaType));
            }
            if (t.hasUser() && rights.canSeeUserOf(t)) {
                properties.put(JSONConstants.USER_KEY, userProvider
                        .encode(t.getUser(), rights, mediaType));
            }
            if (t.hasModificationTime() && rights.canSeeModificationTimeOf(t)) {
                properties.put(JSONConstants.MODIFIED_KEY, getDateTimeFormat()
                        .print(t.getModificationTime()));
            }
            if (t.hasCreationTime() && rights.canSeeCreationTimeOf(t)) {
                properties.put(JSONConstants.CREATED_KEY,
                               getDateTimeFormat().print(t.getCreationTime()));
            }
        }
        if (mediaType.equals(MediaTypes.MEASUREMENT_TYPE) ||
            mediaType.equals(MediaTypes.MEASUREMENTS_TYPE) ||
            mediaType.equals(MediaTypes.TRACK_TYPE)) {
            if (rights.canSeeValuesOf(t)) {
                ObjectNode values = properties
                        .putObject(JSONConstants.PHENOMENONS_KEY);

                for (MeasurementValue mv : t.getValues()) {
                    if (mv.hasPhenomenon() && mv.hasValue()) {
                        ObjectNode phenomenon = values.objectNode();
                        phenomenon.putPOJO(JSONConstants.VALUE_KEY,
                                           mv.getValue());
                        values.put(mv.getPhenomenon().getName(), phenomenon);
                        if (mv.getPhenomenon().hasUnit()) {
                            phenomenon.put(JSONConstants.UNIT_KEY,
                                           mv.getPhenomenon().getUnit());
                        }
                    }
                }
            }
        }
        return measurement;
    }
}
