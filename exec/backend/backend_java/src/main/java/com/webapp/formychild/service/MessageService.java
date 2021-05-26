package com.webapp.formychild.service;

import com.webapp.formychild.dto.MessageDto;
import com.webapp.formychild.dto.MessageRoomDto;

import java.util.List;

public interface MessageService {

    /***** 받은 메세지 목록 조회하기 *****/
    List<MessageDto> getAllMyReceiveMessage(String message_receiverId) throws Exception;

    /***** 전체 메세지 목록 시간순 조회하기 *****/
    List<MessageDto> getAllMyMessage(String message_receiverId) throws Exception;

    /***** 메세지 읽음 처리 *****/
    void checkReadMessage(String message_id) throws Exception;

    // 웹소켓 채팅

    /***** 채팅방 개설 *****/
    void addMessageRoom(MessageRoomDto messageRoom) throws Exception;

    /***** 모든 채팅방 반환 *****/
    List<MessageRoomDto> findAllMessageRoom() throws Exception;

    /***** 채팅방 아이디로 채팅방 검색 *****/
    MessageRoomDto findMessageRoomById(String messageRoom_hashId) throws Exception;

    /***** 메세지 보내기 *****/
    int sendMessage(MessageDto message) throws Exception;

    /***** 채팅방 기록 조회 *****/
    List<MessageDto> getMessages(String message_messageRoomHashId) throws Exception;

    /***** 마지막으로 보낸 채팅기록 반환 *****/
    MessageDto getLastMessageBySenderId(MessageDto message) throws Exception;

    /***** 부모, 아이 아이디로 채팅방 해시아이디 반환 *****/
    String getMessageRoomHashId(MessageRoomDto messageRoom) throws Exception;

    /***** 부모의 아이들과의 채팅방 반환 *****/
    List<MessageRoomDto> getMessageRoomMyChildren(String messageRoom_parentId) throws Exception;

    /***** 읽음 표시 *****/
    void readMessage(MessageDto message) throws Exception;

    /***** 읽지않은 모든 메세지 수 반환 *****/
    int getAllUnreadMessage(String message_receiverId) throws Exception;

    /***** (채팅방 별)읽지않은 메세지 수 반환 *****/
    int getUnreadMessage(MessageDto message) throws Exception;
}
