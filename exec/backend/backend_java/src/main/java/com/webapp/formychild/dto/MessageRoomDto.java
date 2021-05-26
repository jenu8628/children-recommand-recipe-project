package com.webapp.formychild.dto;

public class MessageRoomDto {
    private String messageRoom_id;
    private String messageRoom_hashId;
    private String messageRoom_parentId;
    private String messageRoom_childId;

    public MessageRoomDto() {
    }

    public MessageRoomDto(String messageRoom_id, String messageRoom_hashId, String messageRoom_parentId, String messageRoom_childId) {
        this.messageRoom_id = messageRoom_id;
        this.messageRoom_hashId = messageRoom_hashId;
        this.messageRoom_parentId = messageRoom_parentId;
        this.messageRoom_childId = messageRoom_childId;
    }

    public String getMessageRoom_id() {
        return messageRoom_id;
    }

    public void setMessageRoom_id(String messageRoom_id) {
        this.messageRoom_id = messageRoom_id;
    }

    public String getMessageRoom_hashId() {
        return messageRoom_hashId;
    }

    public void setMessageRoom_hashId(String messageRoom_hashId) {
        this.messageRoom_hashId = messageRoom_hashId;
    }

    public String getMessageRoom_parentId() {
        return messageRoom_parentId;
    }

    public void setMessageRoom_parentId(String messageRoom_parentId) {
        this.messageRoom_parentId = messageRoom_parentId;
    }

    public String getMessageRoom_childId() {
        return messageRoom_childId;
    }

    public void setMessageRoom_childId(String messageRoom_childId) {
        this.messageRoom_childId = messageRoom_childId;
    }

    @Override
    public String toString() {
        return "MessageRoomDto{" +
                "messageRoom_id='" + messageRoom_id + '\'' +
                ", messageRoom_hashId='" + messageRoom_hashId + '\'' +
                ", messageRoom_parentId='" + messageRoom_parentId + '\'' +
                ", messageRoom_childId='" + messageRoom_childId + '\'' +
                '}';
    }
}
