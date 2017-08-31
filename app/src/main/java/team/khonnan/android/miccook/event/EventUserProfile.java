package team.khonnan.android.miccook.event;

import team.khonnan.android.miccook.model.UserInfo;

/**
 * Created by apple on 8/31/17.
 */

public class EventUserProfile {
    private UserInfo userInfo;

    public EventUserProfile(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
