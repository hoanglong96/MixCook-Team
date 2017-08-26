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
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 8/21/17.
 */

public class NewRecipesAdapter extends RecyclerView.Adapter<NewRecipesAdapter.NewRecipesViewHolder> {
    private List<FoodModel> foodList = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public NewRecipesAdapter(List<FoodModel> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }


    @Override
    public NewRecipesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_new_recipes, viewGroup, false);
        view.setOnClickListener(onClickListener);

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
        TextView tvName;
        View view;

        public NewRecipesViewHolder(View itemView) {
            super(itemView);
            ivNewRecipes = itemView.findViewById(R.id.iv_new_recipes);
            tvName = itemView.findViewById(R.id.tv_name_new_recipes);

            view = itemView;
        }

        public void setData(FoodModel foodModel){
            Picasso.with(context).load(foodModel.getImageShow()).resize(150,150).into(ivNewRecipes);
            tvName.setText(foodModel.getName());
            view.setTag(foodModel);
        }
    }
}
