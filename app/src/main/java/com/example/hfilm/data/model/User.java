package com.example.hfilm.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

        public class User {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("full_name")
            @Expose
            private String fullName;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("birthday")
            @Expose
            private String birthday;
            @SerializedName("facebook_id")
            @Expose
            private String facebookId;
            @SerializedName("google_id")
            @Expose
            private String googleId;
            @SerializedName("access_token")
            @Expose
            private String accessToken;
            @SerializedName("created_at")
            @Expose
            private String createdAt;
            @SerializedName("updated_at")
            @Expose
            private String updatedAt;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getFacebookId() {
                return facebookId;
            }

            public void setFacebookId(String facebookId) {
                this.facebookId = facebookId;
            }

            public String getGoogleId() {
                return googleId;
            }

            public void setGoogleId(String googleId) {
                this.googleId = googleId;
            }

            public String getAccessToken() {
                return accessToken;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            @Override
            public String toString() {
                return "User{" +
                        "id='" + id + '\'' +
                        ", email='" + email + '\'' +
                        ", password='" + password + '\'' +
                        ", fullName='" + fullName + '\'' +
                        ", gender='" + gender + '\'' +
                        ", birthday='" + birthday + '\'' +
                        ", facebookId='" + facebookId + '\'' +
                        ", googleId='" + googleId + '\'' +
                        ", accessToken='" + accessToken + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", updatedAt='" + updatedAt + '\'' +
                        '}';
            }
        }