package team.khonnan.android.miccook.model;

import io.realm.RealmObject;

/**
 * Created by tungthanh.1497 on 09/02/2017.
 */

public class RealmFoodModel  extends RealmObject {
    private String _id;
    private String name;
    private String authorName;
    private String imageShow;
    private int sets;

    public RealmFoodModel() {
    }

    @Override
    public String toString() {
        return "RealmFoodModel{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", authorName='" + authorName + '\'' +
                ", imageShow='" + imageShow + '\'' +
                ", sets=" + sets +
                '}';
    }

    public RealmFoodModel(String _id, String name, String authorName, String imageShow, int sets) {
        this._id = _id;
        this.name = name;
        this.authorName = authorName;
        this.imageShow = imageShow;
        this.sets = sets;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getImageShow() {
        return imageShow;
    }

    public void setImageShow(String imageShow) {
        this.imageShow = imageShow;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}
