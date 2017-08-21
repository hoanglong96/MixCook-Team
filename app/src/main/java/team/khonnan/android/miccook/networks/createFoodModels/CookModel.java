package team.khonnan.android.miccook.networks.createFoodModels;

/**
 * Created by tungthanh.1497 on 08/17/2017.
 */

public class CookModel {
    private String image;
    private String note;

    public CookModel(String image, String note) {
        this.image = image;
        this.note = note;
    }

    @Override
    public String toString() {
        return "CookModel{" +
                "image='" + image + '\'' +
                ", note='" + note + '\'' +
                '}';
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
}
