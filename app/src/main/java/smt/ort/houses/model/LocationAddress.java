package smt.ort.houses.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationAddress implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LocationAddress> CREATOR = new Parcelable.Creator<LocationAddress>() {
        @Override
        public LocationAddress createFromParcel(Parcel in) {
            return new LocationAddress(in);
        }

        @Override
        public LocationAddress[] newArray(int size) {
            return new LocationAddress[size];
        }
    };
    private String location;
    private Double lat;
    private Double lng;

    public LocationAddress() {
    }

    public LocationAddress(String location, Double lat, Double lng) {
        this.location = location;
        this.lat = lat;
        this.lng = lng;
    }

    protected LocationAddress(Parcel in) {
        location = in.readString();
        lat = in.readByte() == 0x00 ? null : in.readDouble();
        lng = in.readByte() == 0x00 ? null : in.readDouble();
    }

    public String getLocation() {
        return location;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(location);
        if (lat == null) {
            parcel.writeByte((byte) (0x00));
        } else {
            parcel.writeByte((byte) (0x01));
            parcel.writeDouble(lat);
        }
        if (lng == null) {
            parcel.writeByte((byte) (0x00));
        } else {
            parcel.writeByte((byte) (0x01));
            parcel.writeDouble(lng);
        }
    }
}
