package team.khonnan.android.miccook.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by quyntg94 on 19/05/2017.
 */

public class Food extends RealmObject{

    @PrimaryKey
    private String name;
    private String author;
    private String imageShow;
    private String type;
    private String time;
    private int sets;
    private String level;
    private int rating;
    private RealmList<Material> materials;
    private RealmList<Cook> cooks;

    public Food() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageShow() {
        return imageShow;
    }

    public void setImageShow(String imageShow) {
        this.imageShow = imageShow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public RealmList<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(RealmList<Material> materials) {
        this.materials = materials;
    }

    public RealmList<Cook> getCooks() {
        return cooks;
    }

    public void setCooks(RealmList<Cook> cooks) {
        this.cooks = cooks;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", imageShow='" + imageShow + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", sets=" + sets +
                ", level='" + level + '\'' +
                ", rating=" + rating +
                ", materials=" + materials +
                ", cooks=" + cooks +
                '}';
    }
}
