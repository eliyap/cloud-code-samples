package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * defines the REST endpoints managed by the server.
 */
@Controller
public class FrontendController {

  @Autowired
  protected AuthenticationManager authenticationManager;

  /**
   * endpoint for the login page
   * @return the name of the html template to render
   */
  @GetMapping("/login")
  public final String login() {
    return "login";
  }

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
