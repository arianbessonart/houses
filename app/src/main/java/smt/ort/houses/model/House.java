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
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("InmuebleId")
    private String id;
    @ColumnInfo(name = "title")
    @SerializedName("InmuebleTitulo")
    private String title;
    @ColumnInfo(name = "price")
    @SerializedName("InmueblePrecio")
    private String price;
    @ColumnInfo(name = "neighborhood")
    @SerializedName("InmuebleBarrio")
    private String neighborhood;
    @ColumnInfo(name = "rooms")
    @SerializedName("InmuebleCantDormitorio")
    private Integer rooms;
    @ColumnInfo(name = "squareMeters")
    @SerializedName("InmuebleMetrosCuadrados")
    private String squareMeters;
    @ColumnInfo(name = "hasGarage")
    @SerializedName("InmuebleTieneGarage")
    private Boolean hasGarage;
    @ColumnInfo(name = "hasBarbecue")
    @SerializedName("InmuebleTieneParrillero")
    private Boolean hasBarbecue;
    @ColumnInfo(name = "hasBalcony")
    @SerializedName("InmuebleTieneBalcon")
    private Boolean hasBalcony;
    @ColumnInfo(name = "hasGarden")
    @SerializedName("InmuebleTienePatio")
    private Boolean hasGarden;
    @ColumnInfo(name = "photos")
    @SerializedName("Fotos")
    @TypeConverters({Converters.class})
    private List<Photo> photos;
    @ColumnInfo(name = "favorite")
    @SerializedName("Favorito")
    private Boolean favorite;

    public House() {
    }

    protected House(Parcel in) {
        id = in.readString();
        title = in.readString();
        price = in.readString();
        neighborhood = in.readString();
        rooms = in.readByte() == 0x00 ? null : in.readInt();
        squareMeters = in.readString();
        byte hasGarageVal = in.readByte();
        hasGarage = hasGarageVal == 0x02 ? null : hasGarageVal != 0x00;
        byte hasBarbequeVal = in.readByte();
        hasBarbecue = hasBarbequeVal == 0x02 ? null : hasBarbequeVal != 0x00;
        byte hasBalconyVal = in.readByte();
        hasBalcony = hasBalconyVal == 0x02 ? null : hasBalconyVal != 0x00;
        byte hasGardenVal = in.readByte();
        hasGarden = hasGardenVal == 0x02 ? null : hasGardenVal != 0x00;
        if (in.readByte() == 0x01) {
            photos = new ArrayList<Photo>();
            in.readList(photos, Photo.class.getClassLoader());
        } else {
            photos = null;
        }
        byte favoriteVal = in.readByte();
        favorite = favoriteVal == 0x02 ? null : favoriteVal != 0x00;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public String getSquareMeters() {
        return squareMeters;
    }

    public void setSquareMeters(String squareMeters) {
        this.squareMeters = squareMeters;
    }

    public Boolean getHasGarage() {
        return hasGarage;
    }

    public void setHasGarage(Boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public Boolean getHasBarbecue() {
        return hasBarbecue;
    }

    public void setHasBarbecue(Boolean hasBarbecue) {
        this.hasBarbecue = hasBarbecue;
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(price);
        dest.writeString(neighborhood);
        if (rooms == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(rooms);
        }
        dest.writeString(squareMeters);
        if (hasGarage == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasGarage ? 0x01 : 0x00));
        }
        if (hasBarbecue == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasBarbecue ? 0x01 : 0x00));
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
        if (favorite == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (favorite ? 0x01 : 0x00));
        }
    }
}
