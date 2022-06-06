package top.yudoge.vpadserverj.exceptions;

public class CannotReopenMidiDeviceException extends RuntimeException {
    public CannotReopenMidiDeviceException(String message) {
        super(message);
    }
    public CannotReopenMidiDeviceException(String message, Throwable cause) {
        super(message, cause);
    }
}
