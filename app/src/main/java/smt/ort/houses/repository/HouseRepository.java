package smt.ort.houses.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

import smt.ort.houses.db.HouseDao;
import smt.ort.houses.db.HouseRoomDatabase;
import smt.ort.houses.model.House;
import smt.ort.houses.model.HouseFilters;
import smt.ort.houses.model.ResponseHouses;
import smt.ort.houses.network.ApiResponse;
import smt.ort.houses.network.ClientService;
import smt.ort.houses.network.HousesService;
import smt.ort.houses.network.Resource;
import smt.ort.houses.network.utils.NetworkBoundResource;

public class HouseRepository {

    private HouseDao dao;

    private HousesService service;

    public HouseRepository(Application app) {
        SharedPreferences sharedPreferences = app.getSharedPreferences("general", Context.MODE_PRIVATE);
        HouseRoomDatabase db = HouseRoomDatabase.getDatabase(app);
        dao = db.houseDao();
        service = ClientService.getClient(sharedPreferences.getString("authorization", "9876")).create(HousesService.class);
    }

    public LiveData<Resource<List<House>>> getHouses() {
        return new NetworkBoundResource<List<House>, ResponseHouses>() {

            @Override
            protected void saveCallResult(@NonNull ResponseHouses item) {
                dao.insertHouses(item.getList());
            }

            @Override
            protected boolean shouldFetch(@NonNull List<House> data) {
                return data == null || data.size() == 0;
            }

            @Override
            protected LiveData<List<House>> loadFromDb() {
                return dao.getAllHouses();
            }

            @Override
            protected LiveData<ApiResponse<ResponseHouses>> createCall() {
                return service.getHouses(new HouseFilters());
            }
        }.getAsLiveData();
    }

    public void saveHouse(House house) {

    }
}
