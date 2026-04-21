/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework1.resources.Database;

/**
 *
 * @author shqipdon
 */

import com.mycompany.coursework1.resources.model.Room;
import com.mycompany.coursework1.resources.model.Sensor;
import com.mycompany.coursework1.resources.model.SensorReading;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockDatabase {

    // stores all rooms
    public static Map<String, Room> rooms = new HashMap<>();

    // stores all sensors
    public static Map<String, Sensor> sensors = new HashMap<>();

    // stores readings for each sensor
    public static Map<String, List<SensorReading>> sensorReadings = new HashMap<>();

    static {
        // ----- ROOMS -----
        Room room1 = new Room("CS-LAB-101", "Computer Science Lab", 40);
        Room room2 = new Room("ENG-LAB-202", "Engineering Lab", 25);

        rooms.put(room1.getId(), room1);
        rooms.put(room2.getId(), room2);

        // ----- SENSORS -----
        Sensor sensor1 = new Sensor();
        sensor1.setId("CO2-001");
        sensor1.setType("CO2");
        sensor1.setStatus("ACTIVE");
        sensor1.setCurrentValue(650.0);
        sensor1.setRoomId("CS-LAB-101");

        Sensor sensor2 = new Sensor();
        sensor2.setId("TEMP-001");
        sensor2.setType("Temperature");
        sensor2.setStatus("ACTIVE");
        sensor2.setCurrentValue(21.5);
        sensor2.setRoomId("CS-LAB-101");

        Sensor sensor3 = new Sensor();
        sensor3.setId("OCC-001");
        sensor3.setType("Occupancy");
        sensor3.setStatus("MAINTENANCE");
        sensor3.setCurrentValue(10.0);
        sensor3.setRoomId("ENG-LAB-202");

        sensors.put(sensor1.getId(), sensor1);
        sensors.put(sensor2.getId(), sensor2);
        sensors.put(sensor3.getId(), sensor3);

        // link sensors to rooms
        room1.getSensorIds().add(sensor1.getId());
        room1.getSensorIds().add(sensor2.getId());
        room2.getSensorIds().add(sensor3.getId());

        // ----- SENSOR READINGS -----
        List<SensorReading> co2Readings = new ArrayList<>();

        SensorReading reading1 = new SensorReading();
        reading1.setId("READ-001");
        reading1.setTimestamp(System.currentTimeMillis() - 60000);
        reading1.setValue(640.0);

        SensorReading reading2 = new SensorReading();
        reading2.setId("READ-002");
        reading2.setTimestamp(System.currentTimeMillis());
        reading2.setValue(650.0);

        co2Readings.add(reading1);
        co2Readings.add(reading2);

        sensorReadings.put("CO2-001", co2Readings);

        // empty reading lists for the others
        sensorReadings.put("TEMP-001", new ArrayList<>());
        sensorReadings.put("OCC-001", new ArrayList<>());
    }
}