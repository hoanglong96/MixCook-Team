package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

/**
 * Created by apple on 9/1/17.
 */

public class TopFoodAdapter extends RecyclerView.Adapter<TopFoodAdapter.TopViewHolder> {
    private List<FoodModel> foodTopList = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public TopFoodAdapter(List<FoodModel> foodTopList, Context context) {
        this.foodTopList = foodTopList;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public TopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_top_food, parent, false);
        view.setOnClickListener(onClickListener);

        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopViewHolder holder, int position) {
        holder.setData(foodTopList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodTopList.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFood;
        TextView tvNameFood,tvNameUser;
        RatingBar rtFood;
        View view;

        public TopViewHolder(View itemView) {
            super(itemView);

            ivFood = itemView.findViewById(R.id.iv_top_food);
            tvNameFood = itemView.findViewById(R.id.tv_name_top_food);
            tvNameUser = itemView.findViewById(R.id.tv_user_name_top_food);
            rtFood = itemView.findViewById(R.id.rating_top);

            view = itemView;
        }

        public void setData(FoodModel foodModel) {
            Picasso.with(context).load(foodModel.getImageShow()).resize(500,500).into(ivFood);
            tvNameFood.setText(foodModel.getName());
            tvNameUser.setText(foodModel.getAuthorName());
            rtFood.setRating(foodModel.getRating());
            view.setTag(foodModel);
        }
    }

}
