package cloudcode.guestbook.frontend;

public class GoogleUser {

  private String email;

  private String id_token;

  public GoogleUser() {}

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
