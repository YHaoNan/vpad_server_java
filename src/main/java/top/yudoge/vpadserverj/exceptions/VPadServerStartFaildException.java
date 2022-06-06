package top.yudoge.vpadserverj.exceptions;

public class VPadServerStartFaildException extends RuntimeException {
    public VPadServerStartFaildException(String message) {
        super(message);
    }
    public VPadServerStartFaildException(String message, Throwable cause) {
        super(message, cause);
    }
}
