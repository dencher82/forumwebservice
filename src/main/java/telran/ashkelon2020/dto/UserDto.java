package telran.ashkelon2020.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class UserDto {
	String login;
	String password;
	String firstName;
	String lastName;
	Set<String> roles;
	
}
