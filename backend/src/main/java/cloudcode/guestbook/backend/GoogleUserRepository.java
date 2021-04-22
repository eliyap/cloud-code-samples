package cloudcode.guestbook.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * represents a custom Mongo repository that stores User objects
 */
public interface GoogleUserRepository extends MongoRepository<User, String> {
  User findByEmail(String email);
}
