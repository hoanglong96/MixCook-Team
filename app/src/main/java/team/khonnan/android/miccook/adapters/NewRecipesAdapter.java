package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.model.Food;

/**
 * Created by apple on 8/21/17.
 */

public class NewRecipesAdapter extends RecyclerView.Adapter<NewRecipesAdapter.NewRecipesViewHolder> {
    private List<Food> foodList = new ArrayList<>();
    private Context context;

    public NewRecipesAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }


    @Override
    public NewRecipesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_new_recipes, viewGroup, false);

        return new NewRecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewRecipesViewHolder newRecipesViewHolder, int position) {
        newRecipesViewHolder.setData(foodList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class NewRecipesViewHolder extends RecyclerView.ViewHolder{
        ImageView ivNewRecipes;
        TextView tvRating,tvAuthor,tvName;

        public NewRecipesViewHolder(View itemView) {
            super(itemView);
            ivNewRecipes = itemView.findViewById(R.id.iv_new_recipes);
            tvAuthor = itemView.findViewById(R.id.tv_author_new_recipes);
            tvName = itemView.findViewById(R.id.tv_name_new_recipes);
            tvRating = itemView.findViewById(R.id.rating_number);
        }

        public void setData(Food foodModel){
            Picasso.with(context).load(foodModel.getImageShow()).into(ivNewRecipes);
            tvAuthor.setText(foodModel.getAuthor());
            tvName.setText(foodModel.getName());
            tvRating.setText(foodModel.getRating());
        }
    }
}
