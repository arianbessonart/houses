package smt.ort.houses.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import smt.ort.houses.model.House;

@Dao
public interface HouseDao {

    @Insert
    void insert(House house);

    @Query("DELETE FROM houses")
    void deleteAll();

    @Query("SELECT * FROM houses ORDER BY title")
    LiveData<List<House>> getAllHouses();
}
