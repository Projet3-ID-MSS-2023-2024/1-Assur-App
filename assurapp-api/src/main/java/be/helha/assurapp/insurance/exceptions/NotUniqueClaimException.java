package be.helha.assurapp.insurance.exceptions;

public class NotUniqueClaimException extends Exception {

    public NotUniqueClaimException() {
        super();
    }

    public NotUniqueClaimException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueClaimException(Throwable cause) {
        super(cause);
    }
}
