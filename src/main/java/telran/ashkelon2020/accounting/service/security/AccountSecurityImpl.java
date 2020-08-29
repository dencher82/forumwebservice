package telran.ashkelon2020.accounting.service.security;

import java.time.LocalDateTime;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.accounting.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.accounting.dto.UserLoginDto;
import telran.ashkelon2020.accounting.dto.exception.ForbiddenException;
import telran.ashkelon2020.accounting.dto.exception.UnauthorizedException;
import telran.ashkelon2020.accounting.dto.exception.UserNotFoundException;
import telran.ashkelon2020.accounting.model.User;

@Service
public class AccountSecurityImpl implements AccountSecurity {

	@Autowired
	UserRepositoryMongoDB userRepository;

	@Override
	public String getLogin(String token) {
		UserLoginDto userLoginDto = tokenDecode(token);
		User user = userRepository.findById(userLoginDto.getLogin())
				.orElseThrow(() -> new UserNotFoundException(userLoginDto.getLogin()));
		if (!BCrypt.checkpw(userLoginDto.getPassword(), user.getPassword())) { // check hash functions
			throw new UnauthorizedException();
		}
		if (user.getRoles().isEmpty()) {
			throw new ForbiddenException();
		}
		return user.getLogin();
	}

	@Override
	public void checkExpDate(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		if (user.getExpDate().isBefore(LocalDateTime.now())) {
			throw new ForbiddenException();
		}
	}

	@Override
	public void checkAdminRole(String token) {
		UserLoginDto adminLoginDto = tokenDecode(token);
		User admin = userRepository.findById(adminLoginDto.getLogin())
				.orElseThrow(() -> new UserNotFoundException(adminLoginDto.getLogin()));
		if (!admin.getRoles().contains("ADMIN")) {
			throw new ForbiddenException();
		}
		checkExpDate(adminLoginDto.getLogin());
	}

	private UserLoginDto tokenDecode(String token) {
		try {
			String[] credentials = token.split(" ");
			String credential = new String(Base64.getDecoder().decode(credentials[1]));
			credentials = credential.split(":");
			return new UserLoginDto(credentials[0], credentials[1]);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
	}

}
