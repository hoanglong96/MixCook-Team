package team.khonnan.android.miccook.networks.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

/**
 * Created by apple on 8/31/17.
 */

public interface GetTopFood {
    @GET("/getTopFood")
    Call<GetFoodRespondModel> getTopFood();
}
