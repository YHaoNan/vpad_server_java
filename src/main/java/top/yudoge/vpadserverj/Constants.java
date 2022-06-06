package top.yudoge.vpadserverj;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Constants {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final int MAX_MESSAGE_BYTES = 32767;
    public static final int DEFAULT_PORT = 1236;
    public static final int MAX_SEQUENCER_TICK_RESOLUTION = 64;
}
