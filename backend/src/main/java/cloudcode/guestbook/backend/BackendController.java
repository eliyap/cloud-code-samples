package cloudcode.guestbook.backend;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
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

  @Autowired
  private UserRepository userRepository;

  private static final JacksonFactory jacksonFactory = new JacksonFactory();
  private static final NetHttpTransport NET_HTTP_TRANSPORT = new NetHttpTransport();
  private static String CLIENT_ID =
    "974574715204-4ttrkcsmd7i4ltgmon64klu2a0uocjiu.apps.googleusercontent.com";
  private static GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
    NET_HTTP_TRANSPORT,
    jacksonFactory
  )
    .setAudience(Collections.singletonList(CLIENT_ID))
    .build();

  @PostMapping("/googlesignup")
  public final UserResponse googleSignup(@RequestBody GoogleUser googleUser) {
    // Prevent ambiguous email address lookup
    if (userRepository.findByEmail(googleUser.getEmail()) != null) {
      return new UserResponse(false, "Email already registered");
    }

    // save any new emails to database
    // don't bother updating idToken, it doesn't matter
    if (googleUserRepository.findByEmail(googleUser.getEmail()) == null) {
      googleUserRepository.save(googleUser);
    }
    return new UserResponse(true, null);
  }

  /**
   * Verification Code from: https://developers.google.com/identity/sign-in/web/backend-auth#using-a-google-api-client-library
   */
  @PostMapping("/googleverify")
  public final UserResponse googleVerify(@RequestBody GoogleUser googleUser) {
    try {
      if (verifier.verify(googleUser.getIdToken()) != null) {
        return new UserResponse(true, null);
      } else {
        return new UserResponse(false, "Invalid ID token.");
      }
    } catch (GeneralSecurityException | IOException e) {
      return new UserResponse(false, "Could not verfify ID Token");
    }
  }
}
