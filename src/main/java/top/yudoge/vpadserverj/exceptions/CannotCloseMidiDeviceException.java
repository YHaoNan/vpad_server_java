package top.yudoge.vpadserverj.exceptions;

public class CannotCloseMidiDeviceException extends RuntimeException {
    public CannotCloseMidiDeviceException(String message) {
        super(message);
    }
    public CannotCloseMidiDeviceException(String message, Throwable cause) {
        super(message, cause);
    }
}
