package smt.ort.houses.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import smt.ort.houses.model.House;

public interface HousesService {

    @GET("5bbe51b934000078006fcade/")
    Call<List<House>> getHouses();

}
