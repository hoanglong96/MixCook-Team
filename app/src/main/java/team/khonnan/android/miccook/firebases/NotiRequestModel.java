package team.khonnan.android.miccook.firebases;

/**
 * Created by tungthanh.1497 on 08/29/2017.
 */

public class NotiRequestModel {
    String to;
    NotificationModel notification;
    DataModel data;

    @Override
    public String toString() {
        return "NotiRequestModel{" +
                "to='" + to + '\'' +
                ", notification=" + notification +
                ", data=" + data +
                '}';
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotificationModel getNotification() {
        return notification;
    }

    public void setNotification(NotificationModel notification) {
        this.notification = notification;
    }

    public DataModel getData() {
        return data;
    }

    public void setData(DataModel data) {
        this.data = data;
    }

    public NotiRequestModel(String topicId, NotificationModel notification, DataModel data) {

        this.to = "/topics/"+topicId;
        this.notification = notification;
        this.data = data;
    }
}
