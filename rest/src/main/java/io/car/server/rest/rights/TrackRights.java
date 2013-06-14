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
package io.car.server.rest.rights;

import io.car.server.core.entities.Track;

/**
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public interface TrackRights {
    boolean canSeeTracks();

    boolean canSee(Track track);

    boolean canSeeUserOf(Track track);

    boolean canSeeNameOf(Track track);

    boolean canSeeDescriptionOf(Track track);

    boolean canSeeSensorOf(Track track);

    boolean canSeeMeasurementsOf(Track track);

    boolean canSeeCreationTimeOf(Track track);

    boolean canSeeModificationTimeOf(Track track);

    boolean canModify(Track track);

    boolean canDelete(Track track);

    boolean canSeeStatisticsOf(Track track);
}
