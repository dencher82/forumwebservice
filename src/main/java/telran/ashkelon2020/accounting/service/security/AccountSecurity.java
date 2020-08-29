package telran.ashkelon2020.accounting.service.security;

public interface AccountSecurity {
	
	String getLogin(String token);
	
	void checkExpDate(String login);

	void checkAdminRole(String token);
	
}
