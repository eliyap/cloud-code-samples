package cloudcode.guestbook.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * defines the REST endpoints managed by the server.
 */
@RestController
public class BackendController {

  @Autowired
  private GoogleUserRepository googleUserRepository;

  @PostMapping("/googlesignup")
  public final UserResponse googleSignup(@RequestBody GoogleUser googleUser) {
    // save any new emails to database
    // don't bother updating idToken, it doesn't matter
    if (googleUserRepository.findByEmail(googleUser.getEmail()) == null) {
      googleUserRepository.save(googleUser);
    }
    return new UserResponse(true, null);
  }
 
  @PostMapping("/googleverify")
  public final UserResponse googleVerify(@RequestBody GoogleUser googleUser) {
    return new UserResponse(true, null);
  }
}
