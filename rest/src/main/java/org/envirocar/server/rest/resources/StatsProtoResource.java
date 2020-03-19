/*
 * Copyright (C) 2013-2020 The enviroCar project
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
package org.envirocar.server.rest.resources;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.envirocar.server.core.entities.Phenomenon;
import org.envirocar.server.core.entities.Sensor;
import org.envirocar.server.core.entities.Track;
import org.envirocar.server.core.entities.User;
import org.envirocar.server.core.exception.PhenomenonNotFoundException;
import org.envirocar.server.core.filter.StatisticsFilter;
import org.envirocar.server.core.statistics.Statistic;
import org.envirocar.server.core.statistics.Statistics;
import org.envirocar.server.rest.MediaTypes;
import org.envirocar.server.rest.Schemas;
import org.envirocar.server.rest.StatisticProto;
import org.envirocar.server.rest.schema.Schema;

import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public class StatsProtoResource extends AbstractResource {
    public static final String PHENOMENON = "{phen}";
    private final Track track;
    private final User user;
    private final Sensor sensor;

    @AssistedInject
    public StatsProtoResource() {
        this(null, null, null);
    }

    @AssistedInject
    public StatsProtoResource(@Assisted User user) {
        this(null, user, null);
    }

    @AssistedInject
    public StatsProtoResource(@Assisted @Nullable Track track) {
        this(track, null, null);
    }

    @AssistedInject
    public StatsProtoResource(@Assisted Sensor sensor) {
        this(null, null, sensor);
    }

    @AssistedInject
    public StatsProtoResource(@Nullable @Assisted Track track,
                              @Nullable @Assisted User user,
                              @Nullable @Assisted Sensor sensor) {
        this.track = track;
        this.user = user;
        this.sensor = sensor;
    }

    @GET
    @Produces({MediaTypes.APPLICATION_PROTOBUF})
    public StatisticProto.Statistics statistics() {
        System.out.println("In Stats Proto");
        Statistics statistics = getStatisticsService().getStatistics(new StatisticsFilter(user, track, sensor));
        //Making a statistics array.
        StatisticProto.Statistics.Builder statArr = StatisticProto.Statistics.newBuilder();

        for (Statistic statistic : statistics) {
            //Making a new statistic
            StatisticProto.Statistic.Builder stat = StatisticProto.Statistic.newBuilder();

            stat.setMin(statistic.getMin());
            stat.setAvg(statistic.getMean());
            stat.setMax(statistic.getMax());
            stat.setMeasurement(statistic.getMeasurements());
            stat.setTracks(statistic.getTracks());
            stat.setSensors(statistic.getSensors());

            //Adding new statistic to array.
            statArr.addStats(stat.build());
        }
        return statArr.build();
    }

    @Path(PHENOMENON)
    public StatisticResource statistics(@PathParam("phen") String phenomenon) throws PhenomenonNotFoundException {
        Phenomenon p = getDataService().getPhenomenonByName(phenomenon);
        return getResourceFactory().createStatisticResource(p, user, track, sensor);
    }

    public Track getTrack() {
        return track;
    }

    public User getUser() {
        return user;
    }

    public Sensor getSensor() {
        return sensor;
    }
}
