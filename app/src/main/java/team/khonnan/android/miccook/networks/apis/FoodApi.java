package team.khonnan.android.miccook.networks.apis;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by quyntg94 on 10/08/2017.
 */

public interface FoodApi {
    @GET("http://cookmix.herokuapp.com/getFood")
    Call<Food> callFood();
    class Food{
        @SerializedName("food")
        private List<FoodList> foodList;

        public List<FoodApi.FoodList> getFoodList(){
            return foodList;
        }
    }

    class FoodList {
        @SerializedName("name")
        private String name;
        @SerializedName("author")
        private String author;
        @SerializedName("imageShow")
        private String imageShow;
        @SerializedName("type")
        private String type;
        @SerializedName("time")
        private String time;
        @SerializedName("sets")
        private int sets;
        @SerializedName("level")
        private String level;
        @SerializedName("rating")
        private int rating;
        @SerializedName("material")
        private List<Material> materials;
        @SerializedName("cook")
        private List<Cook> cooks;

        public String getName() {
            return name;
        }

        public String getAuthor() {
            return author;
        }

        public String getImageShow() {
            return imageShow;
        }

        public String getType() {
            return type;
        }

        public String getTime() {
            return time;
        }

        public int getSets() {
            return sets;
        }

        public String getLevel() {
            return level;
        }

        public int getRating() {
            return rating;
        }

        public List<Material> getMaterials() {
            return materials;
        }

        public List<Cook> getCooks() {
            return cooks;
        }
    }

    class Material{
        @SerializedName("matName")
        private String matName;
        @SerializedName("matQuantum")
        private String matQuantum;

        public String getMatName() {
            return matName;
        }

        public String getMatQuantum() {
            return matQuantum;
        }
    }

    class Cook{
        @SerializedName("image")
        private String image;
        @SerializedName("note")
        private String note;

        public String getImage() {
            return image;
        }

        public String getNote() {
            return note;
        }
    }
}
