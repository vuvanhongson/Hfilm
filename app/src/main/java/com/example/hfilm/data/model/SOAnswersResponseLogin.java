package com.example.hfilm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class SOAnswersResponseLogin {

        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private User data;

        public Boolean getError() {
            return error;
        }

        public void setError(Boolean error) {
            this.error = error;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public User getData() {
            return data;
        }

        public void setData(User data) {
            this.data = data;
        }

    }