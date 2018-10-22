package smt.ort.houses.network;

import android.arch.lifecycle.LiveData;

import java.util.List;

import retrofit2.http.GET;
import smt.ort.houses.model.House;

public interface HousesService {

    @GET("5bbe51b934000078006fcade/")
    LiveData<ApiResponse<List<House>>> getHouses();

}
