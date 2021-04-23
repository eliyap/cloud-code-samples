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
import org.springframework.security.core.AuthenticationException;
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

  /**
   * endpoint for the login page
   * @return the name of the html template to render
   */
  @GetMapping("/login")
  public final String login() {
    return "login";
  }

  /**
   * endpoint for handling form submission
   * @param User holds date entered in html form
   * @return redirects back to home page
   * @throws URISyntaxException when there is an issue with the backend uri
   */
  @PostMapping("/signup")
  public RedirectView post(final User user, RedirectAttributes attributes)
    throws URISyntaxException {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", "application/json");
    UserResponse response = new RestTemplate()
    .postForObject(
        new URI(BackendURI.SIGNUP),
        new HttpEntity<User>(user, httpHeaders),
        UserResponse.class
      );
    return autoLoginRedirect(user, attributes, response);
  }

  // Creates a model to have the user automatically "log in"
  private RedirectView autoLoginRedirect(
    final User user,
    RedirectAttributes attributes,
    UserResponse response
  ) {
    RedirectView view = new RedirectView("/login");
    if (response.success) {
      attributes.addFlashAttribute("username", user.getUsername());
      attributes.addFlashAttribute("password", user.getPassword());
      attributes.addFlashAttribute("autologin", "autologin");
    } else {
      attributes.addFlashAttribute(
        "errorMessage",
        "Error: " + response.errorMessage
      );
    }
    return view;
  }

  @GetMapping("/login-error")
  public String login(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession(false);
    String errorMessage = null;
    if (session != null) {
      AuthenticationException ex = (AuthenticationException) session.getAttribute(
        WebAttributes.AUTHENTICATION_EXCEPTION
      );
      if (ex != null) {
        errorMessage = ex.getMessage();
      }
    }
    model.addAttribute("errorMessage", errorMessage);
    return "login";
  }

  @PostMapping(
    value = "/googlesignin",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  )
  public final String tokensignin(final GoogleUser googleUser)
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
    SecurityContextHolder
      .getContext()
      .setAuthentication(new UsernamePasswordAuthenticationToken("foo", "bar"));

    return "redirect:/";
  }

  @Autowired
  protected AuthenticationManager authenticationManager;
}
