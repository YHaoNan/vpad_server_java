package top.yudoge.vpadserverj.exceptions;

public class MessageHandlerNotFoundException extends RuntimeException {
    public MessageHandlerNotFoundException(String message) {
        super(message);
    }
    public MessageHandlerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
