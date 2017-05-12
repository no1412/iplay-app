package com.ll.iplay.gson;

/**
 * Created by ll on 2017/5/11.
 */

public class User {

    /**
     * id : 2
     * errorCode : 1
     * password : 123456
     * headPicUrl : http://192.168.191.1:8080/iplay/http://192.168.191.1:8080/iplay/
     * nickName : sakura
     * email :
     * phoneNumber : 123456
     */

    private int id;
    private String responseCode;
    private String password;
    private String headPicUrl;
    private String nickName;
    private String email;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
