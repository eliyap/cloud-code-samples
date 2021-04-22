package cloudcode.guestbook.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * represents a custom Mongo repository that stores Google User objects
 */
public interface GoogleUserRepository
  extends MongoRepository<GoogleUser, String> {
  GoogleUser findByEmail(String email);
}
