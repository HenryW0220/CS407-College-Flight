package com.example.collegeflight.bean;
public class ChatMessage {
    public static final int SEND = 1;
    public static final int RECEIVE = 2;

    private int type;
    private String message;

    public ChatMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }
    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
