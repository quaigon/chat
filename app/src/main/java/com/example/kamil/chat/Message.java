package com.example.kamil.chat;

public class Message {
    public String nickname;
    public String text;
    public String date;

    public Message(String nickname, String text, String date) {
        this.nickname = nickname;
        this.text = text;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getNickname() {
        return nickname;
    }

    public String getText() {
        return text;
    }
}
