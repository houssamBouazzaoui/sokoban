package exceptions;

@SuppressWarnings("serial")
public class IllegalMoveException extends Exception {

	public IllegalMoveException() {
		super("Verplaatsing niet toegelaten.");
		// TODO Auto-generated constructor stub
	}

	public IllegalMoveException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public IllegalMoveException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IllegalMoveException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IllegalMoveException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
