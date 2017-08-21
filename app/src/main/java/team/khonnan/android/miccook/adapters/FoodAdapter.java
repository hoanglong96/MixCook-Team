package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.graphics.Color;
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
 * Created by quyntg94 on 10/08/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private List<FoodModel> foodModelList = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public void setOnItemClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public FoodAdapter(List<FoodModel> foodModelList, Context context) {
        this.foodModelList = foodModelList;
        this.context = context;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_see_more, viewGroup, false);
        view.setOnClickListener(onClickListener);

        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder foodViewHolder, int position) {
        foodViewHolder.setData(foodModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return foodModelList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFood;
        TextView tvLevel,tvAuthor,tvNameFood;
        RatingBar ratingBar;

        public FoodViewHolder(View itemView) {
            super(itemView);
            ivFood = itemView.findViewById(R.id.iv_food);
            tvLevel = itemView.findViewById(R.id.tv_level);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvNameFood = itemView.findViewById(R.id.tv_name_food);
            ratingBar = itemView.findViewById(R.id.rating_bar);
        }

        public void setData(FoodModel foodModel){
            if(foodModelList!= null){
                Picasso.with(context).load(foodModel.getImageShow()).into(ivFood);
                if(foodModel.getLevel().equals("Trung Bình")){
                    tvLevel.setText("TB");
                    tvLevel.setTextColor(Color.WHITE);
                }else if(foodModel.getLevel().equals("Khó")){
                    tvLevel.setText("Khó");
                    tvLevel.setTextColor(Color.WHITE);
                }else if(foodModel.getLevel().equals("Dễ")){
                    tvLevel.setText("Dễ");
                    tvLevel.setTextColor(Color.WHITE);
                }

                tvAuthor.setText(foodModel.getAuthor());
                tvNameFood.setText(foodModel.getName());

                ratingBar.setRating(foodModel.getRating());
                ratingBar.setIsIndicator(true);

            }
        }
    }
}
