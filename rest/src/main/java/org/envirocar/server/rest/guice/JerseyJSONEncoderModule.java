/*
 * Copyright (C) 2013-2018 The enviroCar project
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
package org.envirocar.server.rest.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.vividsolutions.jts.geom.Geometry;
import org.envirocar.server.core.entities.*;
import org.envirocar.server.core.statistics.Statistic;
import org.envirocar.server.core.statistics.Statistics;
import org.envirocar.server.rest.encoding.JSONEntityEncoder;
import org.envirocar.server.rest.encoding.json.*;
import org.envirocar.server.rest.util.ErrorMessage;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public class JerseyJSONEncoderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(JsonNodeMessageBodyWriter.class).in(Scopes.SINGLETON);
        bind(SensorJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Sensor>>() {
        }).to(SensorJSONEncoder.class);
        bind(SensorsJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Sensors>>() {
        }).to(SensorsJSONEncoder.class);
        bind(TrackJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Track>>() {
        }).to(TrackJSONEncoder.class);
        bind(TracksJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Tracks>>() {
        }).to(TracksJSONEncoder.class);
        bind(MeasurementJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Measurement>>() {
        }).to(MeasurementJSONEncoder.class);
        bind(MeasurementsJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Measurements>>() {
        }).to(MeasurementsJSONEncoder.class);
        bind(PhenomenonJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Phenomenon>>() {
        }).to(PhenomenonJSONEncoder.class);
        bind(PhenomenonsJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Phenomenons>>() {
        }).to(PhenomenonsJSONEncoder.class);
        bind(StatisticJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Statistic>>() {
        }).to(StatisticJSONEncoder.class);
        bind(StatisticsJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Statistics>>() {
        }).to(StatisticsJSONEncoder.class);
        bind(GeometryJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<Geometry>>() {
        }).to(GeometryJSONEncoder.class);
        bind(TermsOfUseInstanceJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<TermsOfUseInstance>>() {
        }).to(TermsOfUseInstanceJSONEncoder.class);
        bind(TermsOfUseJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<TermsOfUse>>() {
        }).to(TermsOfUseJSONEncoder.class);
        bind(PrivacyStatementJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<PrivacyStatement>>() {
        }).to(PrivacyStatementJSONEncoder.class);
        bind(PrivacyStatementsJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<PrivacyStatements>>() {
        }).to(PrivacyStatementsJSONEncoder.class);
        bind(ErrorMessageJSONEncoder.class).in(Scopes.SINGLETON);
        bind(new TypeLiteral<JSONEntityEncoder<ErrorMessage>>() {
        }).to(ErrorMessageJSONEncoder.class);
    }
}
