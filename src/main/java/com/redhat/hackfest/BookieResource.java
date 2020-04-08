package com.redhat.hackfest;

import com.redhat.hackfest.source.CompughterRatingsService;
import com.redhat.hackfest.source.dto.SimulationResult;
import com.redhat.hackfest.source.dto.Sport;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/api/v1/bookie")
public class BookieResource {

    private static Logger logger = Logger.getLogger(BookieResource.class);

    @ConfigProperty(name = "app.app-id")
    String appId;

    @ConfigProperty(name = "app.api-key")
    String apiKey;

    @Inject
    @RestClient
    CompughterRatingsService compughterRatingsService;

    @GET
    @Path("/{sport}/{homeTeam}/{awayTeam}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("sport") String sport, @PathParam("awayTeam") String homeTeam,
                        @PathParam("homeTeam") String awayTeam) {

        Sport sportDto = compughterRatingsService.getSport(appId, apiKey, sport);
        if (Objects.isNull(sportDto.sport)) {
            logger.warnv("Sport {0} NOT Found", sport);
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        SimulationResult simulationResultDto = compughterRatingsService
                .getSimulation(appId, apiKey, sport, homeTeam, awayTeam);

        return Response.ok(new MatchResult(simulationResultDto.team.get(0).name, simulationResultDto.team.get(1).name,
                simulationResultDto.team.get(0).score, simulationResultDto.team.get(1).score)).build();
    }

    @GET
    @Path("{sport}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeams(@PathParam("sport") String sport) {
        Sport sportDto = compughterRatingsService.getSport(appId, apiKey, sport);
        return Response.ok(sportDto.team).build();
    }
}