package telran.ashkelon2020.service;

import telran.ashkelon2020.dto.UserDto;
import telran.ashkelon2020.dto.UserResponseDto;
import telran.ashkelon2020.dto.UserUpdateDto;

public interface UserService {
	
	boolean addUser(String login, UserDto userDto);
	
	UserResponseDto findUserByLogin(String login);
	
	UserResponseDto deleteUser(String login);
	
	UserResponseDto updateUser(String login, UserUpdateDto userUpdateDto);
	
	UserResponseDto addRoleToUser(String login, String role);
	
	UserResponseDto deleteRoleFromUser(String login, String role);

}
