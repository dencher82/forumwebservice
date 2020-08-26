package telran.ashkelon2020.accounting.service;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import telran.ashkelon2020.accounting.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.accounting.dto.UserChangePasswordDto;
import telran.ashkelon2020.accounting.dto.UserRegisterDto;
import telran.ashkelon2020.accounting.dto.UserResponseDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;
import telran.ashkelon2020.accounting.dto.exception.WrongPasswordsException;
import telran.ashkelon2020.accounting.dto.exception.UnauthorizedException;
import telran.ashkelon2020.accounting.dto.exception.UserNotFoundException;
import telran.ashkelon2020.accounting.model.User;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepositoryMongoDB userRepository;

	@Autowired
	ModelMapper modelMapper;

	@Getter
	@Setter
	@Value("${expdate.value}")
	private long period;

	@Getter
	@Setter
	@Value("${default.role}")
	private String defaultUser;

	@Override
	public boolean addUser(UserRegisterDto userRegisterDto) {
		if (userRepository.existsById(userRegisterDto.getLogin())) {
			return false;
		}
		User user = modelMapper.map(userRegisterDto, User.class);
		user.setExpDate(LocalDate.now().plusDays(period));
		user.addRole(defaultUser.toUpperCase());
		userRegisterDto.getRoles().forEach((r) -> user.addRole(r.toString()));
		userRepository.save(user);
		return true;
	}

	@Override
	public UserResponseDto findUserByLogin(String login, String password) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		if (!user.getPassword().equals(password)) {
			throw new UnauthorizedException();
		}
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto deleteUser(String login, String password) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		if (!user.getPassword().equals(password)) {
			throw new UnauthorizedException();
		}
		userRepository.deleteById(login);
		return modelMapper.map(user, UserResponseDto.class);
	}

	@Override
	public UserResponseDto updateUser(UserUpdateDto userUpdateDto) {
		String login = userUpdateDto.getLogin();
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

	@Override
	public UserResponseDto changeUserPassword(UserChangePasswordDto userChangePasswordDto) {
		String login = userChangePasswordDto.getLogin();
		String password = userChangePasswordDto.getPassword();
		String newPassword = userChangePasswordDto.getNewPassword();
		String newPasswordConfirm = userChangePasswordDto.getConfirmPassword();
		if (newPassword == null || newPassword.equals(password) || !newPassword.equals(newPasswordConfirm)) {
			throw new WrongPasswordsException();
		}
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		if (!user.getPassword().equals(userChangePasswordDto.getPassword())) {
			throw new UnauthorizedException();
		}
		user.setPassword(newPassword);
		userRepository.save(user);
		return modelMapper.map(user, UserResponseDto.class);
	}

}
