package za.co.nedj.app.nedj.leaderboardapi.rest;

import io.swagger.annotations.Api;
import za.co.nedj.app.nedj.leaderboardapi.model.Leaderboard;
import za.co.nedj.app.nedj.leaderboardapi.model.Player;
import za.co.nedj.app.nedj.leaderboardapi.service.DataService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/")
@Api(value = "leaderboard API")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class LeaderboardRest {

    @EJB
    private DataService dataService;

    @GET
    @Path("/{leaderboardPlayerPlace}/leaderboard")
    public Response getPlayerByPlace(@PathParam("leaderboardPlayerPlace") Long leaderboardPlayerPlace) {
        Optional<Leaderboard> response = dataService.getPlayerByPlace(leaderboardPlayerPlace);
        return response
                .map(x -> Response.status(Response.Status.OK).entity(x).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

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

/**
    @POST
    @Path("/player")
    public Response addPlayerOnLeaderboard(Player player) {
        return Response.status(Response.Status.OK).entity(dataService.insertStudent(player)).build();
    }

    @PUT
    @Path("/player")
    public Response updatePlayerOnLeaderboard(Player player) {
        return Response.status(Response.Status.OK).entity(dataService.updateStudent(player)).build();
    }

    @DELETE
    @Path("/{playerId}/player")
    public Response deletePlayerOnLeaderboard(@PathParam("playerId") Long playerId) {
        return Response.status(Response.Status.OK).entity(dataService.deleteStudent(playerId)).build();
    }
    */


}
