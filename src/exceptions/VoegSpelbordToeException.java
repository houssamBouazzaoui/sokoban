package exceptions;

public class VoegSpelbordToeException extends RuntimeException {

	public VoegSpelbordToeException() {
		super("Spelbord werd niet toegevoegd");
		// TODO Auto-generated constructor stub
	}

	public VoegSpelbordToeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public VoegSpelbordToeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public VoegSpelbordToeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public VoegSpelbordToeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
