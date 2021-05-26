package com.webapp.formychild.controller;

import com.webapp.formychild.dto.MessageDto;
import com.webapp.formychild.dto.MessageRoomDto;
import com.webapp.formychild.help.NumberResult;
import com.webapp.formychild.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/api/message")
@Api(value = "Message Controller", description = "Message Controller")
public class MessageController {

    @Autowired
    private MessageService messageService;

    final private Logger logger = LoggerFactory.getLogger(MessageController.class);

    /***** 받은 메세지 목록 조회하기 *****/
    @ApiOperation(value = "[받은 메세지 조회] 해당 사용자가 받은 메세지 목록을 조회한다.", response = List.class, notes = "getAllMyReceiveMessage(String message_receiverId)")
    @RequestMapping(value = "/getAllMyReceiveMessage", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getAllMyReceiveMessage(@RequestParam(value = "message_receiverId") String message_receiverId) throws Exception {
        List<MessageDto> messages = null;
        HttpStatus status = null;
        try {
            messages = messageService.getAllMyReceiveMessage(message_receiverId);

            if (!messages.isEmpty()) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<List<MessageDto>>(messages, status);
    }

    /***** 전체 메세지 목록 시간순 조회하기 *****/
    @ApiOperation(value = "[전체 메세지 시간 순 조회] 해당 사용자가 받고 보낸 메세지 목록을 시간순으로 조회한다.", response = List.class,
            notes = "getAllMyMessage(String message_receiverId)")
    @RequestMapping(value = "/getAllMyMessage", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getAllMyMessage(@RequestParam(value = "message_receiverId") String message_receiverId) throws Exception {
        List<MessageDto> messages = null;
        HttpStatus status = null;
        try {
            messages = messageService.getAllMyMessage(message_receiverId);

            if (!messages.isEmpty()) {
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<List<MessageDto>>(messages, status);
    }

    /***** 메세지 읽음 처리하기 *****/
    @ApiOperation(value = "메세지 읽음 처리", response = NumberResult.class, notes = "checkReadMessage(int message_id)")
    @RequestMapping(value = "/checkReadMessage", method = RequestMethod.POST)
    private ResponseEntity<NumberResult> checkReadMessage(@RequestBody String message_id) {
        HttpStatus status = null;
        NumberResult ns = new NumberResult();

        try {
            messageService.checkReadMessage(message_id);
            ns.setValue("메세지를 읽음 처리 하였습니다", 1, "succ");
            status = HttpStatus.OK;
        } catch (Exception e) {
            ns.setValue("메세지를 읽음 처리에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<NumberResult>(ns, status);
    }

    // 웹 소켓 채팅

    /***** 채팅방 개설 *****/
    @ApiOperation(value = "웹소켓 채팅방 개설", response = MessageRoomDto.class)
    @RequestMapping(value = "/addMessageRoom", method = RequestMethod.POST)
    public ResponseEntity<MessageRoomDto> addMessageRoom(@RequestParam(value = "messageRoom_parentId") String messageRoom_parentId,
                                                         @RequestParam(value = "messageRoom_childId") String messageRoom_childId) {
        HttpStatus status = null;
        MessageRoomDto messageRoom = null;

        try {
            messageRoom.setMessageRoom_hashId(UUID.randomUUID().toString());
            messageRoom.setMessageRoom_parentId(messageRoom_parentId);
            messageRoom.setMessageRoom_childId(messageRoom_childId);

            messageService.addMessageRoom(messageRoom);

            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(messageRoom, status);
    }

    @ApiOperation(value = "모든 채팅방 반환", response = List.class)
    @RequestMapping(value = "/findAllMessageRoom", method = RequestMethod.GET)
    public ResponseEntity<List<MessageRoomDto>> findAllMessageRoom() {
        HttpStatus status = null;
        List<MessageRoomDto> messageRooms = null;

        try {
            messageRooms = messageService.findAllMessageRoom();

            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(messageRooms, status);
    }

    @ApiOperation(value = "특정 채팅방 조회", response = MessageRoomDto.class)
    @RequestMapping(value = "/findMessageRoomById/{messageRoom_hashId}", method = RequestMethod.GET)
    public ResponseEntity<MessageRoomDto> findMessageRoomById(@PathVariable("messageRoom_hashId") String messageRoom_hashId) {
        HttpStatus status = null;
        MessageRoomDto messageRoom = null;

        try {
            messageRoom = messageService.findMessageRoomById(messageRoom_hashId);

            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(messageRoom, status);
    }

    @ApiOperation(value = "채팅방 기록 조회", response = List.class)
    @RequestMapping(value = "/getMessages/{message_messageRoomHashId}", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable("message_messageRoomHashId") String message_messageRoomHashId) {
        HttpStatus status = null;
        List<MessageDto> messages = null;

        try {
            messages = messageService.getMessages(message_messageRoomHashId);

            status = HttpStatus.OK;
        } catch (Exception e) {
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(messages, status);
    }

    @ApiOperation(value = "부모와 아이 아이디로 채팅방 해시아이디 반환", response = String.class)
    @RequestMapping(value = "/getMessageRoomHashId", method = RequestMethod.GET)
    public ResponseEntity<String> getMessageRoomHashId(@RequestParam(value = "messageRoom_parentId") String messageRoom_parentId,
                                                       @RequestParam(value = "messageRoom_childId") String messageRoom_childId){
        HttpStatus status = null;
        MessageRoomDto messageRoom = new MessageRoomDto();
        String messageRoom_hashId = null;

        try {
            messageRoom.setMessageRoom_parentId(messageRoom_parentId);
            messageRoom.setMessageRoom_childId(messageRoom_childId);

            messageRoom_hashId = messageService.getMessageRoomHashId(messageRoom);

            status = HttpStatus.OK;
        }catch (Exception e){
            logger.error("error : {}", e);
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(messageRoom_hashId, status);
    }

    @ApiOperation(value = "아이들과의 채팅방 목록 반환", response = List.class)
    @RequestMapping(value = "/getMessageRoomMyChildren", method = RequestMethod.GET)
    public ResponseEntity<List<MessageRoomDto>> getMessageRooMyChildren(@RequestParam(value = "messageRoom_parentId") String messageRoom_parentId){
        HttpStatus status = null;
        List<MessageRoomDto> messageRooms = null;

        try {

            messageRooms = messageService.getMessageRoomMyChildren(messageRoom_parentId);

            status = HttpStatus.OK;

        }catch (Exception e){
            logger.error("error : {}",e);
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(messageRooms, status);
    }

    @ApiOperation(value = "읽음 여부 표시", response = NumberResult.class)
    @RequestMapping(value = "/readMessage", method = RequestMethod.PUT)
    public ResponseEntity<NumberResult> readMessage(@RequestParam(value = "message_messageRoomHashId") String message_messageRoomHashId,
                                                    @RequestParam(value = "message_receiverId") String message_receiverId,
                                                    @RequestParam(value = "message_sendDate") String message_sendDate){
        HttpStatus status = null;
        NumberResult ns = new NumberResult();
        MessageDto message = new MessageDto();

        try {
            message.setMessage_messageRoomHashId(message_messageRoomHashId);
            message.setMessage_receiverId(message_receiverId);
            message.setMessage_sendDate(message_sendDate);

            messageService.readMessage(message);

            ns.setValue("읽음 업데이트에 성공했습니다..",1,"succ");
            status = HttpStatus.OK;
        }catch (Exception e){
            logger.error("error : {}", e);

            ns.setValue("읽음 업데이트에 실패했습니다.",0,"fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }

    @ApiOperation(value = "읽지 않은 모든 메세지 수 반환", response = Integer.class)
    @RequestMapping(value = "getAllUnreadMessage", method = RequestMethod.GET)
    public ResponseEntity<Integer> getAllUnreadMessage(@RequestParam(value = "message_receiverId") String message_receiverId){
        HttpStatus status = null;
        int unread = 0;

        try {

            unread = messageService.getAllUnreadMessage(message_receiverId);

            status = HttpStatus.OK;

        }catch (Exception e){
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(unread, status);
    }

    @ApiOperation(value = "(채팅방별)읽지 않은 메세지 수 반환", response = Integer.class)
    @RequestMapping(value = "getUnreadMessage", method = RequestMethod.GET)
    public ResponseEntity<Integer> getUnreadMessage(@RequestParam(value = "message_messageRoomHashId") String message_messageRoomHashId,
                                                    @RequestParam(value = "message_receiverId") String message_receiverId){
        HttpStatus status = null;
        MessageDto message = new MessageDto();
        int unread = 0;

        try {
            message.setMessage_messageRoomHashId(message_messageRoomHashId);
            message.setMessage_receiverId(message_receiverId);

            unread = messageService.getUnreadMessage(message);

            status = HttpStatus.OK;

        }catch (Exception e){
            logger.error("error : {}", e);

            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(unread, status);
    }

    @ApiOperation(value = "먹고싶어요(채팅방 전송)", response = NumberResult.class)
    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    public ResponseEntity<NumberResult> sendMessage(@RequestParam(value = "message_senderId") String message_senderId,
                                                    @RequestParam(value = "message_receiverId") String message_receiverId,
                                                    @RequestParam(value = "message_content") String message_content){
        NumberResult ns = new NumberResult();
        HttpStatus status = null;
        MessageDto message = new MessageDto();
        MessageRoomDto messageRoom = new MessageRoomDto();
        String messageRoom_hashId = null;

        try {
            messageRoom.setMessageRoom_parentId(message_receiverId);
            messageRoom.setMessageRoom_childId(message_senderId);

            messageRoom_hashId = messageService.getMessageRoomHashId(messageRoom);

            message.setMessage_messageRoomHashId(messageRoom_hashId);
            message.setMessage_senderId(message_senderId);
            message.setMessage_receiverId(message_receiverId);
            message.setMessage_content(message_content);

            messageService.sendMessage(message);

            ns.setValue("먹고싶어요 전송에 성공했습니다.", 1, "succ");
            status = HttpStatus.OK;

        } catch (Exception e){
            logger.error("error : {}", e);

            ns.setValue("먹고싶어요 전송에 실패했습니다.", 0, "fail");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(ns, status);
    }
}
