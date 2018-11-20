package smt.ort.houses.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import smt.ort.houses.db.HouseDao;
import smt.ort.houses.db.HouseRoomDatabase;
import smt.ort.houses.model.Favorite;
import smt.ort.houses.model.FavoriteBodyRequest;
import smt.ort.houses.model.FavoriteBodyResponse;
import smt.ort.houses.model.House;
import smt.ort.houses.model.HouseFilters;
import smt.ort.houses.model.ResponseFavorites;
import smt.ort.houses.model.ResponseHouses;
import smt.ort.houses.network.ApiResponse;
import smt.ort.houses.network.ClientService;
import smt.ort.houses.network.HousesService;
import smt.ort.houses.network.Resource;
import smt.ort.houses.network.utils.AppExecutors;
import smt.ort.houses.network.utils.NetworkBoundResource;

public class HouseRepository {

    private final AppExecutors mAppExecutors;
    private HouseDao dao;
    private HousesService service;

    public HouseRepository(AppExecutors appExecutors, Application app) {
        mAppExecutors = appExecutors;
        SharedPreferences sharedPreferences = app.getSharedPreferences("general", Context.MODE_PRIVATE);
        HouseRoomDatabase db = HouseRoomDatabase.getDatabase(app);
        dao = db.houseDao();
        service = ClientService.getClient(sharedPreferences.getString("authorization", "9876")).create(HousesService.class);
    }

    public LiveData<Resource<List<House>>> getHouses(final HouseFilters filters) {
        return new NetworkBoundResource<List<House>, ResponseHouses>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull ResponseHouses item) {
                if (item.getList() != null && item.getList().size() > 0) {
                    dao.insertHouses(item.getList());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<House> data) {
                return data == null || data.isEmpty();
            }

            @Override
            protected LiveData<List<House>> loadFromDb() {
                String title = filters.getTitle();
                String titleFilter = title != null && !title.equals("") ? "%" + filters.getTitle() + "%" : null;
                return dao.getHousesByFilters(titleFilter, filters.getRooms(), filters.getMaxResults());
            }

            @Override
            protected LiveData<ApiResponse<ResponseHouses>> createCall() {
                return service.getHouses(filters);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<House>> getHouse(final String id) {

        return new NetworkBoundResource<House, ResponseHouses>(mAppExecutors) {

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

    public LiveData<Resource<List<Favorite>>> getFavorites() {
        return new NetworkBoundResource<List<Favorite>, ResponseFavorites>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull ResponseFavorites item) {
                if (item.getList() != null && item.getList().size() > 0) {
                    dao.insertFavorites(item.getList());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Favorite> data) {
                return data == null || data.isEmpty();
            }

            @Override
            protected LiveData<List<Favorite>> loadFromDb() {
                return dao.getFavorites();
            }

            @Override
            protected LiveData<ApiResponse<ResponseFavorites>> createCall() {
                return service.getFavorites();
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
                    HousesService serviceDebug = ClientService.getClientCall("9876").create(HousesService.class);
                    serviceDebug.addFavorite(new FavoriteBodyRequest(house.getId())).enqueue(new Callback<ApiResponse<FavoriteBodyResponse>>() {
                        @Override
                        public void onResponse(Call<ApiResponse<FavoriteBodyResponse>> call, Response<ApiResponse<FavoriteBodyResponse>> response) {
                            Log.d("onResponse", response.message());
                        }

                        @Override
                        public void onFailure(Call<ApiResponse<FavoriteBodyResponse>> call, Throwable t) {
                            Log.d("onFailure", t.getMessage());
                        }
                    });
                } catch (Exception e) {
                    Log.e("FAVORITE", e.getMessage());
                }
                return null;
            }
        }.execute();
    }

}
