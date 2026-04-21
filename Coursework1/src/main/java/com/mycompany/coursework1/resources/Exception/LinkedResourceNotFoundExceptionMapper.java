/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework1.resources.Exception;

/**
 *
 * @author shqipdon
 */


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider // Handles this exception automatically
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException> {

    @Override
    public Response toResponse(LinkedResourceNotFoundException exception) {

        // Simple JSON error message
        String json = "{"
                + "\"error\":\"Linked resource not found\","
                + "\"message\":\"" + exception.getMessage() + "\""
                + "}";

        return Response.status(422) // Invalid linked resource
                .entity(json) // Send JSON response
                .type(MediaType.APPLICATION_JSON) // Set type to JSON
                .build();
    }
}