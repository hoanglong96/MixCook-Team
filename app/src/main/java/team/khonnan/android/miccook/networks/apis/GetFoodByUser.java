package team.khonnan.android.miccook.networks.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

/**
 * Created by tungthanh.1497 on 08/31/2017.
 */

public interface GetFoodByUser {
    @GET("/getFoodByUser/{idFb}")
    Call<GetFoodRespondModel> getFoodByUser(@Path("idFb") String idFb);
}
