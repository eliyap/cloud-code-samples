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

  private static String baseURL = "https://api.tiingo.com/tiingo/daily/";
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
      StockDetails details = fetchDetails(ticker);
      return ResponseEntity.ok(details);
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
    URI url = new URI(baseURL + ticker + "/prices?token=" + token);

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
    URI url = new URI(baseURL + ticker + "?token=" + token);

    ResponseEntity<StockMeta> response = template.getForEntity(
      url,
      StockMeta.class
    );
    StockMeta meta = response.getBody();
    return meta;
  }
}
