package cloudcode.guestbook.backend;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoriteController {

  @Autowired
  private GoogleUserRepository googleUserRepository;

  @Autowired
  private UserRepository userRepository;

  /**
   * Allows user with specified email to add or remove the
   * stock with specified ticker from their favorites list
   * @param ticker
   * @param favorite
   * @param email
   * @throws URISyntaxException
   */
  @PostMapping("/favorite")
  final void favorite(
    @RequestParam String ticker,
    @RequestParam boolean favorite,
    @RequestParam String email
  )
    throws URISyntaxException {
    ticker = ticker.toUpperCase();

    GoogleUser googleUser = googleUserRepository.findByEmail(email);
    User user = userRepository.findByEmail(email);

    if (googleUser != null) {
      if (favorite) {
        googleUser.getFavorites().add(ticker);
      } else {
        googleUser.getFavorites().remove(ticker);
      }
      googleUserRepository.save(googleUser);
    } else if (user != null) {
      if (favorite) {
        user.getFavorites().add(ticker);
      } else {
        user.getFavorites().remove(ticker);
      }
      userRepository.save(user);
    } else {
      System.err.println("No User Found with Email: " + email);
    }
  }

  /**
   * Checks whether the user with the given email
   * listed the given ticker as a favorite
   * @param email
   * @param ticker
   * @return true if user did set this as a ticker
   */
  @GetMapping("/checkfavorite")
  final ResponseEntity<?> checkfavorite(
    @RequestParam String email,
    @RequestParam String ticker
  ) {
    ticker = ticker.toUpperCase();

    GoogleUser googleUser = googleUserRepository.findByEmail(email);
    User user = userRepository.findByEmail(email);

    if (googleUser != null) {
      return ResponseEntity.ok(googleUser.getFavorites().contains(ticker));
    } else if (user != null) {
      return ResponseEntity.ok(user.getFavorites().contains(ticker));
    } else {
      System.err.println("(Favorites) No User Found with Email: " + email);
      return ResponseEntity.status(403).body("No User Found with Email");
    }
  }

  /**
   * Returns a list of the ticker symbols of the user's favorite stocks
   * @param email
   * @param ticker
   * @return
   */
  @GetMapping("/favorites")
  final ResponseEntity<?> favorites(
    @RequestParam String email,
    @RequestParam String ticker
  ) {
    GoogleUser googleUser = googleUserRepository.findByEmail(email);
    User user = userRepository.findByEmail(email);
    if (googleUser != null) {
      return ResponseEntity.ok(googleUser.getFavorites());
    } else if (user != null) {
      return ResponseEntity.ok(user.getFavorites());
    } else {
      System.err.println("(Favorites) No User Found with Email: " + email);
      return ResponseEntity.status(403).body("No User Found with Email");
    }
  }
}
