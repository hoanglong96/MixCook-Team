package team.khonnan.android.miccook.networks.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import team.khonnan.android.miccook.networks.createFoodModels.CreateFoodRequestModel;

/**
 * Created by tungthanh.1497 on 08/17/2017.
 */

public interface CreateNewFood {
    @POST("/createFood")
    Call<CreateFoodRequestModel> createNewFood(@Body CreateFoodRequestModel createFoodRequestModel);
}
