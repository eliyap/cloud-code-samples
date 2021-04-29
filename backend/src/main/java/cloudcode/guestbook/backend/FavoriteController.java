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

  @PostMapping("/favorite")
  final void favorite(
    @RequestParam String ticker,
    @RequestParam boolean favorite,
    @RequestParam String email
  )
    throws URISyntaxException {
    System.out.println(ticker);
    System.out.println(favorite);
    System.out.println("Emial: " + email);

    GoogleUser googleUser = googleUserRepository.findByEmail(email);
    User user = userRepository.findByEmail(email);

    if (googleUser != null) {
      googleUser.getFavorites().add(ticker);
      googleUserRepository.save(googleUser);
    } else if (user != null) {
      user.getFavorites().add(ticker);
      userRepository.save(user);
    } else {
      System.err.println("No User Found with Email: " + email);
    }
  }

  @GetMapping("/favorites")
  final ResponseEntity<?> favorites(
    @RequestParam String email,
    @RequestParam String ticker
  ) {
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
}
