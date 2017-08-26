package team.khonnan.android.miccook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.event.EventUser;
import team.khonnan.android.miccook.model.UserInfo;
import team.khonnan.android.miccook.networks.apis.CreateUser;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;

public class LoginActivity extends AppCompatActivity {

    private UserInfo userInfo;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> loginResult;
    private SharedPreferences sharedPreferences;
    private Boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        LoginButton loginButton = (LoginButton) findViewById(R.id.rl_login);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                // Application code
                                Log.d("hihi", "name : "  + object.optString("name"));
                                Log.d("hihi", "id : " + object.optString("id"));
                                Log.d("hihi", "email : "  + object.optString("email"));
                                userInfo  = new UserInfo();
                                userInfo.setNameFb(object.optString("name"));
                                userInfo.setIdFb(object.optString("id"));
                                userInfo.setEmailFb(object.optString("email"));
                                userInfo.setAvaFb("https://graph.facebook.com/" + object.optString("id")
                                        + "/picture?type=large");
                                userInfo.setRatePoint(0);
                                userInfo.setRateNum(0);

                                sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                editor1.putString("id",object.optString("id"));
                                editor1.putString("name",object.optString("name"));
                                editor1.putString("email",object.optString("email"));
                                editor1.putString("avatar","http://graph.facebook.com/" + object.optString("id")
                                        + "/picture?type=large");
                                editor1.commit();

                                Log.d("Share", "onCompleted: " + object.optString("id"));
                                final CreateUser createUser = RetrofitFactory.getInstance().create(CreateUser.class);
                                Call<UserInfo> call = createUser.createUser(userInfo);
                                call.enqueue(new Callback<UserInfo>() {
                                    @Override
                                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<UserInfo> call, Throwable t) {

                                    }
                                });
                            }
                        });
                isLogin = true;

                sharedPreferences = getSharedPreferences("checkLogin",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin",isLogin);
                editor.commit();

               if(userInfo != null){
                    EventBus.getDefault().postSticky(new EventUser(userInfo));
                }


                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email,first_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
        //initFaceBook();
        //LoginManager.getInstance().registerCallback(callbackManager, loginResult);
//        RelativeLayout relativeLayoutLogin = (RelativeLayout) findViewById(R.id.rl_login);
//        relativeLayoutLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginFaceBook();
//
//                if(userInfo != null){
//                    EventBus.getDefault().postSticky(new EventUser(userInfo));
//
//                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    finish();
//                }
//
//            }
//        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void loginFaceBook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends","email", "user_birthday"));
    }

    public void initFaceBook () {
        loginResult = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Login thành công xử lý tại đây
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                // Application code
                                Log.d("hihi", "name : "  + object.optString("name"));
                                Log.d("hihi", "id : " + object.optString("id"));
                                Log.d("hihi", "email : "  + object.optString("email"));
                                userInfo  = new UserInfo();
                                userInfo.setNameFb(object.optString("name"));
                                userInfo.setIdFb(object.optString("id"));
                                userInfo.setEmailFb(object.optString("email"));
                                userInfo.setAvaFb("http://graph.facebook.com/" + object.optString("id")
                                        + "/picture?type=large");
                                userInfo.setRatePoint(0);
                                userInfo.setRateNum(0);

                                sharedPreferences = getSharedPreferences("userInfo",MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sharedPreferences.edit();
                                editor1.putString("id",object.optString("id"));
                                editor1.putString("name",object.optString("name"));
                                editor1.putString("email",object.optString("email"));
                                editor1.putString("avatar","http://graph.facebook.com/" + object.optString("id")
                                        + "/picture?type=large");
                                editor1.commit();

                                Log.d("Share", "onCompleted: " + object.optString("id"));
                                final CreateUser createUser = RetrofitFactory.getInstance().create(CreateUser.class);
                                Call<UserInfo> call = createUser.createUser(userInfo);
                                call.enqueue(new Callback<UserInfo>() {
                                    @Override
                                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<UserInfo> call, Throwable t) {

                                    }
                                });
                            }
                        });
                isLogin = true;

                sharedPreferences = getSharedPreferences("checkLogin",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin",isLogin);
                editor.commit();



                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email,first_name");
                request.setParameters(parameters);
                request.executeAsync();



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        };
    }
}
