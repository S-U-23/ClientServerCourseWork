/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.coursework1.resources.Filter;

/**
 *
 * @author shqipdon
 */


import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider // Runs for every request/response
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName()); // Logger

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        LOGGER.info("--- Incoming Request ---"); // Start request log
        LOGGER.info("Method: " + requestContext.getMethod()); // HTTP method
        LOGGER.info("URI: " + requestContext.getUriInfo().getRequestUri().toString()); // Request URI
    }

    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        LOGGER.info("--- Outgoing Response ---"); // Start response log
        LOGGER.info("Status: " + responseContext.getStatus()); // HTTP status
    }
}