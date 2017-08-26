package team.khonnan.android.miccook.networks.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import team.khonnan.android.miccook.networks.updateRatingModels.UpdateRatingModel;

/**
 * Created by tungthanh.1497 on 08/26/2017.
 */

public interface UpdateRating {
    @PUT("/updateFood/{foodId}")
    Call<UpdateRatingModel> updateRating(@Path("foodId") String foodId, @Body UpdateRatingModel updateRatingModel);
}
