package top.yudoge.vpadserverj.exceptions;

public class CannotRestartVPadServerException extends RuntimeException {
    public CannotRestartVPadServerException(String message) {
        super(message);
    }
    public CannotRestartVPadServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
