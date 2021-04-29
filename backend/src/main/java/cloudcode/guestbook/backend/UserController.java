package cloudcode.guestbook.backend;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * defines the REST endpoints managed by the server.
 */
@RestController
public class UserController {

  @Autowired
  private GoogleUserRepository googleUserRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CustomUserDetailsService userService;

  @PostMapping("/login")
  public final UserResponse login(@RequestBody User user) {
    User userByName = userRepository.findByUsername(user.getUsername());
    User match = userRepository.findByUsernameAndPassword(
      user.getUsername(),
      user.getPassword()
    );

    if (userByName == null) {
      return new UserResponse(false, "No Account with Username");
    } else if (match == null) {
      return new UserResponse(false, "Incorrect Password");
    } else {
      return new UserResponse(true, null);
    }
  }

  @PostMapping("/signup")
  public final UserResponse signup(@RequestBody User user) {
    Boolean emailExists =
      userService.findUserByEmail(user.getEmail()) != null ||
      googleUserRepository.findByEmail(user.getEmail()) != null;
    Boolean usernameExists =
      userService.findUserByUsername(user.getUsername()) != null;
    if (emailExists) {
      return new UserResponse(false, "Email already registered");
    } else if (usernameExists) {
      return new UserResponse(false, "Username already registered");
    } else {
      user.setFavorites(new ArrayList<String>());
      
      // Paper money of 50k USD
      user.setBalance(5000000);
      
      userRepository.save(user);
      return new UserResponse(true, null);
    }
  }
}
