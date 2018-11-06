package smt.ort.houses.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHouses(List<House> item);

    @Query("SELECT * FROM houses WHERE id = :id ORDER BY title")
    LiveData<House> getHouse(String id);

    @Update
    void update(House house);
}
