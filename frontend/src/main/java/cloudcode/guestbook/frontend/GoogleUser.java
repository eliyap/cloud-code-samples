package cloudcode.guestbook.frontend;

public class GoogleUser {

  private String email;

  private String idToken;

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
}
