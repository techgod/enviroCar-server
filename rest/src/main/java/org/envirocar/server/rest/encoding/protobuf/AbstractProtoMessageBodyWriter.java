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

import org.envirocar.server.rest.GreetingProtos;
import org.envirocar.server.rest.MediaTypes;
import org.envirocar.server.rest.StatisticProto;
import org.envirocar.server.rest.encoding.CSVTrackEncoder;
import org.envirocar.server.rest.encoding.ProtoTrackEncoder;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * TODO: Javadoc
 *
 * @author Benjamin Pross
 */
@Produces(MediaTypes.APPLICATION_PROTOBUF)
public abstract class AbstractProtoMessageBodyWriter<T> implements
        MessageBodyWriter<T>, ProtoTrackEncoder<T> {

    private final Class<T> classType;

    public AbstractProtoMessageBodyWriter(Class<T> classType) {
        this.classType = classType;
    }

    public abstract StatisticProto.Statistics encodeProtoStatistic(T t, MediaType mt);

    @Override
    public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaTyp) {
        return -1;
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
                               MediaType mediaType) {
        return StatisticProto.Statistics.class.isAssignableFrom(type) ||
                StatisticProto.Statistic.class.isAssignableFrom(type) &&
                mediaType.isCompatible(MediaTypes.APPLICATION_PROTOBUF_TYPE);
    }

    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> h,
                        OutputStream out) throws IOException {
        //IOUtils.copy(encodeCSV(t, mediaType), out);
        if (t instanceof StatisticProto.Statistic) {
            StatisticProto.Statistic g = (StatisticProto.Statistic)t;
            g.writeTo(out);
        }
        else if (t instanceof StatisticProto.Statistics) {
            StatisticProto.Statistics g = (StatisticProto.Statistics)t;
            g.writeTo(out);
        }
    }

}
