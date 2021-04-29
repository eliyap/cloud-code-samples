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
public class FavoriteController {

  @GetMapping("/favorites")
  final String favorites(Model model) throws URISyntaxException {
    RestTemplate template = new RestTemplate();
    String email = SecurityContextHolder
      .getContext()
      .getAuthentication()
      .getPrincipal()
      .toString();
    URI url = new URI(BackendURI.FAVORITES + "?email=" + email);
    TickerList list = template.getForEntity(url, TickerList.class).getBody();
    ArrayList<StockInfo> infoList = new ArrayList<StockInfo>();
    for (String ticker : list) {
      StockIEX iex = StockFetch.fetchIEX(ticker);
      StockMeta meta = StockFetch.fetchMeta(ticker);
      StockInfo info = new StockInfo(iex, meta);
      infoList.add(info);
    }
    model.addAttribute("stocks", infoList);
    return "favorites";
  }
}
