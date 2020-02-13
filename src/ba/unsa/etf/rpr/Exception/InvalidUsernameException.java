package ba.unsa.etf.rpr.Exception;

public class InvalidUsernameException extends Throwable {
    public InvalidUsernameException(String wrong_username) {
        super(wrong_username);
    }
}
