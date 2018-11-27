package smt.ort.houses.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ConverterRoom {

    @TypeConverter
    public static String listRoomToString(List<Room> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<Room> stringToListRoom(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Room>>() {
        }.getType();

        List<Room> list = new Gson().fromJson(data, listType);

        for (Room item : list) {
            item.setType(RoomEnum.getEnum(item.getName()));
        }

        return list;
    }

}
