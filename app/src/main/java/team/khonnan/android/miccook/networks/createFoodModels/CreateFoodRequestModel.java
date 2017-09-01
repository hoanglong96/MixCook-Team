package team.khonnan.android.miccook.networks.createFoodModels;

import java.util.List;


/**
 * Created by tungthanh.1497 on 08/17/2017.
 */

public class CreateFoodRequestModel {
    private String name;
    private String author;
    private String authorName;
    private String imageShow;
    private int type;
    private String time;
    private int sets;
    private String level;
    private float rating;
    private int rateNum;
    List<MaterialModel> material;
    List<CookModel> cook;

    @Override
    public String toString() {
        return "CreateFoodRequestModel{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", authorName='" + authorName + '\'' +
                ", imageShow='" + imageShow + '\'' +
                ", type=" + type +
                ", time='" + time + '\'' +
                ", sets=" + sets +
                ", level='" + level + '\'' +
                ", rating=" + rating +
                ", rateNum=" + rateNum +
                ", material=" + material +
                ", cook=" + cook +
                '}';
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRateNum() {
        return rateNum;
    }

    public void setRateNum(int rateNum) {
        this.rateNum = rateNum;
    }

    public List<MaterialModel> getMaterial() {
        return material;
    }

    public void setMaterial(List<MaterialModel> material) {
        this.material = material;
    }

    public List<CookModel> getCook() {
        return cook;
    }

    public void setCook(List<CookModel> cook) {
        this.cook = cook;
    }

    public CreateFoodRequestModel(String name, String author, String authorName, String imageShow, int type, String time, int sets, String level, float rating, int rateNum, List<MaterialModel> material, List<CookModel> cook) {

        this.name = name;
        this.author = author;
        this.authorName = authorName;
        this.imageShow = imageShow;
        this.type = type;
        this.time = time;
        this.sets = sets;
        this.level = level;
        this.rating = rating;
        this.rateNum = rateNum;
        this.material = material;
        this.cook = cook;
    }

    public CreateFoodRequestModel() {

    }
}
