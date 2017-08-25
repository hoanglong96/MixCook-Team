package team.khonnan.android.miccook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

/**
 * Created by apple on 8/25/17.
 */

public class NguyenLieuAdapter extends RecyclerView.Adapter<NguyenLieuAdapter.NguyenLieuViewHolder>  {

    private List<MaterialModel> materialModels = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;
    private MaterialModel materialModel;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public NguyenLieuAdapter(List<MaterialModel> materialModels, Context context) {
        this.materialModels = materialModels;
        this.context = context;
    }

    @Override
    public NguyenLieuAdapter.NguyenLieuViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_nguyenlieu, viewGroup, false);
        view.setOnClickListener(onClickListener);

        return new NguyenLieuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NguyenLieuAdapter.NguyenLieuViewHolder nguyenLieuViewHolder, int i) {
        nguyenLieuViewHolder.setData(materialModels.get(i));
    }

    @Override
    public int getItemCount() {
        return materialModels.size();
    }

    public class NguyenLieuViewHolder extends RecyclerView.ViewHolder {

        TextView tvNguyenLieu;
        TextView slNguyenLieu;

        public NguyenLieuViewHolder(View itemView) {
            super(itemView);
            tvNguyenLieu = itemView.findViewById(R.id.item_nguyenlieu);
            slNguyenLieu = itemView.findViewById(R.id.sl_nguyenlieu);
        }

        public void setData(MaterialModel materialModel){
            tvNguyenLieu.setText(materialModel.getMatName());
            slNguyenLieu.setText(materialModel.getMatQuantum());
        }
    }
}
