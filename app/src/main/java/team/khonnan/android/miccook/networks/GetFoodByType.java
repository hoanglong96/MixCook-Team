package team.khonnan.android.miccook.networks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tungthanh.1497 on 08/11/2017.
 */

public interface GetFoodByType {
    @GET("/getFoodByType/{foodType}")
    Call<GetFoodRespondModel> getFoodByType(@Path("foodType") String foodType);
}
