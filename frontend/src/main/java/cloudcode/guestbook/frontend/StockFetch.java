package cloudcode.guestbook.frontend;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class StockFetch {

  private static String baseURL = "https://api.tiingo.com";
  private static String token = "548557c5bb89f21ff31b725cebc603b73396bb0c";

  // Fetches price information about the given ticker from the Tiingo API
  public static StockDetails fetchDetails(String ticker)
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
  public static StockMeta fetchMeta(String ticker)
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
  public static StockIEX fetchIEX(String ticker)
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

    // do some post processing
    iex.calculate();

    return iex;
  }

  public static Boolean checkFavorite(String email, String ticker)
    throws URISyntaxException, HttpClientErrorException {
    RestTemplate template = new RestTemplate();
    URI url = new URI(
      BackendURI.CHEcK_FAVORITE + "?email=" + email + "&ticker=" + ticker
    );
    ResponseEntity<Boolean> reponse = template.getForEntity(url, Boolean.class);
    return reponse.getBody();
  }
}
