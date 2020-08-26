package telran.ashkelon2020.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class WrongPasswordsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -768935904423717793L;

	public WrongPasswordsException() {
		super("Passwords aren't correct");
	}
	
}
