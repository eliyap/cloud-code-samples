package cloudcode.guestbook.frontend;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:spring/webSecurityConfig.xml" })
public class SecSecurityConfig { 

  public SecSecurityConfig() {
    super();
  }
}
