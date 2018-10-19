package smt.ort.houses.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import smt.ort.houses.db.HouseDao;
import smt.ort.houses.db.HouseRoomDatabase;
import smt.ort.houses.model.House;

public class HouseRepository {

    private HouseDao dao;

    private LiveData<List<House>> houses;

    public HouseRepository(Application app) {
        HouseRoomDatabase db = HouseRoomDatabase.getDatabase(app);
        dao = db.houseDao();
        houses = dao.getAllHouses();
    }

    public LiveData<List<House>> getHouses() {
        return houses;
    }
}
