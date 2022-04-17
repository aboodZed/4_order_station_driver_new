package com.AbdUlla.a4_order_station_driver.models;

public class ChatMessage {
    private String text;
    private int sender_id;
    private String sender_name;
    private String sender_avatar_url;
    private double time;
    private String imageUrl;
    private boolean isDriver;

    public ChatMessage() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_avatar_url() {
        return sender_avatar_url;
    }

    public void setSender_avatar_url(String sender_avatar_url) {
        this.sender_avatar_url = sender_avatar_url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(boolean driver) {
        isDriver = driver;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "text='" + text + '\'' +
                ", sender_id=" + sender_id +
                ", sender_name='" + sender_name + '\'' +
                ", sender_avatar_url='" + sender_avatar_url + '\'' +
                ", time=" + time +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
