package com.ll.iplay.gson;

/**
 * Created by ll on 2017/5/5.
 */

public class EntertainmentDescribe {

    /**
     * nickName : 张三哈哈哈
     * headPicUrl : http://192.168.191.1:8080/iplay/static/img/user/head.jpg
     * id : 1
     * content : 嫩嫩按时都能看
     * title : 熬好玩的耶
     * sendTime : 2017-05-03  10:35:04
     * publishTime : 1493778904000
     * entertainmentSurface : http://192.168.191.1:8080/iplay/static/img/entertainment/
     */

    private String nickName;
    private String headPicUrl;
    private String id;
    private String content;
    private String title;
    private String sendTime;
    private long publishTime;
    private String entertainmentSurface;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getEntertainmentSurface() {
        return entertainmentSurface;
    }

    public void setEntertainmentSurface(String entertainmentSurface) {
        this.entertainmentSurface = entertainmentSurface;
    }
}
