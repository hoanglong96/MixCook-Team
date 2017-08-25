package team.khonnan.android.miccook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.ShopItemAdapter;
import team.khonnan.android.miccook.event.OnClickShopItem;
import team.khonnan.android.miccook.event.onClickAddToShop;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

import static com.facebook.login.widget.ProfilePictureView.TAG;

/**
 * Created by apple on 8/25/17.
 */

public class FragmentShopping extends Fragment {

    private FoodModel foodModel;
    private List<FoodModel> listShop = new ArrayList<>();
    private RecyclerView rvShop;
    private ShopItemAdapter shopItemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        setupUI(view);
        return view;
    }

    @Subscribe(sticky = true)
    public void onEvent(onClickAddToShop onClickAddToShop) {
        foodModel = onClickAddToShop.getFoodModel();
        Log.d(TAG, "onEventOneFragment: " + foodModel);
    }

    private void setupUI(View view) {
        EventBus.getDefault().register(this);
        rvShop = view.findViewById(R.id.rv_shopping);

        listShop.add(foodModel);

        shopItemAdapter = new ShopItemAdapter(listShop,getContext());
        rvShop.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
        rvShop.setAdapter(shopItemAdapter);
        shopItemAdapter.notifyDataSetChanged();

        shopItemAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodModel foodModel = (FoodModel) view.getTag();
                EventBus.getDefault().postSticky(new OnClickShopItem(foodModel));
                ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentDetailShop(),R.id.drawer_layout);
            }
        });

    }
}
