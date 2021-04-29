package cloudcode.guestbook.backend;

import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
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
      //TODO: thing
      googleUserRepository.save(googleUser);
    } else if (user != null) {
      //TODO: thing
      userRepository.save(user);
    } else {
      System.err.println("No User Found with Email: " + email);
    }
  }
}
