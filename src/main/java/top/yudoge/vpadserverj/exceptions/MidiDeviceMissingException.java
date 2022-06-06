package top.yudoge.vpadserverj.exceptions;

public class MidiDeviceMissingException extends RuntimeException {
    public MidiDeviceMissingException(String message) {
        super(message);
    }
    public MidiDeviceMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
