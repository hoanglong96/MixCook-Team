package team.khonnan.android.miccook.firebases;

/**
 * Created by tungthanh.1497 on 08/29/2017.
 */

public class NotiRespondModel {
    String message_id;

    @Override
    public String toString() {
        return "NotiRespondModel{" +
                "message_id='" + message_id + '\'' +
                '}';
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public NotiRespondModel() {

    }

    public NotiRespondModel(String message_id) {

        this.message_id = message_id;
    }
}
