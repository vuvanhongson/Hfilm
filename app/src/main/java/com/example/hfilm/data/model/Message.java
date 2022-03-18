package com.example.hfilm.data.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

        @SerializedName("email")
        @Expose
        private String email = null;
        @SerializedName("password")
        @Expose
        private String password = null;
        @SerializedName("full_name")
        @Expose
        private String fullName = null;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

    }
