package com.webapp.formychild.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class MyChannelInterceptor implements ChannelInterceptor {

    final private Logger logger = LoggerFactory.getLogger(MyChannelInterceptor.class);

    @Override
    public Message preSend(Message<?> message, MessageChannel channel){

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        if(command.compareTo(StompCommand.SUBSCRIBE) == 0){
            String destination = accessor.getDestination();

            logger.info("구독 주소 : ", destination);
            logger.info("message : ",message);
        }else if(command.compareTo(StompCommand.CONNECT) == 0){
            logger.info("사용자 연결");
        }else if(command.compareTo(StompCommand.DISCONNECT) == 0){
            logger.info("사용자 연결 해제");
        }

        return message;
    }
}
