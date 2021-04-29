package cloudcode.guestbook.frontend;

public class BackendURI {

  public static String SIGNUP = String.format(
    "http://%s/signup",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  public static String GOOGLE_SIGNUP = String.format(
    "http://%s/googlesignup",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  public static String GOOGLE_VERIFY = String.format(
    "http://%s/googleverify",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  public static String LOGIN = String.format(
    "http://%s/login",
    System.getenv("GUESTBOOK_API_ADDR")
  );

  public static String FAVORITE = String.format(
    "http://%s/favorite",
    System.getenv("GUESTBOOK_API_ADDR")
  );
  
  public static String FAVORITES = String.format(
    "http://%s/favorites",
    System.getenv("GUESTBOOK_API_ADDR")
  );
  
  public static String CHECK_FAVORITE = String.format(
    "http://%s/checkfavorite",
    System.getenv("GUESTBOOK_API_ADDR")
  );
  
  public static String TRADE = String.format(
    "http://%s/trade",
    System.getenv("GUESTBOOK_API_ADDR")
  );
}
