package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
public class UserLoginController {

  @Autowired
  protected AuthenticationManager authenticationManager;
  
  @Autowired
  protected UserAuthenticationProvider userAuthenticationProvider;

  /**
   * endpoint for handling form submission
   * @param User holds date entered in html form
   * @return redirects back to home page
   * @throws URISyntaxException when there is an issue with the backend uri
   */
  @PostMapping("/signup")
  public RedirectView post(
    final User user,
    RedirectAttributes attributes,
    HttpServletRequest request
  )
    throws URISyntaxException {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Content-Type", "application/json");
    UserResponse response = new RestTemplate()
    .postForObject(
        new URI(BackendURI.SIGNUP),
        new HttpEntity<User>(user, httpHeaders),
        UserResponse.class
      );

    if (response.success) {
      login(request, user.getUsername(), user.getPassword());
      return new RedirectView("/");
    } else {
      attributes.addFlashAttribute(
        "errorMessage",
        "Error: " + response.errorMessage
      );
      return new RedirectView("/login");
    }
  }

  // Failure URL for Login Page
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

  // Manually log the User in.
  // Source: https://stackoverflow.com/a/8336233/12395667
  public void login(
    HttpServletRequest request,
    String userName,
    String password
  ) {
    // Authenticate the user
    Authentication authentication = userAuthenticationProvider.authenticate(
      new UsernamePasswordAuthenticationToken(userName, password)
    );
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    // Create a new session and add the security context.
    HttpSession session = request.getSession(true);
    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
  }
}
