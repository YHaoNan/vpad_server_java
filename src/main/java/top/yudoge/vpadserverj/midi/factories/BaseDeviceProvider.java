package top.yudoge.vpadserverj.midi.factories;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类提供一个基本的模板，它获得系统中所有的MIDI设备信息，然后调用子类的isNeededDevice方法过滤，返回过滤后的结果
 *
 * 该类不缓存任何关于系统中的MIDI设备信息，所以它永远返回最新的数据
 */
public abstract class BaseDeviceProvider implements DeviceProvider {
    /**
     * 尝试获取所有符合子类约束条件的设备，如果在其中出现了任何形式的异常，静默失败
     * @return 所有符合子类约束条件的设备
     */
    @Override
    public List<MidiDevice> getAllDevices() {
        List<MidiDevice> results = new ArrayList<>();

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            try {
                MidiDevice currentDevice = MidiSystem.getMidiDevice(info);
                if (isNeededDevice(currentDevice))
                    results.add(currentDevice);
            } catch (Exception e) {}
        }
        return results;
    }

    public abstract boolean isNeededDevice(MidiDevice device) throws Exception;
}
