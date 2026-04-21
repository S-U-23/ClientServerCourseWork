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

@Provider // Handles this exception
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException> {

    @Override
    public Response toResponse(RoomNotEmptyException exception) {

        // JSON error response
        String json = "{"
                + "\"error\":\"Room conflict\","
                + "\"message\":\"" + exception.getMessage() + "\""
                + "}";

        return Response.status(Response.Status.CONFLICT) // 409 conflict
                .entity(json) // send JSON
                .type(MediaType.APPLICATION_JSON) // JSON type
                .build();
    }
}