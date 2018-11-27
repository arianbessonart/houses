package smt.ort.houses.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<String> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {
        }.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static List<Photo> stringToListPhoto(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Photo>>() {
        }.getType();
        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String listPhotoToString(List<Photo> list) {
        return new Gson().toJson(list);
    }


    @TypeConverter
    public static List<Room> stringToListRoom(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Photo>>() {
        }.getType();
        return new Gson().fromJson(data, listType);
    }

}
