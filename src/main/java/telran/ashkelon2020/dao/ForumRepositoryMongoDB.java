package telran.ashkelon2020.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.model.Post;

@Component
public interface ForumRepositoryMongoDB extends MongoRepository<Post, String>{
	
	Stream<Post> findPostsByAuthor(String author);
	
}
