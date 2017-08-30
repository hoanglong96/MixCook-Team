package team.khonnan.android.miccook.networks.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import team.khonnan.android.miccook.networks.SearchModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;

/**
 * Created by tungthanh.1497 on 08/31/2017.
 */

public interface Searching {
    @POST("/searching")
    Call<GetFoodRespondModel> searching(@Body SearchModel searchModel);
}
