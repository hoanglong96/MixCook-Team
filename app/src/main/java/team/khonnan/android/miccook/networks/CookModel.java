package team.khonnan.android.miccook.networks;

/**
 * Created by tungthanh.1497 on 08/11/2017.
 */

public class CookModel {
    private String image;
    private String note;
    private String _id;

    @Override
    public String toString() {
        return "CookModel{" +
                "image='" + image + '\'' +
                ", note='" + note + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }

    public CookModel(String image, String note, String _id) {
        this.image = image;
        this.note = note;
        this._id = _id;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
