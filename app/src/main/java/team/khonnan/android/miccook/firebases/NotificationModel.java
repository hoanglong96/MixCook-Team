package team.khonnan.android.miccook.firebases;

/**
 * Created by tungthanh.1497 on 08/29/2017.
 */

public class NotificationModel {
    String body = "Tap to see detail...";
    String title;


    @Override
    public String toString() {
        return "NotificationModel{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NotificationModel(String user) {
        this.title = user + " also commented on a post you are interested";
    }
}
