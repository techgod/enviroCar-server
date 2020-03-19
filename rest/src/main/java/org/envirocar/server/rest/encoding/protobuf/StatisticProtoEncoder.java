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
package org.envirocar.server.rest.encoding.protobuf;

import com.google.inject.Inject;
import org.envirocar.server.core.statistics.Statistic;
import org.envirocar.server.core.statistics.Statistics;
import org.envirocar.server.rest.StatisticProto;
import org.envirocar.server.rest.rights.AccessRights;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * TODO: Javadoc
 *
 * @author Benjamin Pross
 */
@Provider
@Singleton
public class StatisticProtoEncoder extends AbstractProtoTrackEncoder<Statistics> {

    @Inject
    public StatisticProtoEncoder() {
        super(Statistics.class);
    }

    @Override
    public StatisticProto.Statistics encodeProto(Statistics statistics, AccessRights rights, MediaType mt) {
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
}
