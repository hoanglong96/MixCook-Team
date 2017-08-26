package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.model.Type;

/**
 * Created by apple on 8/21/17.
 */

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {

    private List<Type> catalogList;
    private View.OnClickListener onClickListener;
    private Context context;

    public void setOnItemClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CatalogAdapter(List<Type> catalogList, Context context) {
        this.catalogList = catalogList;
        this.context = context;
    }

    @Override
    public CatalogAdapter.CatalogViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_new_catolog, viewGroup, false);
        view.setOnClickListener(onClickListener);

        return new CatalogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogAdapter.CatalogViewHolder holder, int position) {
        Type type = catalogList.get(position);
        holder.tvType.setText(type.getType());
        //holder.ivType.setBackgroundResource(type.getUrlImage());
        Picasso.with(context).load(type.getUrlImage()).resize(150,150).into(holder.ivType);
        holder.view.setTag(type);
    }

    @Override
    public int getItemCount() {
        return catalogList.size();
    }

    public class CatalogViewHolder extends RecyclerView.ViewHolder {

        TextView tvType;
        ImageView ivType;
        View view;

        public CatalogViewHolder(View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_type);
            ivType = itemView.findViewById(R.id.iv_catalog);

            view = itemView;

        }
    }
}
