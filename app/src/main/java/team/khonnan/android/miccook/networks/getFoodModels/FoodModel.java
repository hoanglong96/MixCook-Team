package team.khonnan.android.miccook.networks.getFoodModels;

import java.util.List;

/**
 * Created by tungthanh.1497 on 08/11/2017.
 */

public class FoodModel {
    private String _id;
    private String name;
    private String author;
    private String imageShow;
    private int type;
    private String time;
    private int sets;
    private String level;
    private int rating;
    private int rateNum;
    private List<CookModel> cook;
    private List<MaterialModel> material;

    @Override
    public String toString() {
        return "FoodModel{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", imageShow='" + imageShow + '\'' +
                ", type=" + type +
                ", time='" + time + '\'' +
                ", sets=" + sets +
                ", level='" + level + '\'' +
                ", rating=" + rating +
                ", rateNum=" + rateNum +
                ", cook=" + cook +
                ", material=" + material +
                '}';
    }

    public FoodModel(String _id, String name, String author, String imageShow, int type, String time, int sets, String level, int rating, int rateNum, List<CookModel> cook, List<MaterialModel> material) {
        this._id = _id;
        this.name = name;
        this.author = author;
        this.imageShow = imageShow;
        this.type = type;
        this.time = time;
        this.sets = sets;
        this.level = level;
        this.rating = rating;
        this.rateNum = rateNum;
        this.cook = cook;
        this.material = material;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    public List<CookModel> getCook() {
        return cook;
    }

    public void setCook(List<CookModel> cook) {
        this.cook = cook;
    }

    public List<MaterialModel> getMaterial() {
        return material;
    }

    public void setMaterial(List<MaterialModel> material) {
        this.material = material;
    }
}
