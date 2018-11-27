package smt.ort.houses.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.List;

import smt.ort.houses.model.House;
import smt.ort.houses.model.HouseFilters;
import smt.ort.houses.network.Resource;
import smt.ort.houses.network.utils.AppExecutors;
import smt.ort.houses.repository.HouseRepository;

public class HouseViewModel extends AndroidViewModel {

    private HouseRepository repository;

    private LiveData<Resource<List<House>>> houses;

    private MutableLiveData<HouseFilters> filters = new MutableLiveData<>();

    private MutableLiveData<String> query = new MutableLiveData<>();

    public HouseViewModel(Application app) {
        super(app);

        repository = HouseRepository.getInstance(new AppExecutors(), app);
        houses = Transformations.switchMap(filters, houses -> repository.getHouses(filters.getValue()));
    }

    public LiveData<Resource<List<House>>> getHouses() {
        return houses;
    }

    public MutableLiveData<HouseFilters> getFilters() {
        return filters;
    }

    public void setFilters(HouseFilters filters) {
        this.filters.setValue(filters);
    }

}
