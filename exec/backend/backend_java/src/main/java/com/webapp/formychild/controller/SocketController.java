package com.webapp.formychild.controller;

import com.webapp.formychild.dto.MessageDto;
import com.webapp.formychild.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

//@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class SocketController {

    @Autowired
    private MessageService messageService;

    private final SimpMessagingTemplate msgTemplate;

    @Autowired
    public SocketController(SimpMessagingTemplate msgTemplate){
        this.msgTemplate = msgTemplate;
    }

    final private Logger logger = LoggerFactory.getLogger(SocketController.class);

    @MessageMapping("/message/send")
    public void SocketHandler(MessageDto message) throws Exception {
//        Thread.sleep(1000);
        MessageDto result = null;

        try {
            messageService.sendMessage(message);
            result = messageService.getLastMessageBySenderId(message);

        } catch (Exception e) {
            logger.error("error : {}", e);
        }

        msgTemplate.convertAndSend("/sub/" + message.getMessage_messageRoomHashId(), result);
    }
}
