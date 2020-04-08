package com.redhat.hackfest.source;

import com.redhat.hackfest.source.dto.SimulationResult;
import com.redhat.hackfest.source.dto.Sport;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api/v1")
@RegisterRestClient(configKey = "compughter-ratings")
public interface CompughterRatingsService {

    @GET
    @Path("/teams/{sport}/true")
    @Produces(MediaType.APPLICATION_JSON)
    Sport getSport(@HeaderParam("app-id")
                           String appId,
                   @HeaderParam("api-key")
                           String apikey,
                   @PathParam("sport") String sport);

    @GET
    @Path("/simulation/{sport}/{homeTeam}/{awayTeam}")
    @Produces(MediaType.APPLICATION_JSON)
    SimulationResult getSimulation(
            @HeaderParam("app-id")
                    String appId,
            @HeaderParam("api-key")
                    String apikey,
            @PathParam("sport") String sport,
            @PathParam("homeTeam") String homeTeam,
            @PathParam("awayTeam") String awayTeam);
}
