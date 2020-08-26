package telran.ashkelon2020.accounting.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.accounting.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.accounting.dto.UserDto;
import telran.ashkelon2020.accounting.dto.UserResponseDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;
import telran.ashkelon2020.accounting.dto.exception.UserNotFoundException;
import telran.ashkelon2020.accounting.model.User;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepositoryMongoDB userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public boolean addUser(UserDto userDto) {
		if (userRepository.findById(userDto.getLogin()) == null) {
			return false;
		}
		User user = new User(userDto.getLogin(), userDto.getPassword(), userDto.getFirstName(), userDto.getLastName());
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