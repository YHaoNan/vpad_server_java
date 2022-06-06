package top.yudoge.vpadserverj.midi;

import top.yudoge.vpadserverj.exceptions.CannotCloseMidiDeviceException;
import top.yudoge.vpadserverj.exceptions.CannotReopenMidiDeviceException;
import top.yudoge.vpadserverj.exceptions.MidiDeviceMissingException;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

/**
 * 默认MidiDeviceHolder
 */
public class DefaultMidiDeviceHolder implements MidiDeviceHolder {
    private volatile MidiDevice instrumentDevice;
    private volatile MidiDevice controlDevice;

    public synchronized void setUpDevices(MidiDevice instrumentDevice, MidiDevice controlDevice) {
        this.instrumentDevice = instrumentDevice;
        this.controlDevice = controlDevice;
    }

    @Override
    public MidiDevice instrumentDevice() {
        if (instrumentDevice == null)
            throw new MidiDeviceMissingException("Please setup the instrument device before you use it.");

        return instrumentDevice;
    }

    @Override
    public MidiDevice controlDevice() {
        if (controlDevice == null)
            throw new MidiDeviceMissingException("Please setup the control device before you use it.");

        return controlDevice;
    }

    @Override
    public synchronized void open() throws MidiUnavailableException {
        if (instrumentDevice.isOpen())
            throw new CannotReopenMidiDeviceException("InstrumentDevice is already open, cannot open it again!");
        if (controlDevice.isOpen())
            throw new CannotReopenMidiDeviceException("ControlDevice is already open, cannot open it again!");

        instrumentDevice.open();
        // 如果第二个开启失败，则将第一个连接也关闭
        try {
            controlDevice.open();
        } catch (MidiUnavailableException e) {
            if (instrumentDevice.isOpen()) instrumentDevice.close();
            throw new MidiUnavailableException();
        }
    }

    @Override
    public synchronized void close() {
        if (!instrumentDevice.isOpen())
            throw new CannotCloseMidiDeviceException("InstrumentDevice has not open yet, cannot close it!");
        if (!controlDevice.isOpen())
            throw new CannotReopenMidiDeviceException("ControlDevice has not open yet, cannot close it!");

        instrumentDevice.close();
        controlDevice.close();
    }
}
