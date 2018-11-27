package smt.ort.houses.ui.favorite;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
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
//        repository = new HouseRepository(new AppExecutors(), application);
        repository = HouseRepository.getInstance(new AppExecutors(), application);
        favorites = repository.getFavorites();
    }

    public LiveData<Resource<List<House>>> getFavorites() {
        return favorites;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Application application;

        public Factory(@NonNull Application application) {
            this.application = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavoritesViewModel(application);
        }
    }

}
