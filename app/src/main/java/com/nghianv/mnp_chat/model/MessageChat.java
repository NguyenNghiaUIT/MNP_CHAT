package com.nghianv.mnp_chat.model;

/**
 * Created by NguyenNghia on 4/2/2016.
 */
public class MessageChat {
    private String mMessage;
    private boolean isMine;

    public MessageChat(){
    }

    public MessageChat(String message, boolean isMine) {
        this.mMessage = message;
        this.isMine = isMine;
    }

    public String getMessage(){
        return this.mMessage;
    }

    public boolean getIsMine(){
        return this.isMine;
    }


}
