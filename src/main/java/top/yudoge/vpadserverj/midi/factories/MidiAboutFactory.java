package top.yudoge.vpadserverj.midi.factories;

public class MidiAboutFactory {
    private static final DeviceProvider inportProvider = new HasTransmitterDeviceProvider();
    private static final DeviceProvider outportProvider = new HasRecevierDeviceProvider();
    private static final DeviceProvider sequencerProvider = new SequencerDeviceProvider();
    private static final DeviceProvider synthesizerProvider = new SynthesizerDeviceProvider();

    /**
     * OutportProvider提供可以向其发送Midi消息的一批设备
     * 实际上，若一个MidiDevice.getRecevier()方法正常返回且不返回null
     * 它就会被outportProvider提供
     * @return
     */
    public static DeviceProvider outportProvider() {
        return outportProvider;
    }

    /**
     * inportProvider提供具有Transmitter的一批设备
     * 简而言之，这匹配设备中的每一个都可以绑定到一个或多个接收消息的MIDI设备中
     * 并向它们传递消息
     * 实际上，若一个MidiDevice.getTransmitter()方法正常返回且不返回null
     * 它就会被outportProvider提供
     * @return
     */
    public static DeviceProvider inportProvider() {
        return inportProvider;
    }

    public static DeviceProvider sequencerProvider() { return sequencerProvider; }
    public static DeviceProvider synthesizerProvider() { return synthesizerProvider; }

}
