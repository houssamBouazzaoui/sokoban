package exceptions;

@SuppressWarnings("serial")
public class GebruikersnaamAlInGebruikException extends RuntimeException {
	public GebruikersnaamAlInGebruikException()
    {
    }
    
    public GebruikersnaamAlInGebruikException(String message)
    {
        super(message);
    }

    public GebruikersnaamAlInGebruikException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GebruikersnaamAlInGebruikException(Throwable cause)
    {
        super(cause);
    }

    public GebruikersnaamAlInGebruikException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
