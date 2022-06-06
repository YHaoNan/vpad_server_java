package top.yudoge.vpadserverj.midi.factories;

import javax.sound.midi.MidiDevice;
import java.util.List;

/**
 * 一个DeviceProvider向外界提供一批MIDIDevice
 *
 * Provider可以不向外提供当前系统中所有的MIDIDevice，而是提供一个子集
 * 具体返回哪些MidiDevice实例参见具体实现类
 */
public interface DeviceProvider {
    /**
     * 提供一批设备
     * @return
     */
    List<MidiDevice> getAllDevices();
}
