package smt.ort.houses.network;

import android.arch.lifecycle.LiveData;

import retrofit2.http.Body;
import retrofit2.http.POST;
import smt.ort.houses.model.FavoriteBodyRequest;
import smt.ort.houses.model.HouseFilters;
import smt.ort.houses.model.ResponseHouses;

public interface HousesService {

    @POST("buscarInmueble/")
    LiveData<ApiResponse<ResponseHouses>> getHouses(@Body HouseFilters filters);

    @POST("guardarFavorito/")
    LiveData<ApiResponse<Void>> addFavorite(@Body FavoriteBodyRequest body);

    @POST("listadoFavoritos/")
    LiveData<ApiResponse<ResponseHouses>> getFavorites();

}
