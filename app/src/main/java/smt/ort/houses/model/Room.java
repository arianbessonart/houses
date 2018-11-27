package smt.ort.houses.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Room implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Room> CREATOR = new Parcelable.Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };
    @SerializedName("InmuebleHabitacionCantidad")
    private Integer quantity;
    @SerializedName("InmuebleHabitacionNombre")
    private String name;
    @SerializedName("type")
    private RoomEnum type;

    protected Room(Parcel in) {
        quantity = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        type = RoomEnum.valueOf(in.readString());
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public RoomEnum getType() {
        return type;
    }

    public void setType(RoomEnum type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (quantity == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(quantity);
        }
        dest.writeString(name);
        dest.writeString(type.name());
    }

}
