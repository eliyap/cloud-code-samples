package cloudcode.guestbook.backend;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
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
    @RequestParam String email,
    @RequestParam Integer price
  ) {
    GoogleUser googleUser = googleUserRepository.findByEmail(email);
    User user = userRepository.findByEmail(email);

    Integer dxn = (action.toUpperCase() == "BUY") ? -1 : +1;
    Integer cost = price * quantity * dxn;

    if (googleUser != null) {
      Integer newBalance = googleUser.getBalance() + cost;
      if (newBalance < 0) {
        return ResponseEntity
          .status(HttpStatus.PAYMENT_REQUIRED)
          .body("Insufficient Balance");
      } else {
        googleUser.setBalance(newBalance);
        googleUserRepository.save(googleUser);
        return ResponseEntity.ok("Done");
      }
    } else if (user != null) {
      Integer newBalance = user.getBalance() + cost;
      if (newBalance < 0) {
        return ResponseEntity
          .status(HttpStatus.PAYMENT_REQUIRED)
          .body("Insufficient Balance");
      } else {
        user.setBalance(newBalance);
        userRepository.save(user);
        return ResponseEntity.ok("Done");
      }
    } else {
      System.err.println("No User Found with Email: " + email);
      return ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body("No User Found with Email");
    }
  }
}
