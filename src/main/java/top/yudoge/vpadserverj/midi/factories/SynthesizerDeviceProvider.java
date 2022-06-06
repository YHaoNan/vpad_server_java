package top.yudoge.vpadserverj.midi.factories;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.Synthesizer;

public class SynthesizerDeviceProvider extends BaseDeviceProvider {
    protected SynthesizerDeviceProvider(){}
    @Override
    public boolean isNeededDevice(MidiDevice device) throws Exception {
        return device instanceof Synthesizer;
    }
}
