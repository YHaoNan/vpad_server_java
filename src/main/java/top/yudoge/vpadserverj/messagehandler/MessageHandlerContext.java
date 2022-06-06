package top.yudoge.vpadserverj.messagehandler;

import top.yudoge.vpadserverj.midi.MidiDeviceHolder;

public interface MessageHandlerContext {
    ClientEndCommunicator clientEndCommunicator();
    MidiDeviceHolder midiDeviceHolder();
}
