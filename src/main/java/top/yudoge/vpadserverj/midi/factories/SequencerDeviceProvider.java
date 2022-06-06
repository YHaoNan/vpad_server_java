package top.yudoge.vpadserverj.midi.factories;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Sequencer;

public class SequencerDeviceProvider extends BaseDeviceProvider {
    protected SequencerDeviceProvider() {}
    @Override
    public boolean isNeededDevice(MidiDevice device) throws Exception {
        return device instanceof Sequencer;
    }
}
