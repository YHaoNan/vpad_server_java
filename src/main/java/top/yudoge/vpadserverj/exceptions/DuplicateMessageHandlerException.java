package top.yudoge.vpadserverj.exceptions;

public class DuplicateMessageHandlerException extends RuntimeException {
    public DuplicateMessageHandlerException(String message) {
        super(message);
    }
    public DuplicateMessageHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
