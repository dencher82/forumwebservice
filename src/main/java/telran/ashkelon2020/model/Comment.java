package telran.ashkelon2020.model;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = { "user", "dateCreated" })
public class Comment {
	@Setter
	String user;
    String message;
    LocalDateTime dateCreated = LocalDateTime.now();
    int likes;
	
    public Comment(String user, String message) {
		this.user = user;
		this.message = message;
		dateCreated = LocalDateTime.now();
		likes = 0;
	}

}
