package team.khonnan.android.miccook.networks.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tungthanh.1497 on 08/10/2017.
 */

public class RetrofitFactory {
    private Retrofit retrofit;

    private static RetrofitFactory instance = new RetrofitFactory("http://cookmix.herokuapp.com/");
    public static RetrofitFactory getInstance(){
        return instance;
    }

    public RetrofitFactory(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public <T> T create(final Class<T> service){
        return retrofit.create(service);
    }
}