/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework1.resources.ModelResources;

/**
 *
 * @author shqipdon
 */



import com.mycompany.coursework1.resources.Database.MockDatabase;
import com.mycompany.coursework1.resources.Exception.LinkedResourceNotFoundException;

import com.mycompany.coursework1.resources.model.Room;
import com.mycompany.coursework1.resources.model.Sensor;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sensors")
public class SensorResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getAllSensors(@QueryParam("type") String type) {

        List<Sensor> sensors = new ArrayList<>(MockDatabase.sensors.values());

        // if no type is given, return all sensors
        if (type == null || type.trim().isEmpty()) {
            return sensors;
        }

        // otherwise only return matching sensor types
        List<Sensor> filteredSensors = new ArrayList<>();
        for (Sensor sensor : sensors) {
            if (sensor.getType() != null && sensor.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(sensor);
            }
        }

        return filteredSensors;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensor(Sensor sensor) {

        // check sensor object exists
        if (sensor == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Sensor data is required\"}")
                    .build();
        }

        // check sensor id exists
        if (sensor.getId() == null || sensor.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Sensor id is required\"}")
                    .build();
        }

        // check roomId exists in request
        if (sensor.getRoomId() == null || sensor.getRoomId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"roomId is required\"}")
                    .build();
        }

        // stop duplicate sensor ids
        if (MockDatabase.sensors.containsKey(sensor.getId())) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"Sensor with this id already exists\"}")
                    .build();
        }

        // coursework requirement: roomId must already exist
        Room room = MockDatabase.rooms.get(sensor.getRoomId());
        if (room == null) {
            throw new LinkedResourceNotFoundException(
                    "Cannot create sensor because room " + sensor.getRoomId() + " does not exist"
            );
        }

        // add sensor to mock database
        MockDatabase.sensors.put(sensor.getId(), sensor);

        // create an empty reading history list for this sensor
        MockDatabase.sensorReadings.put(sensor.getId(), new ArrayList<>());

        // also link the sensor id into the room
        if (room.getSensorIds() == null) {
            room.setSensorIds(new ArrayList<>());
        }
        room.getSensorIds().add(sensor.getId());

        return Response.status(Response.Status.CREATED)
                .entity(sensor)
                .build();
    }

    // sub-resource locator for /sensors/{sensorId}/readings
    @Path("/{sensorId}/readings")
    public SensorReadingResource getSensorReadingResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}