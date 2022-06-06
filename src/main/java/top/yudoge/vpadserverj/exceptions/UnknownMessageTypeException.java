package top.yudoge.vpadserverj.exceptions;

public class UnknownMessageTypeException extends RuntimeException {
    public UnknownMessageTypeException(String message) {
        super(message);
    }
    public UnknownMessageTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
