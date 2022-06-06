package top.yudoge.vpadserverj.messagehandler;

import top.yudoge.vpadserverj.Constants;
import top.yudoge.vpadserverj.messages.ArpMessage;
import top.yudoge.vpadserverj.messages.MessageTypes;

import javax.sound.midi.*;
import java.util.Map;

public class ArpMessageHandler implements MessageHandler<ArpMessage> {

    private Sequencer sequencer;
    private Sequence sequence;
    private volatile Receiver outportReceiver;
    private Map<String, Track> trackMap;
    private static final int[] RATE_TICK_MAP = new int[] {

    };

    public ArpMessageHandler() throws MidiUnavailableException, InvalidMidiDataException {
        sequencer = MidiSystem.getSequencer();
        sequence = new Sequence(Sequence.PPQ, Constants.MAX_SEQUENCER_TICK_RESOLUTION);
        sequencer.setSequence(sequence);
        sequencer.setTempoInBPM(130);
        sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
    }

    @Override
    public void handle(ArpMessage message, MessageHandlerContext ctx) throws Exception {
        Receiver newOutReceiver = ctx.midiDeviceHolder().instrumentDevice().getReceiver();
        // 如果新的和之前的不是一个对象
        if (outportReceiver != newOutReceiver) {
            outportReceiver = newOutReceiver;
            sequencer.getTransmitter().setReceiver(outportReceiver);
        }

        if (message.getState() == ArpMessage.STATE_ON) {
            startArp(message, ctx);
        } else {
            stopArp(message, ctx);
        }

    }

    private synchronized void startArp(ArpMessage message, MessageHandlerContext ctx) {
        String key = getKey(message, ctx);
        if (trackMap.containsKey(key)) return;
        Track track = sequence.createTrack();
        trackMap.put(key, track);
    }

    private synchronized void stopArp(ArpMessage message, MessageHandlerContext ctx) {
        String key = getKey(message, ctx);
        if (!trackMap.containsKey(key)) return;
        sequence.deleteTrack(trackMap.get(key));
    }

    private String getKey(ArpMessage message, MessageHandlerContext ctx) {
        ClientEndCommunicator communicator = ctx.clientEndCommunicator();
        return message.getNote() + "@" + communicator.clientHostName() + ":" + communicator.clientPort();
    }

    @Override
    public int messageTypeCanHandle() {
        return MessageTypes.ARPMESSAGE;
    }
}
