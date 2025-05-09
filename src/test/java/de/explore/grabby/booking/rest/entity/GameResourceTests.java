package de.explore.grabby.booking.rest.entity;

import de.explore.grabby.booking.model.entity.Game;
import de.explore.grabby.booking.repository.entity.GameRepository;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

@TestHTTPEndpoint(GameResource.class)
@QuarkusTest
@TestSecurity(authorizationEnabled = false)
class GameResourceTests {

  private Game game1;
  private final GameRepository gameRepository;

  @Inject
  public GameResourceTests(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  @BeforeEach
  @Transactional
  void setUp() {
    game1 = new Game("Let's dance!", "This is a fun dance game", "Nintendo Switch");
    Game game2 = new Game("Mario Kart Deluxe 8", "Race against your friends and family!", "Nintendo Switch");

    gameRepository.persist(game1, game2);
  }

  @AfterEach
  @Transactional
  void tearDown() {
    gameRepository.deleteAll();
  }

  @Test
  void shouldReturnAListWithTwoGames() {
    given()
            .when()
            .get()
            .then()
            .statusCode(SC_OK)
            .body("size()", is(2));
  }

  @Test
  void shouldCreateNewGame() {
    Game newGame = new Game("Super Smash Bros", "Beat everyone", "Nintendo Switch");

    given()
            .when()
            .contentType("application/json")
            .body(newGame)
            .post()
            .then()
            .statusCode(SC_CREATED);
  }

  @Test
  void shouldFailCreatingNewGame() {
    given()
            .when()
            .contentType("application/json")
            .post()
            .then()
            .statusCode(SC_BAD_REQUEST);
  }

  @Test
  void shouldUpdateGame() {
    Game updatedGame = new Game(game1.getName(), "updated description", game1.getConsoleType());

    given()
            .when()
            .contentType("application/json")
            .pathParams("id", game1.getId())
            .body(updatedGame)
            .put("/{id}")
            .then()
            .statusCode(SC_NO_CONTENT);
  }

  @Test
  void shouldFailUpdatingGame() {
    given()
            .when()
            .contentType("application/json")
            .pathParams("id", "non-existing-game")
            .body(new Game())
            .put("/{id}")
            .then()
            .statusCode(SC_NOT_FOUND);
  }
}