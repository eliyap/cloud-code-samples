package cloudcode.guestbook.backend;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class StockController {

  @Autowired
  private GoogleUserRepository googleUserRepository;

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/trade")
  public ResponseEntity<?> trade(
    @RequestParam String action,
    @RequestParam Integer quantity,
    @RequestParam String ticker,
    @RequestParam String email
  ) {
    GoogleUser googleUser = googleUserRepository.findByEmail(email);
    User user = userRepository.findByEmail(email);

    if (googleUser != null) {
      // TODO
    } else if (user != null) {
      // TODO

    } else {
      System.err.println("No User Found with Email: " + email);
    }
    
    // TODO
    return ResponseEntity.ok("");
  }
}
