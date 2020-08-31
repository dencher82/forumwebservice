package telran.ashkelon2020.accounting.service.security;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.accounting.dto.exception.ForbiddenException;

@Aspect
@Component
public class SecureAspect { // works between Controller and Service

	@Autowired
	AccountSecurity securityService;

	@Pointcut("execution(public * telran.ashkelon2020.accounting.controller.UserController.login(..))"
			+ "&& args(token, ..)")
	public void loginUser(String token) {
	} // will handle method(s) from annotation @Pointcut ((..) - means that can be any args)

	@Pointcut("execution(public * telran.ashkelon2020.accounting.controller.UserController.*User(..))"
			+ "&& args(login, token, ..)")
	public void manipulateUser(String login, String token) { // the order of args is important
	}
	
	@Pointcut("execution(public * telran.ashkelon2020.accounting.controller.UserController.*Role(..))"
			+ "&& args(.., token)")
	public void manipulateRole(String token) {}
	
	@Pointcut("@annotation(Authenticated)")
	public void authenticated() {}

	@Around("loginUser(token)") // handle method from this annotation
	public Object checkTokenAndExpDate(ProceedingJoinPoint pjp, String token) throws Throwable {
		Object[] args = pjp.getArgs(); // get the arguments
		String user = securityService.getLogin(token); // handle with AccountSecurity and get user login
		securityService.checkExpDate(user);
		args[args.length - 1] = user; // write new value in field user
		return pjp.proceed(args); // Proceed the method(s) from annotation @Pointcut with new arguments
	}

	@Before("manipulateUser(login, token)")
	public void checkTokenAndExpDateAndValidateUser(String login, String token) {
		String user = securityService.getLogin(token);
		securityService.checkExpDate(user);
		if (!user.equals(login)) {
			throw new ForbiddenException();
		}
	}
	
	@Before("manipulateRole(token)")
	public void checkTokenAndValidateAdmin(String token) {
		String user = securityService.getLogin(token);
		if (!securityService.checkAdminRole(user, "ADMIN")) {
			throw new ForbiddenException();
		}
	}
	
	@Around("authenticated()")
	public Object checkAuthenticated(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		String user = securityService.getLogin((String) args[0]);
		args[args.length - 1] = user;
		return pjp.proceed(args);
	}
	
}
