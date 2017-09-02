package team.khonnan.android.miccook.fragment.DetailFoodFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.CookAdapter;
import team.khonnan.android.miccook.event.OnClickFood;
import team.khonnan.android.miccook.networks.getFoodModels.CookModel;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;

import static android.view.View.inflate;

/**
 * Created by apple on 8/25/17.
 */

public class CookFragment extends Fragment {
//    private VerticalStepView mSetpview0;
    private FoodModel foodModel;
    private ArrayList<String> listCookStep = new ArrayList<>();
    private List<CookModel> cookModels = new ArrayList<>();
    private RecyclerView rvCook;
    private CookAdapter cookAdapter;
    TextView cookTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View mView = inflate(getActivity(), R.layout.fragment_cook, null);
        setup(mView);
        return mView;
    }

    @Subscribe(sticky = true)
    public void onEvent(OnClickFood onClickFood) {
        foodModel = onClickFood.getFoodModel();
    }

    boolean isFirstTime = true;
    public void setup(View view)
    {

        if(isFirstTime) {
            EventBus.getDefault().register(this);
            isFirstTime=false;
        }
//        mSetpview0 = view.findViewById(R.id.step_view0);

        List<CookModel> food = new ArrayList<>();
        cookModels = foodModel.getCook();
        for(int i = cookModels.size()-1;i>=0;i--){
            food.add(cookModels.get(i));
        }

        cookTime = view.findViewById(R.id.tv_time);
        String[] time = foodModel.getTime().split(" ");
        cookTime.setText(time[0]);
        rvCook = view.findViewById(R.id.rv_cook);
        cookAdapter = new CookAdapter(food,getContext());
        rvCook.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,true));
        rvCook.setAdapter(cookAdapter);
        cookAdapter.notifyDataSetChanged();

    }
}
