package top.yudoge.vpadserverj.messagehandler;

import top.yudoge.vpadserverj.messages.MessageTypes;
import top.yudoge.vpadserverj.messages.MidiMessage;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;

public class MidiMessageHandler implements MessageHandler<MidiMessage> {

    @Override
    public void handle(MidiMessage message, MessageHandlerContext ctx) throws InvalidMidiDataException, MidiUnavailableException {
        MidiDevice device = ctx.midiDeviceHolder().instrumentDevice();
            ShortMessage shortMessage = new ShortMessage(
                    message.getState() == MidiMessage.STATE_NOTE_ON ? ShortMessage.NOTE_ON : ShortMessage.NOTE_OFF,
                    0, message.getNote(), message.getVelocity()
            );
            device.getReceiver().send(shortMessage, -1);
    }

    @Override
    public int messageTypeCanHandle() {
        return MessageTypes.MIDIMESSAGE;
    }
}
