package top.yudoge.vpadserverj.cmdline;

import top.yudoge.vpadserverj.NettyVPadServer;
import top.yudoge.vpadserverj.VPadServer;
import top.yudoge.vpadserverj.messagehandler.HandShakeMessageHandler;
import top.yudoge.vpadserverj.messagehandler.MidiMessageHandler;
import top.yudoge.vpadserverj.midi.DefaultMidiDeviceHolder;
import top.yudoge.vpadserverj.midi.factories.MidiAboutFactory;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import java.util.List;
import java.util.Scanner;

public class CommandLineServerStarter {
    private static DefaultMidiDeviceHolder deviceHolder;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException, MidiUnavailableException {
        initMidiDevice();
        System.out.println(deviceHolder.controlDevice().isOpen());
        System.out.println(deviceHolder.instrumentDevice().isOpen());

        VPadServer server = new NettyVPadServer(deviceHolder)
                .addServerStopCallback(s -> deviceHolder.close());

        server.messageHandlerRegistry()
                .registe(new HandShakeMessageHandler())
                .registe(new MidiMessageHandler());


        Thread serverThread = new Thread(()->{
            server.start();
        });

        serverThread.start();
        Thread.sleep(60 * 1000 * 20);
        server.stop();
        System.out.println(deviceHolder.controlDevice().isOpen());
        System.out.println(deviceHolder.instrumentDevice().isOpen());
    }



    private static void initMidiDevice() throws MidiUnavailableException {
        deviceHolder = new DefaultMidiDeviceHolder();
        List<MidiDevice> midiDevices = MidiAboutFactory.outportProvider().getAllDevices();
        System.out.println("All midi devices: ");
        for (int i=0; i<midiDevices.size(); i++) {
            System.out.printf("\t%d. %s\n", i, midiDevices.get(i).getDeviceInfo().getName());
        }

        System.out.print("Choose instrument device: ");
        MidiDevice instDevice = midiDevices.get(sc.nextInt());
        System.out.print("Choose control device: ");
        MidiDevice ctrlDevice = midiDevices.get(sc.nextInt());

        deviceHolder.setUpDevices(instDevice, ctrlDevice);
        deviceHolder.open();
    }
}
