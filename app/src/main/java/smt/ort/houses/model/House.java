package smt.ort.houses.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "houses")
public class House implements Parcelable {

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

    public House() {}

    protected House(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readLong();
        title = in.readString();
        price = in.readByte() == 0x00 ? null : in.readFloat();
        rooms = in.readByte() == 0x00 ? null : in.readInt();
        bathrooms = in.readByte() == 0x00 ? null : in.readInt();
        squareMeters = in.readByte() == 0x00 ? null : in.readInt();
        byte hasGarageVal = in.readByte();
        hasGarage = hasGarageVal == 0x02 ? null : hasGarageVal != 0x00;
        byte hasBarbequeVal = in.readByte();
        hasBarbeque = hasBarbequeVal == 0x02 ? null : hasBarbequeVal != 0x00;
        byte hasBalconyVal = in.readByte();
        hasBalcony = hasBalconyVal == 0x02 ? null : hasBalconyVal != 0x00;
        byte hasGardenVal = in.readByte();
        hasGarden = hasGardenVal == 0x02 ? null : hasGardenVal != 0x00;
        if (in.readByte() == 0x01) {
            photos = new ArrayList<>();
            in.readList(photos, String.class.getClassLoader());
        } else {
            photos = null;
        }
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(id);
        }
        dest.writeString(title);
        if (price == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(price);
        }
        if (rooms == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(rooms);
        }
        if (bathrooms == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(bathrooms);
        }
        if (squareMeters == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(squareMeters);
        }
        if (hasGarage == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasGarage ? 0x01 : 0x00));
        }
        if (hasBarbeque == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasBarbeque ? 0x01 : 0x00));
        }
        if (hasBalcony == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasBalcony ? 0x01 : 0x00));
        }
        if (hasGarden == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasGarden ? 0x01 : 0x00));
        }
        if (photos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(photos);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<House> CREATOR = new Parcelable.Creator<House>() {
        @Override
        public House createFromParcel(Parcel in) {
            return new House(in);
        }

        @Override
        public House[] newArray(int size) {
            return new House[size];
        }
    };
}
