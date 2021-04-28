package cloudcode.guestbook.frontend;

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
  public final ResponseEntity<?> stock(@RequestParam String ticker) {
    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    System.out.println("Is Authenticated: " + auth.isAuthenticated());
    System.out.println("Auth: " + auth.getPrincipal());
    System.out.println("Det: " + auth.getDetails());

    try {
      RestTemplate template = new RestTemplate();
      StockDetails stockDetails = template.getForObject(
        baseURL + ticker + "/prices?token=" + token,
        StockDetails.class
      );
      return ResponseEntity.ok().body(stockDetails);
    } catch (HttpClientErrorException e) {
      if (e.getRawStatusCode() == 404) {
        System.out.println("404ed");
      } else {
        System.out.println(e.getCause());
        System.out.println(e.getMostSpecificCause());
      }
    }

    return ResponseEntity.ok().body("TEST");
  }
}
