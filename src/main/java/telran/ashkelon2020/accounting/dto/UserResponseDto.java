package telran.ashkelon2020.accounting.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Getter;
import telran.ashkelon2020.accounting.model.Roles;

@Getter
public class UserResponseDto {
	String login;
	String firstName;
	String lastName;
	LocalDate expDate;
	Set<Roles> roles;
	
}
