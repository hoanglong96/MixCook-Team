package team.khonnan.android.miccook.event;

import team.khonnan.android.miccook.model.UserInfo;

/**
 * Created by apple on 12/08/2017.
 */

public class EventUser {
    private UserInfo userInfo;

    public EventUser(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
