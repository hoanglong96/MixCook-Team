package team.khonnan.android.miccook.networks.apis;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import team.khonnan.android.miccook.networks.UpdateFavoriteModel;
import team.khonnan.android.miccook.networks.getUserProfileModels.UserProfileModel;

/**
 * Created by tungthanh.1497 on 09/01/2017.
 */

public interface UpdateFavorite {
    @PUT("/updateUser/{idFb}")
    Call<UserProfileModel> updateFavorite(@Path("idFb") String idFb, @Body UpdateFavoriteModel updateFavoriteModel);
}
