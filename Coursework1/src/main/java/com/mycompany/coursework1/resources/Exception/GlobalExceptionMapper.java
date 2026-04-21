/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework1.resources.Exception;

/**
 *
 * @author shqipdon
 */


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Throwable exception) {

        // log full error on the server side only
        LOGGER.log(Level.SEVERE, "Unexpected server error", exception);

        String json = "{"
                + "\"error\":\"Internal server error\","
                + "\"message\":\"Something went wrong. Please try again later.\""
                + "}";

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}