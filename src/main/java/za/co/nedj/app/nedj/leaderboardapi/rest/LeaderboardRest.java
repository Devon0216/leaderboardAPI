package za.co.nedj.app.nedj.leaderboardapi.rest;

import io.swagger.annotations.Api;
import za.co.nedj.app.nedj.leaderboardapi.model.Leaderboard;
import za.co.nedj.app.nedj.leaderboardapi.service.DataService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("")
@Api(value = "leaderboard API")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class LeaderboardRest {

    @EJB
    private DataService dataService;


    @GET
    @Path("/{leaderboardPlayerId}/leaderboard")
    public Response getPlayerById(@PathParam("leaderboardPlayerId") Long leaderboardPlayerId) {
        Optional<Leaderboard> response = dataService.getPlayerById(leaderboardPlayerId);
        return response
                .map(x -> Response.status(Response.Status.OK).entity(x).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/leaderboard")
    public Response getTopPlayers() {
        return Response.status(Response.Status.OK).entity(dataService.getTopPlayers()).build();
    }


    @POST
    @Path("/leaderboard")
    public Response addPlayerOnLeaderboard(Leaderboard leaderboardPlayer) {
        return Response.status(Response.Status.OK).entity(dataService.insertLeaderboardPlayer(leaderboardPlayer)).build();
    }

    @PUT
    @Path("/leaderboard")
    public Response updatePlayerOnLeaderboard(Leaderboard leaderboardPlayer) {
        return Response.status(Response.Status.OK).entity(dataService.updateLeaderboardPlayer(leaderboardPlayer)).build();
    }

    @DELETE
    @Path("/{leaderboardPlayerId}/leaderboard")
    public Response deletePlayerOnLeaderboard(@PathParam("leaderboardPlayerId") Long leaderboardPlayerId) {
        return Response.status(Response.Status.OK).entity(dataService.deleteLeaderboardPlayer(leaderboardPlayerId)).build();
    }



}
