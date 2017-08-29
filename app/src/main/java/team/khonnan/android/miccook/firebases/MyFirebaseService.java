package team.khonnan.android.miccook.firebases;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by tungthanh.1497 on 08/23/2017.
 */

public class MyFirebaseService extends FirebaseMessagingService {
//    private static final String TAG = "MyFirebaseService";
    private static final String TAG = "MyFirebaseService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "onMessageReceived: ");
    }
}
