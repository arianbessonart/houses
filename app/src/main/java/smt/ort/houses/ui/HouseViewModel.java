package smt.ort.houses.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import smt.ort.houses.model.House;
import smt.ort.houses.network.Resource;
import smt.ort.houses.repository.HouseRepository;

public class HouseViewModel extends AndroidViewModel {

    private HouseRepository repository;

    private LiveData<Resource<List<House>>> houses;

    public HouseViewModel(Application app) {
        super(app);
        repository = new HouseRepository(app);
        houses = repository.getHouses();
    }

    public LiveData<Resource<List<House>>> getHouses() {
        return houses;
    }
}
