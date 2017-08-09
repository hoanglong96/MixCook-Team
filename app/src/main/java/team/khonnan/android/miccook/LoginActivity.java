    package team.khonnan.android.miccook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

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

import org.json.JSONObject;

import java.util.Arrays;

import team.khonnan.android.miccook.model.UserInfo;

import static java.lang.Integer.parseInt;

    public class LoginActivity extends AppCompatActivity {

        private UserInfo userInfo;
        private CallbackManager callbackManager;
        private FacebookCallback<LoginResult> loginResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);
        initFaceBook();
        LoginManager.getInstance().registerCallback(callbackManager, loginResult);
        RelativeLayout relativeLayoutLogin = (RelativeLayout) findViewById(R.id.rl_login);
        relativeLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFaceBook();

                if(userInfo != null){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
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
                                    userInfo.setName(object.optString("name"));
                                    userInfo.setId(object.optInt("id"));
                                    userInfo.setEmail(object.optString("email"));
                                    userInfo.setAvatar("http://graph.facebook.com/" + object.optString("id")
                                            + "/picture?type=large");
                                }
                            });
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
