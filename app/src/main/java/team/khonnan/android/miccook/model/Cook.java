package team.khonnan.android.miccook.model;

import io.realm.RealmObject;

/**
 * Created by quyntg94 on 19/05/2017.
 */

public class Cook extends RealmObject {
    private String image;
    private String note;

    public Cook() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Cook{" +
                "image='" + image + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
