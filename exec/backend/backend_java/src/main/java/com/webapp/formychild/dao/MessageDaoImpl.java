package com.webapp.formychild.dao;

import com.webapp.formychild.dto.MessageDto;
import com.webapp.formychild.dto.MessageRoomDto;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;


@Repository
public class MessageDaoImpl {
    String ns = "com.webapp.formychild.dao.messageDao.";

    final private Logger logger = LoggerFactory.getLogger(MessageDaoImpl.class);

    @Autowired
    private SqlSession sqlSession;

    /***** 받은 메세지 목록 조회하기 *****/
    public List<MessageDto> getAllMyReceiveMessage(String message_receiverId) {
        System.out.println("받은 메세지 목록 조회");
        return sqlSession.selectList(ns + "getAllMyReceiveMessage", message_receiverId);
    }

    /***** 전체 메세지 목록 시간순 조회하기 *****/
    public List<MessageDto> getAllMyMessage(String message_receiverId) {
        return sqlSession.selectList(ns + "getAllMyMessage", message_receiverId);
    }

    /***** 메세지 읽음 처리 *****/
    public void checkReadMessage(String message_id) {
        sqlSession.update(ns + "checkReadMessage", message_id);
    }

    // 웹소켓 채팅

    /***** 채팅방 개설 *****/
    public void addMessageRoom(MessageRoomDto messageRoom) {
        sqlSession.insert(ns + "addMessageRoom", messageRoom);
    }

    public List<MessageRoomDto> findAllMessageRoom() {

        return sqlSession.selectList(ns + "findAllMessageRoom");
    }

    public MessageRoomDto findMessageRoomById(String messageRoom_hashId) {
        return sqlSession.selectOne(ns + "findMessageRoomById", messageRoom_hashId);
    }

    /***** 메세지 보내기 *****/
    public int sendMessage(MessageDto message) {
        return sqlSession.insert(ns + "sendMessage", message);
    }

    /***** 채팅방 기록 조회 *****/
    public List<MessageDto> getMessages(String message_messageRoomHashId) {
        return sqlSession.selectList(ns+"getMessages", message_messageRoomHashId);
    }

    /***** 마지막으로 보낸 채팅기록 반환 *****/
    public MessageDto getLastMessageBySenderId(MessageDto message) {
        return sqlSession.selectOne(ns+"getLastMessageBySenderId", message);
    }

    /***** 마지막으로 보낸 채팅기록 반환 *****/
    public String getMessageRoomHashId(MessageRoomDto messageRoom) {
        return sqlSession.selectOne(ns+"getMessageRoomHashId", messageRoom);
    }

    /***** 부모의 아이들과의 채팅방 반환 *****/
    public List<MessageRoomDto> getMessageRoomMyChildren(String messageRoom_parentId) {
        return sqlSession.selectList(ns+"getMessageRoomMyChildren", messageRoom_parentId);
    }

    /***** 읽음 표시 *****/
    public void readMessage(MessageDto message) {
        sqlSession.update(ns+"readMessage", message);
    }

    /***** 읽지않은 모든 메세지 수 반환 *****/
    public int getAllUnreadMessage(String message_receiverId) {
        return sqlSession.selectOne(ns+"getAllUnreadMessage", message_receiverId);
    }

    /***** (채팅방 별)읽지않은 메세지 수 반환 *****/
    public int getUnreadMessage(MessageDto message) {
        return sqlSession.selectOne(ns+"getUnreadMessage", message);
    }
}
