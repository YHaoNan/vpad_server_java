package top.yudoge.vpadserverj.midi;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;


/**
 * 持有两个Midi设备的类，一个用于演奏，一个用于控制，提供它们的共同开启和关闭
 * 该类需要是线程安全的，该类持有的Midi设备可以被动态更换，且更换后应该立即对其它线程可见
 * 若实现类希望持有的Midi设备是可替换的，那么它应该保证不会有两个线程同时对MidiDeviceHolder持有的Midi设备进行替换
 *
 * 一般情况下，该类应该是全局Singleton的
 */
public interface MidiDeviceHolder extends AutoCloseable {
    /**
     * 返回持有的InstrumentDevice，用于演奏
     * 该方法不必是同步的
     * @return instrumentDevice
     */
    MidiDevice instrumentDevice();

    /**
     * 返回持有的controlDevice，用于控制
     * 该方法不必是同步的
     * @return deviceDevice
     */
    MidiDevice controlDevice();

    /**
     * 同时开启两个Midi设备，此时这两个MIDI设备都应该处于关闭状态
     * 若其中任意一个处于开启状态，方法立即抛出CannotReopenMidiDeviceException
     * 并结束执行，两个midi设备的状态不会发生任何改变（即对两个设备的检测操作在具体的修改状态操作前）
     *
     * 该方法必须是同步的，若有一个线程想要调用open方法，必须等待当前在open方法中执行的线程退出该方法
     * @throws MidiUnavailableException
     *          如果MIDI设备不可以被开启，抛出这个异常，此时该类的两个MIDI设备可能处于不一致的状态
     *          即一个关闭一个开启，该类的实现类需要保证这种不一致不会发生，即它会去关闭另一个midi设备
     */
    void open() throws MidiUnavailableException;

    /**
     * 同时关闭两个Midi设备，此时这两个MIDI设备都应该处于开启状态
     * 若其中任意一个处于关闭状态，方法立即抛出CannotCloseMidiDeviceException
     * 并结束执行，两个midi设备的状态不会发生任何改变（即对两个设备的检测操作在具体的修改状态操作前）
     *
     * 该方法必须是同步的，若有一个线程想要调用close方法，必须等待当前在close方法中执行的线程退出该方法
     */
    void close();
}
