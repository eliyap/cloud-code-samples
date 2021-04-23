package cloudcode.guestbook.frontend;

public class BackendURI {

  public static String SIGNUP = String.format(
    "http://%s/signup",
    System.getenv("GUESTBOOK_API_ADDR")
  );
  
  public static String GOOGLE = String.format(
    "http://%s/googlesignin",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  public static String LOGIN = String.format(
    "http://%s/login",
    System.getenv("GUESTBOOK_API_ADDR")
  );
}
