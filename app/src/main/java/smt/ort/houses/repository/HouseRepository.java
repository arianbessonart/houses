package smt.ort.houses.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smt.ort.houses.db.HouseDao;
import smt.ort.houses.db.HouseRoomDatabase;
import smt.ort.houses.model.House;
import smt.ort.houses.network.ClientService;
import smt.ort.houses.network.HousesService;

public class HouseRepository {

    private HouseDao dao;

    private HousesService service;

    private LiveData<List<House>> houses;

    public HouseRepository(Application app) {
        HouseRoomDatabase db = HouseRoomDatabase.getDatabase(app);
        dao = db.houseDao();
        houses = dao.getAllHouses();
        service = ClientService.getClient().create(HousesService.class);
    }

    public LiveData<List<House>> getHouses() {
        final MutableLiveData<List<House>> data = new MutableLiveData<>();
        service.getHouses().enqueue(new Callback<List<House>>() {
            @Override
            public void onResponse(Call<List<House>> call, Response<List<House>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<House>> call, Throwable t) {
                Log.w("WEBSERVICES", t);
            }
        });
        return data;
    }
}
