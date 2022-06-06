package top.yudoge.vpadserverj.midi.factories;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;

/**
 * 该类只返回系统中那些getTransmitter()!=null的MidiDevice
 * 该类不会为性能而提供任何缓存机制，每次调用`getAllDevice`都返回系统中最新的结果
 */
public class HasTransmitterDeviceProvider extends BaseDeviceProvider {
    protected HasTransmitterDeviceProvider(){}

    @Override
    public boolean isNeededDevice(MidiDevice device) throws MidiUnavailableException {
        return device.getTransmitter() != null;
    }

}
