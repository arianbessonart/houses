package smt.ort.houses.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import smt.ort.houses.db.HouseDao;
import smt.ort.houses.db.HouseRoomDatabase;
import smt.ort.houses.model.FavoriteBodyRequest;
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
                // TODO: change this
                return true;
//                return data.size() == 0;
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

    public LiveData<Resource<House>> getHouse(final String id) {

        return new NetworkBoundResource<House, ResponseHouses>() {

            @Override
            protected void saveCallResult(@NonNull ResponseHouses item) {
            }

            @Override
            protected boolean shouldFetch(@NonNull House data) {
                return false;
            }

            @Override
            protected LiveData<House> loadFromDb() {
                return dao.getHouse(id);
            }

            @Override
            protected LiveData<ApiResponse<ResponseHouses>> createCall() {
                return new MutableLiveData<>();
            }
        }.getAsLiveData();
    }

    @SuppressLint("StaticFieldLeak")
    public void toggleFavorite(final House house) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.update(house);
                try {
                    service.addFavorite(new FavoriteBodyRequest(house.getId()));
                } catch (Exception e) {
                    Log.e("FAVORITE", e.getMessage());
                }
                return null;
            }
        }.execute();
    }

    public LiveData<Resource<List<House>>> getFavorites() {
        return new NetworkBoundResource<List<House>, ResponseHouses>() {

            @Override
            protected void saveCallResult(@NonNull ResponseHouses item) {
//                dao.insertFavorites(item.getList());
            }

            @Override
            protected boolean shouldFetch(@NonNull List<House> data) {
                // TODO: change this
                return true;
//                return data.size() == 0;
            }

            @Override
            protected LiveData<List<House>> loadFromDb() {
                return dao.getAllHouses();
            }

            @Override
            protected LiveData<ApiResponse<ResponseHouses>> createCall() {
                return service.getFavorites();
            }
        }.getAsLiveData();
    }

}
