package smt.ort.houses.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "houses")
public class House {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private Long id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("price")
    @ColumnInfo(name = "price")
    private Float price;

    @SerializedName("rooms")
    @ColumnInfo(name = "rooms")
    private Integer rooms;

    @SerializedName("bathrooms")
    @ColumnInfo(name = "bathrooms")
    private Integer bathrooms;

    @SerializedName("squareMeters")
    @ColumnInfo(name = "squareMeters")
    private Integer squareMeters;

    @SerializedName("hasGarage")
    @ColumnInfo(name = "hasGarage")
    private Boolean hasGarage;

    @SerializedName("hasBarbeque")
    @ColumnInfo(name = "hasBarbeque")
    private Boolean hasBarbeque;

    @SerializedName("hasBalcony")
    @ColumnInfo(name = "hasBalcony")
    private Boolean hasBalcony;

    @SerializedName("hasGarden")
    @ColumnInfo(name = "hasGarden")
    private Boolean hasGarden;

    @SerializedName("photos")
    @ColumnInfo(name = "photos")
    @TypeConverters({Converters.class})
    private List<String> photos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(Integer squareMeters) {
        this.squareMeters = squareMeters;
    }

    public Boolean getHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(Boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public Boolean getHasBarbeque() {
        return hasBarbeque;
    }

    public void setHasBarbeque(Boolean hasBarbeque) {
        this.hasBarbeque = hasBarbeque;
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
