package telran.ashkelon2020.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Getter;
import telran.ashkelon2020.model.Roles;

@Getter
public class UserResponseDto {
	String login;
	String password;
	String firstName;
	String lastName;
	LocalDate expDate;
	Set<Roles> roles;
	
}
