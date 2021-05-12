package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class PortfolioController {

  @GetMapping("/portfolio")
  final String portfolio(Model model) throws URISyntaxException {
    RestTemplate template = new RestTemplate();
    String email = SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal()
      .toString();
    URI url = new URI(BackendURI.FAVORITES + "?email=" + email);
    
    return "portfolio";
  }
}
