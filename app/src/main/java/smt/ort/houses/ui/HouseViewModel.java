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

    private LiveData<Resource<List<House>>> searchResults;

    private MutableLiveData<HouseFilters> filters = new MutableLiveData<>();

    private MutableLiveData<String> query = new MutableLiveData<>();

    public HouseViewModel(Application app) {
        super(app);

        repository = new HouseRepository(new AppExecutors(), app);

//        searchResults = Transformations.switchMap(filters, new Function<HouseFilters, LiveData<Resource<List<House>>>>() {
//            @Override
//            public LiveData<Resource<List<House>>> apply(HouseFilters filters) {
//                return repository.getHouses(filters);
//            }
//        });

//        searchResults = Transformations.switchMap(query, search -> {
//            return repository.getHouses(new HouseFilters());
//        });
//        houses = repository.getHouses(new HouseFilters());

        houses = Transformations.switchMap(filters, houses -> repository.getHouses(filters.getValue()));
//        searchResults = Transformations.switchMap(query, houses -> repository.searchHouses(query.getValue()));

    }

    public LiveData<Resource<List<House>>> getHouses() {
        return houses;
    }

    public LiveData<Resource<List<House>>> getSearchResults() {
        return searchResults;
    }

    public void setFilters(HouseFilters filters) {
        this.filters.setValue(filters);
    }

    public MutableLiveData<HouseFilters> getFilters() {
        return filters;
    }

    public void setQuery(String query) {
        this.query.setValue(query);
    }
}
