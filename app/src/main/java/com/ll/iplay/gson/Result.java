package com.ll.iplay.gson;

/**
 * Created by ll on 2017/5/23.
 */

public class Result {

    /**
     * status : 0
     * tipCode :
     * tipMsg : ""
     */

    private String status;
    private String tipCode;
    private String tipMsg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipCode() {
        return tipCode;
    }

    public void setTipCode(String tipCode) {
        this.tipCode = tipCode;
    }

    public String getTipMsg() {
        return tipMsg;
    }

    public void setTipMsg(String tipMsg) {
        this.tipMsg = tipMsg;
    }
}
