package com.webapp.formychild.service;

import com.webapp.formychild.dao.MessageDaoImpl;
import com.webapp.formychild.dto.MessageDto;
import com.webapp.formychild.dto.MessageRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDaoImpl messageDao;

    private final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    /***** 받은 메세지 목록 조회하기 *****/
    @Override
    public List<MessageDto> getAllMyReceiveMessage(String message_receiverId) throws Exception {
        return messageDao.getAllMyReceiveMessage(message_receiverId);
    }

    /***** 전체 메세지 목록 시간순 조회하기 *****/
    @Override
    public List<MessageDto> getAllMyMessage(String message_receiverId) throws Exception {
        return messageDao.getAllMyMessage(message_receiverId);
    }

    /***** 메세지 읽음 처리 *****/
    @Override
    public void checkReadMessage(String message_id) throws Exception {
        messageDao.checkReadMessage(message_id);
    }

    // 웹소켓 실시간 채팅

    /***** 채팅방 개설 *****/
    @Override
    public void addMessageRoom(MessageRoomDto messageRoom) throws Exception {
        messageDao.addMessageRoom(messageRoom);
    }

    @Override
    public List<MessageRoomDto> findAllMessageRoom() throws Exception {
        return messageDao.findAllMessageRoom();
    }

    @Override
    public MessageRoomDto findMessageRoomById(String messageRoom_hashId) throws Exception {
        return messageDao.findMessageRoomById(messageRoom_hashId);
    }

    /***** 메세지 보내기 *****/
    @Override
    public int sendMessage(MessageDto message) throws Exception {
        return messageDao.sendMessage(message);
    }

    /***** 채팅방 기록 조회 *****/
    @Override
    public List<MessageDto> getMessages(String message_messageRoomHashId) throws Exception {
        return messageDao.getMessages(message_messageRoomHashId);
    }

    /***** 마지막으로 보낸 채팅기록 반환 *****/
    @Override
    public MessageDto getLastMessageBySenderId(MessageDto message) throws Exception {
        return messageDao.getLastMessageBySenderId(message);
    }

    /***** 마지막으로 보낸 채팅기록 반환 *****/
    @Override
    public String getMessageRoomHashId(MessageRoomDto messageRoom) throws Exception {
        return messageDao.getMessageRoomHashId(messageRoom);
    }

    /***** 부모의 아이들과의 채팅방 반환 *****/
    @Override
    public List<MessageRoomDto> getMessageRoomMyChildren(String messageRoom_parentId) throws Exception {
        return messageDao.getMessageRoomMyChildren(messageRoom_parentId);
    }

    /***** 읽음 표시 *****/
    @Override
    public void readMessage(MessageDto message) throws Exception {
        messageDao.readMessage(message);
    }

    /***** 읽지않은 모든 메세지 수 반환 *****/
    @Override
    public int getAllUnreadMessage(String message_receiverId) throws Exception {
        return messageDao.getAllUnreadMessage(message_receiverId);
    }

    /***** (채팅방 별)읽지않은 메세지 수 반환 *****/
    @Override
    public int getUnreadMessage(MessageDto message) throws Exception {
        return messageDao.getUnreadMessage(message);
    }


}
