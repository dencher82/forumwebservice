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
@EqualsAndHashCode(of = {"login"})
@Document(collection = "users")
public class User {
	@Id
	String login;
	@Setter
	String password;
	@Setter
	String firstName;
	@Setter
	String lastName;
	LocalDate expDate = LocalDate.now().plusDays(30);
	Set<Roles> roles = new HashSet<>();
	
	public User() {
		roles.add(Roles.USER);
	}

	public User(String login, String password, String firstName, String lastName) {
		this();
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * The method adds a role in set of roles
	 * 
	 * @param role
	 * @return
	 */
	public boolean addRole(String role) {
		String newRole = role.toUpperCase();
		return roles.add(Roles.valueOf(newRole));
	}
	
	/**
	 * The method removes a role from set of roles
	 * 
	 * @param role
	 * @return
	 */
	public boolean removeRole(String role) {
		String newRole = role.toUpperCase();
		return roles.remove(Roles.valueOf(newRole));
	}
	
}
