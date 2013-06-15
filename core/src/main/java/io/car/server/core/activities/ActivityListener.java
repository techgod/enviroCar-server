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
package io.car.server.core.activities;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import io.car.server.core.dao.ActivityDao;
import io.car.server.core.event.ChangedGroupEvent;
import io.car.server.core.event.ChangedProfileEvent;
import io.car.server.core.event.CreatedGroupEvent;
import io.car.server.core.event.CreatedTrackEvent;
import io.car.server.core.event.DeletedGroupEvent;
import io.car.server.core.event.FriendedUserEvent;
import io.car.server.core.event.JoinedGroupEvent;
import io.car.server.core.event.LeftGroupEvent;
import io.car.server.core.event.UnfriendedUserEvent;

/**
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
@Singleton
public class ActivityListener {
    private final ActivityDao dao;
    private final ActivityFactory fac;

    @Inject
    public ActivityListener(ActivityDao dao,
                            ActivityFactory factory) {
        this.dao = dao;
        this.fac = factory;
    }

    @Subscribe
    public void onChangedGroupEvent(ChangedGroupEvent e) {
        dao.save(fac.createGroupActivity(ActivityType.CHANGED_GROUP,
                                         e.getUser(),
                                         e.getGroup()));
    }

    @Subscribe
    public void onChangedProfileEvent(ChangedProfileEvent e) {
        dao.save(fac.createActivity(ActivityType.CHANGED_PROFILE,
                                    e.getUser()));
    }

    @Subscribe
    public void onCreatedGroupEvent(CreatedGroupEvent e) {
        dao.save(fac.createGroupActivity(ActivityType.CREATED_GROUP,
                                         e.getUser(),
                                         e.getGroup()));
    }

    @Subscribe
    public void onCreatedTrackEvent(CreatedTrackEvent e) {
        dao.save(fac.createTrackActivity(ActivityType.CREATED_TRACK,
                                         e.getUser(),
                                         e.getTrack()));
    }

    @Subscribe
    public void onDeletedGroupEvent(DeletedGroupEvent e) {
        dao.save(fac.createGroupActivity(ActivityType.DELETED_GROUP,
                                         e.getUser(),
                                         e.getGroup()));
    }

    @Subscribe
    public void onFriendedUserEvent(FriendedUserEvent e) {
        dao.save(fac.createUserActivity(ActivityType.FRIENDED_USER,
                                        e.getUser(),
                                        e.getOther()));
    }

    @Subscribe
    public void onJoinedGroupEvent(JoinedGroupEvent e) {
        dao.save(fac.createGroupActivity(ActivityType.JOINED_GROUP,
                                         e.getUser(),
                                         e.getGroup()));
    }

    @Subscribe
    public void onLeftGroupEvent(LeftGroupEvent e) {
        dao.save(fac.createGroupActivity(ActivityType.LEFT_GROUP,
                                         e.getUser(),
                                         e.getGroup()));
    }

    @Subscribe
    public void onUnfriendedUserEvent(UnfriendedUserEvent e) {
        dao.save(fac.createUserActivity(ActivityType.UNFRIENDED_USER,
                                        e.getUser(),
                                        e.getOther()));
    }
}
