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

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.envirocar.server.core.entities.Group;
import org.envirocar.server.core.entities.User;
import org.envirocar.server.core.entities.Users;
import org.envirocar.server.core.exception.BadRequestException;
import org.envirocar.server.core.exception.UserNotFoundException;
import org.envirocar.server.rest.MediaTypes;
import org.envirocar.server.rest.Schemas;
import org.envirocar.server.rest.UserReference;
import org.envirocar.server.rest.auth.Authenticated;
import org.envirocar.server.rest.schema.Schema;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann <autermann@uni-muenster.de>
 */
public class GroupMembersResource extends AbstractResource {
    public static final String MEMBER = "{member}";
    private final Group group;

    @Inject
    public GroupMembersResource(@Assisted Group group) {
        this.group = group;
    }

    @GET
    @Schema(response = Schemas.USERS)
    @Produces({MediaTypes.JSON, MediaTypes.XML_RDF, MediaTypes.TURTLE, MediaTypes.TURTLE_ALT})
    public Users get() throws BadRequestException {
        checkRights(getRights().canSeeMembersOf(group));
        return getGroupService().getGroupMembers(group, getPagination());
    }

    @POST
    @Authenticated
    @Consumes({MediaTypes.JSON})
    @Schema(request = Schemas.USER_REF)
    public void add(UserReference user) throws UserNotFoundException {
        User u = getUserService().getUser(user.getName());
        checkRights(getRights().isSelf(u) && getRights().canJoinGroup(group));
        getGroupService().addGroupMember(group, u);
    }

    @Path(MEMBER)
    public GroupMemberResource member(@PathParam("member") String username) throws UserNotFoundException {
        checkRights(getRights().canSeeMembersOf(group));
        User user = getGroupService().getGroupMember(group, username);
        checkRights(getRights().canSee(user));
        return getResourceFactory().createGroupMemberResource(group, user);
    }
}
