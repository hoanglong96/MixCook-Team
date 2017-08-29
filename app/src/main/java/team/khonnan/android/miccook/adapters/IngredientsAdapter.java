package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.createFoodModels.MaterialModel;


/**
 * Created by apple on 8/29/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    MaterialModel ingredientsModel;
    List<MaterialModel> ingredientsModels;
    Context context;

    public IngredientsAdapter(List<MaterialModel> ingredientsModels, Context context) {
        this.ingredientsModels = ingredientsModels;
        this.context = context;
    }

    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_ingredient, parent, false);

        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientsViewHolder holder, final int position) {
        MaterialModel ingredientsModel = ingredientsModels.get(position);
        String ingredients = ingredientsModel.getMatQuantum() + " " + ingredientsModel.getMatName();
        holder.tvIngredients.setText(ingredients);
        holder.btnDelIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsModels.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, ingredientsModels.size());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientsModels.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView tvIngredients;
        ImageView btnDelIngredients;

        public IngredientsViewHolder(View itemView) {
            super(itemView);

            tvIngredients = itemView.findViewById(R.id.tv_ingredients);
            btnDelIngredients = itemView.findViewById(R.id.del_ingredients);
        }
    }
}
