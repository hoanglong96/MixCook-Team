package team.khonnan.android.miccook.firebases;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by tungthanh.1497 on 08/29/2017.
 */

public interface SendNoti {
    @Headers({
            "Content-Type: application/json",
            "Authorization: key=AAAAMYwVZOo:APA91bHukKONwrbxCHcyq7CL2A7TvsWo4U32EhlMRrTwguOlreH4DfhFGJyY8UgpSb_muon6QBnGjSjM2jyZgqXsX-Xhanrh2-RYYyqLxC4vFiV5nIognqv1kP2bzyLDoB4_BgYekqyj"
    })
    @POST("https://fcm.googleapis.com/fcm/send")
    Call<NotiRespondModel> sendNoti(@Body NotiRequestModel notiRequestModel);

}
