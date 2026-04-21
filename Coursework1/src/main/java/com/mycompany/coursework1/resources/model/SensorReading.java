/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework1.resources.model;

/**
 *
 * @author shqipdon
 */


public class SensorReading {

    private String id; // Reading ID
    private long timestamp; // Time (ms)
    private double value; // Reading value

    public SensorReading() {
    }

    public SensorReading(String id, long timestamp, double value) {
        this.id = id; // Set ID
        this.timestamp = timestamp; // Set time
        this.value = value; // Set value
    }

    public String getId() {
        return id; // Get ID
    }

    public void setId(String id) {
        this.id = id; // Set ID
    }

    public long getTimestamp() {
        return timestamp; // Get time
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp; // Set time
    }

    public double getValue() {
        return value; // Get value
    }

    public void setValue(double value) {
        this.value = value; // Set value
    }
}