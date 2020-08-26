package telran.ashkelon2020.accounting.service;

import telran.ashkelon2020.accounting.dto.UserChangePasswordDto;
import telran.ashkelon2020.accounting.dto.UserRegisterDto;
import telran.ashkelon2020.accounting.dto.UserResponseDto;
import telran.ashkelon2020.accounting.dto.UserUpdateDto;

public interface UserService {
	
	boolean addUser(UserRegisterDto userRegisterDto);
	
	UserResponseDto findUserByLogin(String login, String password);
	
	UserResponseDto deleteUser(String login, String password);
	
	UserResponseDto updateUser(UserUpdateDto userUpdateDto);
	
	UserResponseDto addRoleToUser(String login, String role);
	
	UserResponseDto deleteRoleFromUser(String login, String role);
	
	UserResponseDto changeUserPassword(UserChangePasswordDto userChangePasswordDto);

}
