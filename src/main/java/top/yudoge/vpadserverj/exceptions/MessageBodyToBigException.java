package top.yudoge.vpadserverj.exceptions;

public class MessageBodyToBigException extends RuntimeException {
    public MessageBodyToBigException(String message) {
        super(message);
    }
    public MessageBodyToBigException(String message, Throwable cause) {
        super(message, cause);
    }
}
