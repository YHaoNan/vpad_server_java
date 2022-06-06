package top.yudoge.vpadserverj.exceptions;

public class VPadServerStopFaildException extends RuntimeException {
    public VPadServerStopFaildException(String message) {
        super(message);
    }
    public VPadServerStopFaildException(String message, Throwable cause) {
        super(message, cause);
    }
}
