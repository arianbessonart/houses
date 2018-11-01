package smt.ort.houses.model;

import com.google.gson.annotations.SerializedName;

public class HouseFilters {

    @SerializedName("MaxResults")
    private Integer maxResults = 20;

    @SerializedName("Titulo")
    private String title;

    @SerializedName("Barrio")
    private String neighborhood;

    @SerializedName("Precio")
    private String price = String.valueOf(Integer.MAX_VALUE);

    @SerializedName("CantDormitorio")
    private String rooms;

    @SerializedName("TieneParrillero")
    private Boolean hasBarbeque;

    @SerializedName("TieneGarage")
    private Boolean hasGarage;

    @SerializedName("TieneBalcon")
    private Boolean hasBalcony;

    @SerializedName("TienePatio")
    private Boolean hasGarden;

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public Boolean getHasBarbeque() {
        return hasBarbeque;
    }

    public void setHasBarbeque(Boolean hasBarbeque) {
        this.hasBarbeque = hasBarbeque;
    }

    public Boolean getHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(Boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public Boolean getHasBalcony() {
        return hasBalcony;
    }

    public void setHasBalcony(Boolean hasBalcony) {
        this.hasBalcony = hasBalcony;
    }

    public Boolean getHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(Boolean hasGarden) {
        this.hasGarden = hasGarden;
    }
}