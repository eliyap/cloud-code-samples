package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
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
    System.out.println(ticker);
    System.out.println(favorite);
    RestTemplate template = new RestTemplate();
    URI url = new URI(
      BackendURI.FAVORITE + "?ticker=" + ticker + "&favorite=" + favorite
    );
    template.postForLocation(url, null);
  }
}
