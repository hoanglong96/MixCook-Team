package team.khonnan.android.miccook.networks.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by tungthanh.1497 on 09/01/2017.
 */

public interface GetFoodByIdFood {
    @GET("/getFoodById/{idFood}")
    Call<FoodModel> getFoodByIdFood(@Path("idFood") String idFood);
}
