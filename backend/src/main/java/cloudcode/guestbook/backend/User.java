package cloudcode.guestbook.backend;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * defines the data associated with a single guest book entry
 */
@Document(collection = "user")
public class User {

  @Id
  private String email;

  @Indexed(
    unique = true,
    direction = IndexDirection.DESCENDING,
    dropDups = true
  )
  private String username;

  private String password;

  private ArrayList<String> favorites;

  public User() {}

  public User(String email, String username, String password) {
    this.email = email;
    this.username = username;
    this.password = password;
  }

  public final String getEmail() {
    return email;
  }

  public final void setEmail(String email) {
    this.email = email;
  }

  public final String getUsername() {
    return username;
  }

  public final void setUsername(String username) {
    this.username = username;
  }

  public final String getPassword() {
    return password;
  }

  public final void setPassword(String password) {
    this.password = password;
  }

  public final ArrayList<String> getFavorites() {
    return favorites;
  }

  public final void setFavorites(ArrayList<String> favorites) {
    this.favorites = favorites;
  }
}
