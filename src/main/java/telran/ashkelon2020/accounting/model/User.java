package telran.ashkelon2020.accounting.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"login"})
@Document(collection = "users")
public class User {
	@Id
	String login;
	String password;
	String firstName;
	String lastName;
	LocalDate expDate;
	Set<String> roles = new HashSet<>();
		
	/**
	 * The method adds a role in set of roles
	 * 
	 * @param role
	 * @return
	 */
	public boolean addRole(String role) {
		return roles.add(role.toUpperCase());
	}
	
	/**
	 * The method removes a role from set of roles
	 * 
	 * @param role
	 * @return
	 */
	public boolean removeRole(String role) {
		return roles.remove(role.toUpperCase());
	}
	
}
