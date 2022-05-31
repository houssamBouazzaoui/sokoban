package exceptions;

public class FouteInlogGegevensException extends IllegalArgumentException {

	public FouteInlogGegevensException() {
		super("De inloggegevens die u ingaf zijn niet correct.");
		// TODO Auto-generated constructor stub
	}

	public FouteInlogGegevensException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FouteInlogGegevensException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	public FouteInlogGegevensException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
