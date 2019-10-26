package core.jdbc;

public class CannotDeleteException extends Exception {
    private static final long serialVersionUID = 1L;

    public CannotDeleteException() {
    }

    public CannotDeleteException(String message) {
        super(message);
    }

    public CannotDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotDeleteException(Throwable cause) {
        super(cause);
    }

    public CannotDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
