package telran.ashkelon2020.accounting.service;

import telran.ashkelon2020.accounting.dto.UserDto;
import telran.ashkelon2020.accounting.dto.UserResponseDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;

public interface UserService {
	
	boolean addUser(UserDto userDto);
	
	UserResponseDto findUserByLogin(String login);
	
	UserResponseDto deleteUser(String login);
	
	UserResponseDto updateUser(String login, UserUpdateDto userUpdateDto);
	
	UserResponseDto addRoleToUser(String login, String role);
	
	UserResponseDto deleteRoleFromUser(String login, String role);

}
