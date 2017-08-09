package team.khonnan.android.miccook.networks;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import team.khonnan.android.miccook.model.UserInfo;

/**
 * Created by tungthanh.1497 on 08/10/2017.
 */

public interface CreateUser {
    @POST("/createUser")
    Call<UserInfo> createUser(@Body UserInfo userInfo);

}
