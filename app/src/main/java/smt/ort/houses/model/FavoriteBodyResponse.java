package smt.ort.houses.model;

import com.google.gson.annotations.SerializedName;

public class FavoriteBodyResponse {

    @SerializedName("Resultado")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
