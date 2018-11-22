package smt.ort.houses.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFavorites {

    @SerializedName("Response")
    private List<Favorite> list;

    public List<Favorite> getList() {
        return list;
    }

    public void setList(List<Favorite> list) {
        this.list = list;
    }
}
