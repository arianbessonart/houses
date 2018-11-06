package smt.ort.houses.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class FavoriteBodyRequest {

    @NonNull
    @SerializedName("InmuebleId")
    private String id;

    public FavoriteBodyRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
