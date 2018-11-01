package smt.ort.houses.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseHouses {

    @SerializedName("Response")
    private List<House> list;

    public List<House> getList() {
        return list;
    }

    public void setList(List<House> list) {
        this.list = list;
    }
}
