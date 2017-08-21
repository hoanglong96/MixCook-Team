package team.khonnan.android.miccook.model;

/**
 * Created by apple on 8/21/17.
 */

public class Type {
    String type;
    int urlImage;

    public Type(String type, int urlImage) {
        this.type = type;
        this.urlImage = urlImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(int urlImage) {
        this.urlImage = urlImage;
    }
}
