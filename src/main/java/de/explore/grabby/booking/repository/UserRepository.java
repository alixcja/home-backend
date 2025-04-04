package de.explore.grabby.booking.repository;

import de.explore.grabby.booking.model.Users;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Optional;

@ApplicationScoped
public class UserRepository implements PanacheRepository<Users> {
  public Users getUserByIdentifier(String identifier) {
    return getUserByIdentifierIfExists(identifier).orElseThrow(NotFoundException::new);
  }

  @Transactional
  public Users createUser(JsonWebToken jwt) {
    Users users = new Users(jwt.getSubject(), jwt.getClaim(Claims.family_name), jwt.getClaim(Claims.given_name));
    persist(users);
    return users;
  }

  private Optional<Users> getUserByIdentifierIfExists(String identifier) {
    return find("id", identifier).singleResultOptional();
  }

  public Users getMyUser(JsonWebToken jwt) {
    return getUserByIdentifierIfExists(jwt.getSubject()).orElseGet(() -> createUser(jwt));
  }
}