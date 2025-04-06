package de.explore.grabby.booking.rest.entity;

import de.explore.grabby.booking.model.entity.Game;
import de.explore.grabby.booking.repository.entity.GameRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/games")
public class GameResource {

  @Inject
  GameRepository gameRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Game> getAllGames() {
    return gameRepository.getAllGames();
  }

  @RolesAllowed("${admin-role}")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response addGame(@Valid @NotNull Game game) {
    long id = gameRepository.persistGame(game);
    return Response.status(Response.Status.CREATED).entity(id).build();
  }

  @RolesAllowed("${admin-role}")
  @Path("/{id}")
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public void updateGame(@PathParam("id") long id, @Valid @NotNull Game game) {
    ensureGameExists(id);
    gameRepository.updateGame(id, game);
  }

  public void ensureGameExists(long id) {
    Optional<Game> byId = gameRepository.findByIdOptional(id);
    if (byId.isEmpty()) {
      throw new NotFoundException("Game with id " + id + " was not found");
    }
  }
}
