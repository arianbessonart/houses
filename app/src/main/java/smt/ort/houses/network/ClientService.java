package smt.ort.houses.network;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import smt.ort.houses.BuildConfig;
import smt.ort.houses.network.utils.LiveDataCallAdapterFactory;

public class ClientService {

    private static String URL = BuildConfig.API_URL;

    public static Retrofit getClient(final String authorization) {

        Interceptor interceptor = chain -> {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder();
            if (authorization != null) {
                builder.addHeader("Authorization", authorization);
            }

            Request newRequest = builder.build();
            Response mainResponse = chain.proceed(newRequest);
            return mainResponse;
        };

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();

        return retrofit;
    }

    public static Retrofit getClientCall(final String authorization) {

        Interceptor interceptor = chain -> {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder();
            if (authorization != null) {
                builder.addHeader("Authorization", authorization);
            }

            Request newRequest = builder.build();
            Response mainResponse = chain.proceed(newRequest);
            return mainResponse;
        };

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

}
