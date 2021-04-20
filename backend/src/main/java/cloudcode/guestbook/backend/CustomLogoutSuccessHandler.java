package cloudcode.guestbook.backend;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

  //   @Autowired
  //   private AuditService auditService;

  @Override
  public void onLogoutSuccess(
    HttpServletRequest request,
    HttpServletResponse response,
    Authentication authentication
  )
    throws IOException, ServletException {
    // String refererUrl = request.getHeader("Referer");
    // auditService.track("Logout from: " + refererUrl);

    super.onLogoutSuccess(request, response, authentication);
  }
}
