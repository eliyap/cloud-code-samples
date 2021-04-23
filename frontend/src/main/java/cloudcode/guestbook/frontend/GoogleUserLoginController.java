package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

/**
 * defines the REST endpoints managed by the server.
 */
@Controller
public class GoogleUserLoginController {

  @Autowired
  protected AuthenticationManager authenticationManager;

  @PostMapping(
    value = "/googlesignin",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  )
  public final String tokensignin(
    final GoogleUser googleUser,
    HttpServletRequest request
  )
    throws URISyntaxException {
    System.out.println(googleUser.getEmail());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", "application/json");
    UserResponse response = new RestTemplate()
    .postForObject(
        new URI(BackendURI.GOOGLE),
        new HttpEntity<GoogleUser>(googleUser, httpHeaders),
        UserResponse.class
      );

    // try manual auth
    // login(request, "foo", "bar");

    return "redirect:/";
  }
}
