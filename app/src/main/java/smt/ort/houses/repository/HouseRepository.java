package smt.ort.houses.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import smt.ort.houses.db.HouseDao;
import smt.ort.houses.db.HouseRoomDatabase;
import smt.ort.houses.model.House;
import smt.ort.houses.network.ApiResponse;
import smt.ort.houses.network.ClientService;
import smt.ort.houses.network.HousesService;
import smt.ort.houses.network.NetworkBoundResource;

public class HouseRepository {

    private HouseDao dao;

    private HousesService service;

    private LiveData<List<House>> houses;

    public HouseRepository(Application app) {
        HouseRoomDatabase db = HouseRoomDatabase.getDatabase(app);
        dao = db.houseDao();
        service = ClientService.getClient().create(HousesService.class);
    }

    public LiveData<List<House>> getHouses() {
        return new NetworkBoundResource<List<House>, List<House>>() {

            @Override
            protected void saveCallResult(@NonNull List<House> item) {
                dao.insertHouses(item);
            }

            @Override
            protected boolean shouldFetch(@NonNull List<House> data) {
                return true;
            }

            @Override
            protected LiveData<List<House>> loadFromDb() {
                return dao.getAllHouses();
            }

            @Override
            protected LiveData<ApiResponse<List<House>>> createCall() {
                return service.getHouses();
            }
        }.getAsLiveData();
    }
}
