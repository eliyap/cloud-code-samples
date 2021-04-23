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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

  @Autowired
  protected GoogleUserAuthenticationProvider googleUserAuthenticationProvider;

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
    login(request, googleUser.getEmail(), googleUser.getIdToken());

    return "redirect:/";
  }

  // Manually log the User in.
  // Source: https://stackoverflow.com/a/8336233/12395667
  public void login(
    HttpServletRequest request,
    String userName,
    String password
  ) {
    // Authenticate the user
    Authentication authentication = googleUserAuthenticationProvider.authenticate(
      new UsernamePasswordAuthenticationToken(userName, password)
    );
    SecurityContext securityContext = SecurityContextHolder.getContext();
    securityContext.setAuthentication(authentication);

    // Create a new session and add the security context.
    HttpSession session = request.getSession(true);
    session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
  }
}
