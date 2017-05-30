package com.touna.tcc.demo.trading.web.bean;

/**
 * Created by yumo on 16/8/30.
 */
public class Response {

    public Response(String message){
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}