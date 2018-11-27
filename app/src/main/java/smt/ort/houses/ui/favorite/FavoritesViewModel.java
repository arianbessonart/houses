package smt.ort.houses.ui.favorite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import smt.ort.houses.model.House;
import smt.ort.houses.network.Resource;
import smt.ort.houses.network.utils.AppExecutors;
import smt.ort.houses.repository.HouseRepository;

public class FavoritesViewModel extends AndroidViewModel {

    private HouseRepository repository;

    private LiveData<Resource<List<House>>> favorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = HouseRepository.getInstance(new AppExecutors(), application);
        favorites = repository.getFavorites();
    }

    public LiveData<Resource<List<House>>> getFavorites() {
        return favorites;
    }

}
