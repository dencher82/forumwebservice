package telran.ashkelon2020.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Post {
	String id;
	@Setter
	String title;
	@Setter
	String content;
	@Setter
	String author;
	LocalDateTime dateCreated = LocalDateTime.now();
	@Setter
	Set<String> tags = new HashSet<>();
	@Setter
	int likes;
	List<Comment> comments = new ArrayList<>();
	
	public Post(String title, String content, String author, Set<String> tags) {
//		this.id = author + System.currentTimeMillis(); // mongoDB will generate Id by yourself
		this.title = title;
		this.content = content;
		this.author = author;
		dateCreated = LocalDateTime.now();
		this.tags = tags;
		likes = 0;
		comments = new ArrayList<Comment>();
	}
	
	public void addComment(Comment comment) {
		comments.add(comment);
	}

}
