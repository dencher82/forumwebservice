package telran.ashkelon2020.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.dto.UserDto;
import telran.ashkelon2020.dto.UserResponseDto;
import telran.ashkelon2020.dto.UserUpdateDto;
import telran.ashkelon2020.dto.exception.UserNotFoundException;
import telran.ashkelon2020.model.User;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepositoryMongoDB userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public boolean addUser(String login, UserDto userDto) {
		if (userRepository.findById(login) == null) {
			return false;
		}
		User user = new User(login, userDto.getPassword(), userDto.getFirstName(), userDto.getLastName());
		userDto.getRoles().forEach((r) -> user.addRole(r.toString()));
		userRepository.save(user);
		return true;
	}

	@Override
	public UserResponseDto findUserByLogin(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto deleteUser(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		userRepository.deleteById(login);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto updateUser(String login, UserUpdateDto userUpdateDto) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		String firstName = userUpdateDto.getFirstName();
		if (firstName == null) {
			firstName = user.getFirstName();
		}
		user.setFirstName(firstName);
		String lastName = userUpdateDto.getLastName();
		if (lastName == null) {
			lastName = user.getLastName();
		}
		user.setLastName(lastName);
		userRepository.save(user);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto addRoleToUser(String login, String role) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		user.addRole(role);
		userRepository.save(user);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto deleteRoleFromUser(String login, String role) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		user.removeRole(role);
		userRepository.save(user);
		return modelMapper.map(user, UserResponseDto.class);
	}

}
