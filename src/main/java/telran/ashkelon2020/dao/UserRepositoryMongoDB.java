package telran.ashkelon2020.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.model.User;

@Component
public interface UserRepositoryMongoDB extends MongoRepository<User, String> {
	
}
