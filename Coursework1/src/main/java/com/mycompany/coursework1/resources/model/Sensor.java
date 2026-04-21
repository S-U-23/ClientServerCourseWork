/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework1.resources.model;

/**
 *
 * @author shqipdon
 */


public class Sensor {

    private String id; // Sensor ID
    private String type; // Sensor type
    private String status; // Status
    private double currentValue; // Current value
    private String roomId; // Linked room

    public Sensor() {
    }

    public Sensor(String id, String type, String status, String roomId) {
        this.id = id; // Set ID
        this.type = type; // Set type
        this.status = status; // Set status
        this.roomId = roomId; // Set room
    }

    public String getId() {
        return id; // Get ID
    }

    public void setId(String id) {
        this.id = id; // Set ID
    }

    public String getType() {
        return type; // Get type
    }

    public void setType(String type) {
        this.type = type; // Set type
    }

    public String getStatus() {
        return status; // Get status
    }

    public void setStatus(String status) {
        this.status = status; // Set status
    }

    public double getCurrentValue() {
        return currentValue; // Get value
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue; // Set value
    }

    public String getRoomId() {
        return roomId; // Get room ID
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId; // Set room ID
    }
}