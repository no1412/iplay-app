package com.ll.iplay.model;

import java.util.Date;

/**
 * Created by ll on 2017/5/2.
 */

public class Food {
    private int foodSurface;
    private int userHead;
    private String nickName;
    private String title;
    private String content;
    private Date publishTime;

    public Food(int foodSurface, String title, int userHead, String nickName) {
        this.foodSurface = foodSurface;
        this.userHead = userHead;
        this.nickName = nickName;
        this.title = title;
    }

    public int getFoodSurface() {
        return foodSurface;
    }

    public void setFoodSurface(int foodSurface) {
        this.foodSurface = foodSurface;
    }

    public int getUserHead() {
        return userHead;
    }

    public void setUserHead(int userHead) {
        this.userHead = userHead;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
