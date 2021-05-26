package com.webapp.formychild.dto;

public class MessageDto {
    private String message_id;          // 메세지 아이디
    private String message_receiverId;  // 받는사람 아이디
    private String message_sendDate;    // 메세지 보낸 날짜
    private int message_flag;           // 읽음 여부
    private String message_recipeId;    // 아이가 보낸 레시피 아이디
    private String message_messageRoomHashId;   // 방번호
    private String message_senderId;        // 보낸사람
    private String message_content;         // 메세지

    public MessageDto() {
    }

    public MessageDto(String message_id, String message_senderId, String message_receiverId, String message_content, String message_sendDate, int message_flag, String message_recipeId) {
        this.message_id = message_id;
        this.message_senderId = message_senderId;
        this.message_receiverId = message_receiverId;
        this.message_content = message_content;
        this.message_sendDate = message_sendDate;
        this.message_flag = message_flag;
        this.message_recipeId = message_recipeId;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getMessage_senderId() {
        return message_senderId;
    }

    public void setMessage_senderId(String message_senderId) {
        this.message_senderId = message_senderId;
    }

    public String getMessage_receiverId() {
        return message_receiverId;
    }

    public void setMessage_receiverId(String message_receiverId) {
        this.message_receiverId = message_receiverId;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getMessage_sendDate() { return message_sendDate; }

    public void setMessage_sendDate(String message_sendDate) { this.message_sendDate = message_sendDate; }

    public int getMessage_flag() {
        return message_flag;
    }

    public void setMessage_flag(int message_flag) {
        this.message_flag = message_flag;
    }

    public String getMessage_recipeId() { return message_recipeId; }

    public void setMessage_recipeId(String message_recipeId) { this.message_recipeId = message_recipeId; }

    public String getMessage_messageRoomHashId() {
        return message_messageRoomHashId;
    }

    public void setMessage_messageRoomHashId(String message_messageRoomHashId) {
        this.message_messageRoomHashId = message_messageRoomHashId;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "message_id='" + message_id + '\'' +
                ", message_senderId='" + message_senderId + '\'' +
                ", message_receiverId='" + message_receiverId + '\'' +
                ", message_content='" + message_content + '\'' +
                ", message_flag=" + message_flag +
                '}';
    }
}
