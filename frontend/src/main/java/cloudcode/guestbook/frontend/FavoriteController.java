package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/favorites")
  final String favorites(Model model) throws URISyntaxException {
    RestTemplate template = new RestTemplate();
    URI url = new URI(BackendURI.FAVORITES);
    TickerList list = template.getForEntity(url, TickerList.class).getBody();
    ArrayList<StockIEX> iexList = new ArrayList<StockIEX>();
    for (String ticker : list) {
      iexList.add(StockFetch.fetchIEX(ticker));
    }
    model.addAttribute("stocks", iexList);
    return "favorites";
  }
}
