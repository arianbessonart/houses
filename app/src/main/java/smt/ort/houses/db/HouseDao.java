package smt.ort.houses.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import smt.ort.houses.model.House;

@Dao
public interface HouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(House house);

    @Query("DELETE FROM houses")
    void deleteAllHouses();

    @Delete
    void deleteFavorite(House house);

    @Query("SELECT * FROM houses ORDER BY title")
    LiveData<List<House>> getAllHouses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHouses(List<House> item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorites(List<House> item);

    @Query("SELECT * FROM houses WHERE id = :id ORDER BY title")
    LiveData<House> getHouse(String id);

    @Update
    void update(House house);

    @Query("SELECT * FROM houses h WHERE h.roomsQuantity = coalesce(:rooms, h.roomsQuantity) AND h.title LIKE coalesce(:title, h.title) ORDER BY h.title LIMIT :maxResults")
    LiveData<List<House>> getHousesByFilters(String title, Integer rooms, Integer maxResults);

    @Query("SELECT * FROM houses h WHERE h.favorite = 1 ORDER BY title")
    LiveData<List<House>> getFavorites();
}
