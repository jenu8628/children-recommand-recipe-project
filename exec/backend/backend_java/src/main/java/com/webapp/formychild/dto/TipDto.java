package com.webapp.formychild.dto;

import java.util.Arrays;

public class TipDto {
    private String tip_id;      // 소식 아이디
    private String tip_title;   // 소식 제목
    private String tip_content; // 소식 내용
    private byte[] tip_media;   // 소식 미디어(사진, 동영상)

    public TipDto() {
    }

    public TipDto(String tip_id, String tip_title, String tip_content, byte[] tip_media) {
        this.tip_id = tip_id;
        this.tip_title = tip_title;
        this.tip_content = tip_content;
        this.tip_media = tip_media;
    }

    public String getTip_id() {
        return tip_id;
    }

    public void setTip_id(String tip_id) {
        this.tip_id = tip_id;
    }

    public String getTip_title() {
        return tip_title;
    }

    public void setTip_title(String tip_title) {
        this.tip_title = tip_title;
    }

    public String getTip_content() {
        return tip_content;
    }

    public void setTip_content(String tip_content) {
        this.tip_content = tip_content;
    }

    public byte[] getTip_media() {
        return tip_media;
    }

    public void setTip_media(byte[] tip_media) {
        this.tip_media = tip_media;
    }

    @Override
    public String toString() {
        return "TipDto{" +
                "tip_id='" + tip_id + '\'' +
                ", tip_title='" + tip_title + '\'' +
                ", tip_content='" + tip_content + '\'' +
                ", tip_media=" + Arrays.toString(tip_media) +
                '}';
    }
}
