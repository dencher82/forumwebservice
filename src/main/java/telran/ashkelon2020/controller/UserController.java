package telran.ashkelon2020.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.dto.UserDto;
import telran.ashkelon2020.dto.UserResponseDto;
import telran.ashkelon2020.dto.UserUpdateDto;
import telran.ashkelon2020.service.UserService;

@RestController
@RequestMapping("/forum/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/{login}")
	public boolean addUser(@PathVariable String login, @RequestBody UserDto userDto) {
		return userService.addUser(login, userDto);
	}
	
	@GetMapping("/{login}")
	public UserResponseDto findUserByLogin(@PathVariable String login) {
		return userService.findUserByLogin(login);
	}
	
	@DeleteMapping("/{login}")
	public UserResponseDto deleteUser(@PathVariable String login) {
		return userService.deleteUser(login);
	}
	
	@PutMapping("/{login}")
	public UserResponseDto updateUser(@PathVariable String login, @RequestBody UserUpdateDto userUpdateDto) {
		return userService.updateUser(login, userUpdateDto);
	}
	
	@PutMapping("/{login}/role/{role}")
	public UserResponseDto addRoleToUser(@PathVariable String login, @PathVariable String role) {
		return userService.addRoleToUser(login, role);
	}
	
	@DeleteMapping("/{login}/role/{role}")
	public UserResponseDto deleteRoleFromUser(@PathVariable String login, @PathVariable String role) {
		return userService.deleteRoleFromUser(login, role);
	}
	
}
