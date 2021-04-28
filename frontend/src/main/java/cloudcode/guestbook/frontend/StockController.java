package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class StockController {

  private static String baseURL = "https://api.tiingo.com";
  private static String token = "548557c5bb89f21ff31b725cebc603b73396bb0c";

  @GetMapping("/stock")
  public final ResponseEntity<?> stock(@RequestParam String ticker)
    throws URISyntaxException {
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    System.out.println("Is Authenticated: " + auth.isAuthenticated());
    System.out.println("Auth: " + auth.getPrincipal());
    System.out.println("Det: " + auth.getDetails());

    try {
      /**
       * For some reason, the backend has `isAuthenticated` backwards:
       * anonymous users return True, authenticated ones return False.
       * Just roll with it.
       */
      if (auth.isAuthenticated()) {
        StockDetails details = fetchDetails(ticker);
        StockMeta meta = fetchMeta(ticker);
        StockInfo info = new StockInfo(details, meta);
        return ResponseEntity.ok(info);
      } else {
        StockIEX iex = fetchIEX(ticker);
        StockMeta meta = fetchMeta(ticker);
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

  // Fetches price information about the given ticker from the Tiingo API
  private StockDetails fetchDetails(String ticker)
    throws URISyntaxException, HttpClientErrorException {
    RestTemplate template = new RestTemplate();
    URI url = new URI(
      baseURL + "/tiingo/daily/" + ticker + "/prices?token=" + token
    );

    ResponseEntity<StockDetailsList> response = template.getForEntity(
      url,
      StockDetailsList.class
    );
    StockDetailsList list = response.getBody();

    if (list.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
    StockDetails details = list.get(0);
    return details;
  }

  // Fetches metadata about the given ticker from the Tiingo API
  private StockMeta fetchMeta(String ticker)
    throws URISyntaxException, HttpClientErrorException {
    RestTemplate template = new RestTemplate();
    URI url = new URI(baseURL + "/tiingo/daily/" + ticker + "?token=" + token);

    ResponseEntity<StockMeta> response = template.getForEntity(
      url,
      StockMeta.class
    );
    StockMeta meta = response.getBody();
    return meta;
  }

  // Fetches IEX for the given ticker from the Tiingo API
  private StockIEX fetchIEX(String ticker)
    throws URISyntaxException, HttpClientErrorException {
    RestTemplate template = new RestTemplate();
    URI url = new URI(baseURL + "/iex/" + ticker + "?token=" + token);

    ResponseEntity<StockIEXList> response = template.getForEntity(
      url,
      StockIEXList.class
    );
    StockIEXList list = response.getBody();
    if (list.isEmpty()) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    }
    StockIEX iex = list.get(0);
    return iex;
  }
}
