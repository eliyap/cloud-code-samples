package cloudcode.guestbook.frontend;

import java.net.http.HttpResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

  @GetMapping("/stock")
  public final ResponseEntity<?> stock(@RequestParam String stock) {
    return ResponseEntity.ok().body("TEST");
  }
}
