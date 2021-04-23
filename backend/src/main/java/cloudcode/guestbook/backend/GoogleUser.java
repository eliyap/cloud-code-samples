package cloudcode.guestbook.backend;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "googleuser")
public class GoogleUser {

  @Indexed(
    unique = true,
    direction = IndexDirection.DESCENDING,
    dropDups = true
  )
  private String email;

  private String id_token;

  public GoogleUser() {}

  public GoogleUser(String email, String id_token) {
    this.email = email;
    this.id_token = id_token;
  }

  public final String getEmail() {
    return email;
  }

  public final void setEmail(String email) {
    this.email = email;
  }

  public final String getIdToken() {
    return id_token;
  }

  public final void setIdToken(String id_token) {
    this.id_token = id_token;
  }
}
