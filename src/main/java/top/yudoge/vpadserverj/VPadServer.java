package top.yudoge.vpadserverj;

import org.jetbrains.annotations.NotNull;
import top.yudoge.vpadserverj.exceptions.VPadServerStartFaildException;
import top.yudoge.vpadserverj.exceptions.VPadServerStopFaildException;
import top.yudoge.vpadserverj.messagehandler.MessageHandler;
import top.yudoge.vpadserverj.messagehandler.MessageHandlerRegistry;
import top.yudoge.vpadserverj.midi.MidiDeviceHolder;

import javax.sound.midi.MidiDevice;
import java.util.function.Consumer;
import java.util.function.Function;

public interface VPadServer {
    /**
     * 开启VPadServer
     *
     * 所有实现类需要将VPadServer绑定到0.0.0.0:1236上
     * 如果启动失败，抛出VPadServerStartFaildException，并说明原因
     * 如果isStarted() == true，抛出CannotRestartVPadServerException
     * 该方法将会阻塞，直到stop方法被调用，该方法才会顺利结束
     *
     *
     * 所以，stop和start的调用线程应该是不一致的，下面是一个调用的例子：
     *
     * 创建服务器的线程
     * new Thread("ServerThread", ()->{
     *     server.start();
     * }).start(); // 该线程阻塞
     *
     * 当UI线程或者什么其它接收用户输入的线程接收到关闭服务器的指令：
     * server.stop(); // ServerThread结束
     *
     * 如果有其它线程正在调用start，当前调用线程可能会阻塞，并且如果其它线程调用成功，当前线程会收到CannotRestartVPadServerException
     */
    void start();

    /**
     * 关闭VPadServer
     * 此操作取消0.0.0.0:1236上的绑定，会让所有连接到服务器的Client断开
     * 如果关闭失败，抛出VPadServerStopFaildException，并说明原因
     *
     * 关闭操作可能会阻塞一个稍短的时间，因为它要处理所有的资源，这取决于具体的实现
     *
     * 关闭操作会导致start方法的阻塞结束
     * 如果一个线程正在start方法的绑定服务阶段（尚未进入已经绑定后的阻塞阶段），另一个线程的stop调用将阻塞，直到它绑定完成
     */
    void stop();

    /**
     * 该方法返回VPadServer是否已经启动
     * 仅当start方法已经把服务器绑定到0.0.0.0:1236时，该方法返回true
     * 当stop方法成功将服务器从0.0.0.0:1236上解绑或从未绑定到此地址时，该方法返回false
     * 该方法应该是并发安全的
     *
     * @return 如果VPadServer已经启动，返回true，否则返回false
     */
    boolean isStarted();

    /**
     * 该方法返回当前连接到VPadServer的客户端数量，如果isStarted() == false，返回0
     * 该方法不要求一定并发安全，就是说有些线程可能会读到过时的数据，这无所谓
     *
     * 实现类甚至可以不实现该方法
     * @return 当前有多少已连接客户端，返回一个参考值
     */
    int connections();

    /**
     * 添加Server启动回调
     * 添加进去的Consumer对象将在Server启动后被accept
     * 回调的调用过程中不应该有其它线程改变Server的状态，所以回调调用应该在start方法绑定服务的过程中
     * 所以回调不应该做太耗时的操作，这会导致其它想要调用start和stop的线程的长时间阻塞
     * 如果callback==null，抛出IllegalArgumentException
     *
     * 该方法需要是同步的
     * @return 为了支持链式调用而返回的this对象
     */
    VPadServer addServerStartCallback(Consumer<VPadServer> callback);
    /**
     * 添加Server关闭回调
     * 添加进去的Consumer对象将在Server关闭后被accept
     * 回调的调用过程中不应该有其它线程改变Server的状态
     * 所以回调不应该做太耗时的操作，这会导致其它想要调用start和stop的线程的长时间阻塞
     * 如果callback==null，抛出IllegalArgumentException
     *
     * 该方法需要是同步的
     * @return 为了支持链式调用而返回的this对象
     */
    VPadServer addServerStopCallback(Consumer<VPadServer> callback);

    /**
     * 获取MessageHandler注册处
     * @return
     */
    MessageHandlerRegistry messageHandlerRegistry();

    /**
     * 获取MidiDeviceHolder
     * 其实应该提供一套自己的MidiDevice接口以避免和javax.sound.midi耦合
     * 但是工作量巨大，所以...哎哎哎...
     * @return
     */
    MidiDeviceHolder midiDeviceHolder();

}
