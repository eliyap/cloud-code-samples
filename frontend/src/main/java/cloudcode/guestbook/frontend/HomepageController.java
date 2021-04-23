package cloudcode.guestbook.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * defines the REST endpoints managed by the server.
 */
@Controller
public class HomepageController {

  /**
   * endpoint for the landing page
   * @param model defines model for html template
   * @return the name of the html template to render
   */
  @GetMapping("/")
  public final String main(final Model model) {
    return "home";
  }
}
