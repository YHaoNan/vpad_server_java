package top.yudoge.vpadserverj.messagehandler;

import top.yudoge.vpadserverj.exceptions.DuplicateMessageHandlerException;
import top.yudoge.vpadserverj.exceptions.MessageHandlerNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class HashMapMessageHandlerRegistry implements MessageHandlerRegistry {

    private final Map<Integer, MessageHandler> handlerMap;

    public HashMapMessageHandlerRegistry(){
        this.handlerMap = new HashMap<>();
    }

    @Override
    public MessageHandlerRegistry registe(MessageHandler handler) {
        if (this.handlerMap.containsKey(handler.messageTypeCanHandle()))
            throw new DuplicateMessageHandlerException("There already has a handler for operation type : " + handler.messageTypeCanHandle() + ", you can not add again!");

        this.handlerMap.put(handler.messageTypeCanHandle(), handler);
        return this;
    }

    @Override
    public MessageHandler get(int operation) {
        if (!this.handlerMap.containsKey(operation))
            throw new MessageHandlerNotFoundException("Can not found an message handler can handle operation type : " + operation);

        return this.handlerMap.get(operation);
    }

}
