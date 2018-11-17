package smt.ort.houses.model;

import com.google.gson.annotations.SerializedName;

public class HouseFilters {

    public static final Integer MAX_PRICE = 50000000;
    public static final Integer MIN_PRICE = 3000000;

    @SerializedName("MaxResults")
    private Integer maxResults = 10;

    @SerializedName("Titulo")
    private String title;

    @SerializedName("Barrio")
    private String neighborhood;

    @SerializedName("Precio")
    private String price = String.valueOf(MAX_PRICE);

    @SerializedName("CantDormitorio")
    private Integer rooms;

    @SerializedName("TieneParrillero")
    private Boolean hasBarbecue;

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

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Boolean getHasBarbecue() {
        return hasBarbecue;
    }

    public void setHasBarbecue(Boolean hasBarbecue) {
        this.hasBarbecue = hasBarbecue;
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