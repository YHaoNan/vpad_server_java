package top.yudoge.vpadserverj.midi.factories;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

public class HasRecevierDeviceProvider extends BaseDeviceProvider {
    protected HasRecevierDeviceProvider() {}
    @Override
    public boolean isNeededDevice(MidiDevice device) throws MidiUnavailableException {
        return device.getReceiver() != null;
    }
}
