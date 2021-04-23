package cloudcode.guestbook.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
