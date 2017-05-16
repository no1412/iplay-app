package com.ll.iplay.gson;

/**
 * Created by ll on 2017/5/3.
 */

public class FoodDescribe {

    /**
     * nickName : 张三哈哈哈
     * headPicUrl : http://www.baidu.com/img/bd_logo1.png
     * id : 1
     * content : 嫩嗯嗯嗯我也这样觉得
     * title : 这是一道比较好吃的美食
     * sendTime : 2017-05-11  10:34:00
     * publishTime : 1494470040000
     * foodSurface : http://localhost:8080/iplay/static/img/food/avatar.png
     */

    private String nickName;
    private String headPicUrl;
    private String id;
    private String content;
    private String title;
    private String sendTime;
    private long publishTime;
    private String foodSurface;

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

    public String getFoodSurface() {
        return foodSurface;
    }

    public void setFoodSurface(String foodSurface) {
        this.foodSurface = foodSurface;
    }
}
