package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FavoriteController {

  @PostMapping("/favorite")
  final void favorite(
    @RequestParam String ticker,
    @RequestParam boolean favorite
  )
    throws URISyntaxException {
    RestTemplate template = new RestTemplate();
    URI url = new URI(
      BackendURI.FAVORITE +
      "?ticker=" +
      ticker +
      "&favorite=" +
      favorite +
      "&email=" +
      SecurityContextHolder
        .getContext()
        .getAuthentication()
        .getPrincipal()
        .toString()
    );
    template.postForLocation(url, null);
  }
}
