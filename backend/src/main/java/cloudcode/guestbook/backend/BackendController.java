package cloudcode.guestbook.backend;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * defines the REST endpoints managed by the server.
 */
@RestController
public class BackendController {

  @Autowired
  private MessageRepository repository;

  /**
   * endpoint for retrieving all guest book entries stored in database
   * @return a list of GuestBookEntry objects
   */
  @GetMapping("/messages")
  public final List<GuestBookEntry> getMessages() {
    Sort byCreation = new Sort(Sort.Direction.DESC, "_id");
    List<GuestBookEntry> msgList = repository.findAll(byCreation);
    return msgList;
  }

  /**
   * endpoint for retrieving all guest book entries stored in database
   * @return a list of GuestBookEntry objects
   */
  @GetMapping("/query")
  public final List<GuestBookEntry> query(
    @RequestParam("username") String username,
    @RequestParam("password") String password
  ) {
    List<GuestBookEntry> msgList = repository.findByUsernameAndPassword(
      username,
      password
    );
    return msgList;
  }

  /**
   * endpoint for adding a new guest book entry to the database
   * @param message a message object passed in the HTTP POST request
   */
  @PostMapping("/messages")
  public final void addMessage(@RequestBody GuestBookEntry message) {
    message.setDate(System.currentTimeMillis());
    repository.save(message);
  }

//   @PostMapping("/signup")
//   public final void addUser(@RequestBody GuestBookEntry message) {
//     message.setDate(System.currentTimeMillis());
//     repository.save(message);
//   }

  @Autowired
  CustomUserDetailsService userService;

  @PostMapping("/signup")
  public ModelAndView createNewUser(
    @Valid User user,
    BindingResult bindingResult
  ) {
    ModelAndView modelAndView = new ModelAndView();
    User userExists = userService.findUserByEmail(user.getEmail());
    if (userExists != null) {
      bindingResult.rejectValue(
        "email",
        "error.user",
        "There is already a user registered with the username provided"
      );
    }
    if (bindingResult.hasErrors()) {
      modelAndView.setViewName("signup");
    } else {
      userService.saveUser(user);
      modelAndView.addObject(
        "successMessage",
        "User has been registered successfully"
      );
      modelAndView.addObject("user", new User());
      modelAndView.setViewName("login");
    }
    return modelAndView;
  }
}
