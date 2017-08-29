package team.khonnan.android.miccook.firebases;

/**
 * Created by tungthanh.1497 on 08/29/2017.
 */

public class DataModel {
    String message;

    @Override
    public String toString() {
        return "DataModel{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataModel(String message) {

        this.message = message;
    }
}
