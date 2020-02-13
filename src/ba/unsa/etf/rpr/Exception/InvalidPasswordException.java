package ba.unsa.etf.rpr.Exception;

public class InvalidPasswordException extends Throwable {
    public InvalidPasswordException(String wrong_password) {
        super(wrong_password);
    }
}
