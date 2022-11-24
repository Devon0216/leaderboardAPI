package za.co.nedj.app.nedj.leaderboardapi.rest;

import io.swagger.annotations.Api;
import za.co.nedj.app.nedj.leaderboardapi.model.Player;
import za.co.nedj.app.nedj.leaderboardapi.service.DataService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("")
@Api(value = "player API")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class PlayerRest {

    @EJB
    private DataService dataService;

    @GET
    @Path("/{playerId}/player")
    public Response getPlayer(@PathParam("playerId") Long playerId) {
        Optional<Player> response = dataService.getPlayer(playerId);
        return response
                .map(x -> Response.status(Response.Status.OK).entity(x).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/player")
    public Response getAllPlayers() {
        return Response.status(Response.Status.OK).entity(dataService.getAllPlayers()).build();
    }

    @POST
    @Path("/player")
    public Response addPlayer(Player player) {
        return Response.status(Response.Status.OK).entity(dataService.insertPlayer(player)).build();
    }

    @PUT
    @Path("/player")
    public Response updatePlayer(Player player) {
        return Response.status(Response.Status.OK).entity(dataService.updatePlayer(player)).build();
    }

    @DELETE
    @Path("/{playerId}/player")
    public Response deletePlayer(@PathParam("playerId") Long playerId) {
        return Response.status(Response.Status.OK).entity(dataService.deletePlayer(playerId)).build();
    }


}
