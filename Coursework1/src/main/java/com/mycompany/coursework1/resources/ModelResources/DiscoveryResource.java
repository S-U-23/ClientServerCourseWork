/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework1.resources.ModelResources;

/**
 *
 * @author shqipdon
 */



import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("") // Root endpoint
public class DiscoveryResource {

    @GET // GET request
    @Produces(MediaType.APPLICATION_JSON) // Returns JSON
    public Map<String, Object> getDiscovery() {

        Map<String, Object> response = new HashMap<>(); // Main response

        response.put("version", "v1"); // API version
        response.put("adminContact", "admin@smartcampus.com"); // Contact

        Map<String, String> resources = new HashMap<>(); // Endpoints
        resources.put("rooms", "/api/v1/rooms"); // Rooms path
        resources.put("sensors", "/api/v1/sensors"); // Sensors path

        response.put("resources", resources); // Add resources

        return response; // Return JSON
    }
}