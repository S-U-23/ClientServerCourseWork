package com.mycompany.coursework1.resources.ModelResources;

import com.mycompany.coursework1.resources.Database.MockDatabase;
import com.mycompany.coursework1.resources.Exception.RoomNotEmptyException;
import com.mycompany.coursework1.resources.model.Room;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/rooms")
public class RoomResource {

    // GET /api/v1/rooms
    // returns all rooms
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return new ArrayList<>(MockDatabase.rooms.values());
    }

    // POST /api/v1/rooms
    // creates a new room and returns 201 Created + Location header
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(Room room, @Context UriInfo uriInfo) {

        // check request body exists
        if (room == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Room data is required\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // validate id
        if (room.getId() == null || room.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Room id is required\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // validate name
        if (room.getName() == null || room.getName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Room name is required\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // validate capacity
        if (room.getCapacity() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Room capacity must be greater than 0\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // stop duplicate ids
        if (MockDatabase.rooms.containsKey(room.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Room with this id already exists\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        // make sure sensor list is never null
        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }

        // save room
        MockDatabase.rooms.put(room.getId(), room);

        // build Location header
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(room.getId())
                .build();

        return Response.created(location)
                .entity(room)
                .build();
    }

    // GET /api/v1/rooms/{roomId}
    // returns a room by id
    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoomById(@PathParam("roomId") String roomId) {
        Room room = MockDatabase.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.ok(room).build();
    }

    // DELETE /api/v1/rooms/{roomId}
    // deletes a room only if it has no linked sensors
    @DELETE
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = MockDatabase.rooms.get(roomId);

        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Room not found\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (room.getSensorIds() != null && !room.getSensorIds().isEmpty()) {
            throw new RoomNotEmptyException(
                    "Room " + roomId + " cannot be deleted because sensors are still assigned to it"
            );
        }

        MockDatabase.rooms.remove(roomId);

        return Response.ok("{\"message\":\"Room deleted successfully\"}")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}