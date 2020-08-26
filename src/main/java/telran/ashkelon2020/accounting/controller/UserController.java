package telran.ashkelon2020.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.accounting.dto.UserChangePasswordDto;
import telran.ashkelon2020.accounting.dto.UserLoginDto;
import telran.ashkelon2020.accounting.dto.UserRegisterDto;
import telran.ashkelon2020.accounting.dto.UserResponseDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;
import telran.ashkelon2020.accounting.service.UserService;

@RestController
@RequestMapping("/forum")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public boolean addUser(@RequestBody UserRegisterDto userRegisterDto) {
		return userService.addUser(userRegisterDto);
	}
	
	@GetMapping("/login")
	public UserResponseDto findUserByLogin(@RequestBody UserLoginDto userLoginDto) {
		return userService.findUserByLogin(userLoginDto.getLogin(), userLoginDto.getPassword());
	}
	
	@DeleteMapping("/delete/")
	public UserResponseDto deleteUser(@RequestBody UserLoginDto userLoginDto) {
		return userService.deleteUser(userLoginDto.getLogin(), userLoginDto.getPassword());
	}
	
	@PutMapping("/update")
	public UserResponseDto updateUser(@RequestBody UserUpdateDto userUpdateDto) {
		return userService.updateUser(userUpdateDto);
	}

	@PutMapping("/user/{login}/role/{role}")
	public UserResponseDto addRoleToUser(@PathVariable String login, @PathVariable String role) {
		return userService.addRoleToUser(login, role);
	}
	
	@DeleteMapping("/user/{login}/role/{role}")
	public UserResponseDto deleteRoleFromUser(@PathVariable String login, @PathVariable String role) {
		return userService.deleteRoleFromUser(login, role);
	}
	
	@PutMapping("/changepassword")
	public UserResponseDto changeUserPassword(@RequestBody UserChangePasswordDto userChangePasswordDto) {
		return userService.changeUserPassword(userChangePasswordDto);
	}
	
}
