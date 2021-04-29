package cloudcode.guestbook.backend;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "googleuser")
public class GoogleUser {

  @Id
  private String email;

  private String idToken;

  private ArrayList<String> favorites;

  // User's paper money, in cents
  private Integer balance;

  public GoogleUser() {}

  public GoogleUser(String email, String idToken) {
    this.email = email;
    this.idToken = idToken;
  }

  public final String getEmail() {
    return email;
  }

  public final void setEmail(String email) {
    this.email = email;
  }

  public final String getIdToken() {
    return idToken;
  }

  public final void setIdToken(String idToken) {
    this.idToken = idToken;
  }

  public final ArrayList<String> getFavorites() {
    return favorites;
  }

  public final void setFavorites(ArrayList<String> favorites) {
    this.favorites = favorites;
  }

  public final Integer getBalance() {
    return balance;
  }

  public final void setBalance(Integer balance) {
    this.balance = balance;
  }
}
