package smt.ort.houses.ui.housedetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import smt.ort.houses.model.House;
import smt.ort.houses.network.Resource;
import smt.ort.houses.repository.HouseRepository;

public class HouseDetailViewModel extends AndroidViewModel {

    private HouseRepository repository;

    private LiveData<Resource<House>> house;

    public HouseDetailViewModel(@NonNull Application application, final String houseID) {
        super(application);
        repository = new HouseRepository(application);
        house = repository.getHouse(houseID);
    }

    public LiveData<Resource<House>> getHouse() {
        return house;
    }

    public void toggleFavorite(House house) {
        repository.toggleFavorite(house);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Application application;

        private final String houseID;

        public Factory(@NonNull Application application, String houseID) {
            this.application = application;
            this.houseID = houseID;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new HouseDetailViewModel(application, this.houseID);
        }
    }

}
