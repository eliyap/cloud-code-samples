package cloudcode.guestbook.frontend;

import java.net.URISyntaxException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class StockController {

  @GetMapping("/stock")
  public final ResponseEntity<?> stock(@RequestParam String ticker)
    throws URISyntaxException {
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    String email = auth.getPrincipal().toString();

    try {
      /**
       * For some reason, the backend has `isAuthenticated` backwards:
       * anonymous users return True, authenticated ones return False.
       * Just roll with it.
       */
      if (auth.isAuthenticated()) {
        StockDetails details = StockFetch.fetchDetails(ticker);
        StockMeta meta = StockFetch.fetchMeta(ticker);
        StockInfo info = new StockInfo(details, meta);
        return ResponseEntity.ok(info);
      } else {
        StockIEX iex = StockFetch.fetchIEX(ticker);
        StockMeta meta = StockFetch.fetchMeta(ticker);
        Boolean isFavorite = StockFetch.checkFavorite(email, ticker);
        iex.isFavorite = isFavorite;
        StockInfo info = new StockInfo(iex, meta);
        return ResponseEntity.ok(info);
      }
    } catch (HttpClientErrorException e) {
      int status_code = e.getRawStatusCode();
      if (status_code == 404) {
        return ResponseEntity
          .status(e.getRawStatusCode())
          .contentType(MediaType.TEXT_PLAIN)
          .body("No Such Stock");
      } else {
        return ResponseEntity
          .status(e.getRawStatusCode())
          .contentType(MediaType.TEXT_PLAIN)
          .body("Encountered an error");
      }
    }
  }
}
