package telran.ashkelon2020.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.accounting.dto.UserRegisterDto;
import telran.ashkelon2020.accounting.dto.UserResponseDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;
import telran.ashkelon2020.accounting.service.UserService;
import telran.ashkelon2020.accounting.service.security.AccountSecurity;
import telran.ashkelon2020.accounting.service.security.Authenticated;

@RestController
@RequestMapping("/forum")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AccountSecurity securityService;

	@PostMapping("/register")
	public boolean register(@RequestBody UserRegisterDto userRegisterDto) {
		return userService.addUser(userRegisterDto);
	}

	@GetMapping("/login")
	public UserResponseDto login(@RequestHeader("Authorization") String token, String user) {
//		String user = securityService.getLogin(token);
//		securityService.checkExpDate(user);
		return userService.findUserByLogin(user);
	}

	@DeleteMapping("/delete/{login}")
	public UserResponseDto deleteUser(@PathVariable String login, @RequestHeader("Authorization") String token) {
//		String user = securityService.getLogin(token);
//		if (!user.equals(login)) {
//			throw new ForbiddenException();
//		}
		return userService.deleteUser(login);
	}

	@PutMapping("/update/{login}")
	public UserResponseDto updateUser(@PathVariable String login, @RequestHeader("Authorization") String token,
			@RequestBody UserUpdateDto userUpdateDto) {
//		String user = securityService.getLogin(token);
//		securityService.checkExpDate(user);
//		if (!user.equals(login)) {
//			throw new ForbiddenException();
//		}
		return userService.updateUser(login, userUpdateDto);
	}

	@PutMapping("/user/{login}/role/{role}")
	public UserResponseDto addRole(@PathVariable String login, @PathVariable String role,
			@RequestHeader("Authorization") String token) {
//		securityService.checkAdminRole(token);
		return userService.changeRolesList(login, role, true);
	}

	@DeleteMapping("/user/{login}/role/{role}")
	public UserResponseDto deleteRole(@PathVariable String login, @PathVariable String role,
			@RequestHeader("Authorization") String token) {
//		securityService.checkAdminRole(token);
		return userService.changeRolesList(login, role, false);
	}

	@PutMapping("/changepassword")
	@Authenticated
	public UserResponseDto changeUserPassword(@RequestHeader("Authorization") String token,
			@RequestHeader("X-Password") String newPassword, String user) {
//		String user = securityService.getLogin(token);
		return userService.changeUserPassword(user, newPassword);
	}

}
