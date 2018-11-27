package smt.ort.houses.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import smt.ort.houses.util.Sha1Encoded;

public class HouseFilters implements Parcelable {

    public static final Integer MAX_PRICE = 50000000;
    public static final Integer MIN_PRICE = 3000000;
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<HouseFilters> CREATOR = new Parcelable.Creator<HouseFilters>() {
        @Override
        public HouseFilters createFromParcel(Parcel in) {
            return new HouseFilters(in);
        }

        @Override
        public HouseFilters[] newArray(int size) {
            return new HouseFilters[size];
        }
    };
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

    public HouseFilters() {

    }

    public HouseFilters(Parcel in) {
        maxResults = in.readByte() == 0x00 ? null : in.readInt();
        title = in.readString();
        neighborhood = in.readString();
        price = in.readString();
        rooms = in.readByte() == 0x00 ? null : in.readInt();
        byte hasBarbecueVal = in.readByte();
        hasBarbecue = hasBarbecueVal == 0x02 ? null : hasBarbecueVal != 0x00;
        byte hasGarageVal = in.readByte();
        hasGarage = hasGarageVal == 0x02 ? null : hasGarageVal != 0x00;
        byte hasBalconyVal = in.readByte();
        hasBalcony = hasBalconyVal == 0x02 ? null : hasBalconyVal != 0x00;
        byte hasGardenVal = in.readByte();
        hasGarden = hasGardenVal == 0x02 ? null : hasGardenVal != 0x00;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (maxResults == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(maxResults);
        }
        dest.writeString(title);
        dest.writeString(neighborhood);
        dest.writeString(price);
        if (rooms == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(rooms);
        }
        if (hasBarbecue == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasBarbecue ? 0x01 : 0x00));
        }
        if (hasGarage == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (hasGarage ? 0x01 : 0x00));
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
    }

    public String getEncodedKey() {
        String jsonStr = new Gson().toJson(this);
        try {
            return Sha1Encoded.SHA1(jsonStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}