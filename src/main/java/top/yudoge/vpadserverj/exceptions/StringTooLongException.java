package top.yudoge.vpadserverj.exceptions;

public class StringTooLongException extends RuntimeException {
    public StringTooLongException(String message) {
        super(message);
    }
    public StringTooLongException(String message, Throwable cause) {
        super(message, cause);
    }
}
