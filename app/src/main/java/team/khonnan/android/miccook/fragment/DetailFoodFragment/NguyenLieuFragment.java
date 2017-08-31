package team.khonnan.android.miccook.fragment.DetailFoodFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.NguyenLieuAdapter;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.event.onClickAddToShop;
import team.khonnan.android.miccook.fragment.ShopFragment.FragmentShopping;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.MaterialModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/25/17.
 */

public class NguyenLieuFragment extends Fragment {

    private FoodModel foodModel;
    private List<MaterialModel> materialModels = new ArrayList<>();
    private RecyclerView rvNguyenLieu;
    private NguyenLieuAdapter nguyenLieuAdapter;
    private TextView tvSet;
    private Button btnShopping;
    private List<FoodModel> listFoodInShop = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nguyenlieu, container, false);
        setupUI(view);
        return view;
    }


    @Subscribe(sticky = true)
    public void onEvent(OnClickFood onClickFood) {
        foodModel = onClickFood.getFoodModel();
        Log.d(TAG, "onEventOneFragment: " + foodModel);
    }



    boolean isFirstTime = true;
    public void setupUI(View view){
        if(isFirstTime) {
            EventBus.getDefault().register(this);
            isFirstTime=false;
        }
        rvNguyenLieu = view.findViewById(R.id.rv_nguyen_lieu);
        tvSet = view.findViewById(R.id.tv_set);

        String set = String.valueOf(foodModel.getSets());
        tvSet.setText(set);

        materialModels = foodModel.getMaterial();

        List<MaterialModel> materialModelList = new ArrayList<>();

        for(int i= materialModels.size()-1;i>=0;i--){
            materialModelList.add(materialModels.get(i));
        }

        nguyenLieuAdapter = new NguyenLieuAdapter(materialModelList,getContext());
        rvNguyenLieu.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
        rvNguyenLieu.setAdapter(nguyenLieuAdapter);
        nguyenLieuAdapter.notifyDataSetChanged();

        btnShopping = view.findViewById(R.id.btn_shopping);
        btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Đăng ký events bus để chuyển dữ liệu sang màn shopping
                EventBus.getDefault().postSticky(new onClickAddToShop(foodModel));

                //Snackbar
                Snackbar snackbar = Snackbar
                        .make(view, "Đã thêm món ăn vào đi chợ", Snackbar.LENGTH_LONG)
                        .setAction("VIEW SHOP", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentShopping(),R.id.drawer_layout);
                            }
                        });

                snackbar.show();
            }
        });
    }
}
