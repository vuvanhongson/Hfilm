package com.example.hfilm.data.model;
//public class SOAnswersResponseRegister {

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SOAnswersResponseRegister {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("data")
    @Expose
    private User data;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}