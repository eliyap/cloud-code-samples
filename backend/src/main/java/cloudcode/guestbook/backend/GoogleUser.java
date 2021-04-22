package cloudcode.guestbook.backend;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "googleuser")
public class GoogleUser {

  @Indexed(
    unique = true,
    direction = IndexDirection.DESCENDING,
    dropDups = true
  )
  private String email;
}
