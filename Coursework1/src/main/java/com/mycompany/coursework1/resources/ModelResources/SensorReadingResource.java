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
import com.mycompany.coursework1.resources.Exception.SensorUnavailableException;
import com.mycompany.coursework1.resources.model.Sensor;
import com.mycompany.coursework1.resources.model.SensorReading;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SensorReadingResource {

    private String sensorId;

    // constructor receives the sensor id from the sub-resource locator
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    // GET /api/v1/sensors/{sensorId}/readings
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllReadings() {

        Sensor sensor = MockDatabase.sensors.get(sensorId);

        // check sensor exists
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Sensor not found\"}")
                    .build();
        }

        List<SensorReading> readings = MockDatabase.sensorReadings.get(sensorId);

        // if no list exists yet, create empty list
        if (readings == null) {
            readings = new ArrayList<>();
            MockDatabase.sensorReadings.put(sensorId, readings);
        }

        return Response.ok(readings).build();
    }

    // POST /api/v1/sensors/{sensorId}/readings
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading) {

        Sensor sensor = MockDatabase.sensors.get(sensorId);

        // check sensor exists
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Sensor not found\"}")
                    .build();
        }

        // Part 5.3: sensor in maintenance cannot accept readings
        if (sensor.getStatus() != null && sensor.getStatus().equalsIgnoreCase("MAINTENANCE")) {
            throw new SensorUnavailableException(
                    "Sensor " + sensorId + " is in MAINTENANCE and cannot accept readings"
            );
        }

        // basic validation
        if (reading == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Reading data is required\"}")
                    .build();
        }

        if (reading.getId() == null || reading.getId().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"Reading id is required\"}")
                    .build();
        }

        // if timestamp not sent, generate current time
        if (reading.getTimestamp() == 0) {
            reading.setTimestamp(System.currentTimeMillis());
        }

        List<SensorReading> readings = MockDatabase.sensorReadings.get(sensorId);

        if (readings == null) {
            readings = new ArrayList<>();
            MockDatabase.sensorReadings.put(sensorId, readings);
        }

        // stop duplicate reading ids for the same sensor
        for (SensorReading existingReading : readings) {
            if (existingReading.getId().equals(reading.getId())) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\":\"Reading with this id already exists for this sensor\"}")
                        .build();
            }
        }

        // add reading to this sensor's history
        readings.add(reading);

        // required coursework side effect:
        // update parent sensor currentValue with latest reading value
        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED)
                .entity(reading)
                .build();
    }
}