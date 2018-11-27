package smt.ort.houses.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LoginBodyRequest {

    @NonNull
    @SerializedName("email")
    private String email;

    public LoginBodyRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
