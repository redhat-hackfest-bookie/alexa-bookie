package com.redhat.hackfest.event;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sports")
@RegisterRestClient(configKey = "prediction")
public interface PredictionEventClient {
    @GET
    @Path("/update/{sport}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response postEvent(@PathParam("sport") String sport);
}
