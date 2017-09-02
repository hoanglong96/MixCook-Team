package team.khonnan.android.miccook;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.fragment.FragmentFavorites;
import team.khonnan.android.miccook.fragment.HomeFragment.HomeFragment;
import team.khonnan.android.miccook.fragment.NewRecipesFragment.FragmentNewRecipes;
import team.khonnan.android.miccook.fragment.ProfileFragment.FragmentProfileAccount;
import team.khonnan.android.miccook.fragment.SearchFragment;
import team.khonnan.android.miccook.fragment.SeeMoreFragment;
import team.khonnan.android.miccook.fragment.ShopFragment.FragmentShopping;
import team.khonnan.android.miccook.managers.ScreenManager;
import team.khonnan.android.miccook.networks.apis.GetFoodByType;
import team.khonnan.android.miccook.networks.apis.GetUserProfile;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.getFoodModels.FoodModel;
import team.khonnan.android.miccook.networks.getFoodModels.GetFoodRespondModel;
import team.khonnan.android.miccook.networks.getUserProfileModels.UserProfileModel;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    ImageView avatarUser;
    private final int TAKE_PHOTO_CODE = 2;
    TextView tvName;
    Toolbar toolbar;
    ImageView ivSearch;
    List<FoodModel> foodMonChinh = new ArrayList<>();
    List<FoodModel> foodAnSang = new ArrayList<>();
    List<FoodModel> foodAnVat = new ArrayList<>();
    List<FoodModel> foodMonBanh = new ArrayList<>();
    List<FoodModel> foodDoUong = new ArrayList<>();
    private SliderLayout mDemoSlider;
    SharedPreferences sharedPreferences;

    Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivSearch = toolbar.findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getSupportFragmentManager(), new SearchFragment(), R.id.drawer_layout);
            }
        });
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        getCurrentUserProfile();
        String id = sharedPreferences.getString("id", "");
        String name = sharedPreferences.getString("name", "");
        View headerView = navigationView.getHeaderView(0);
        avatarUser = headerView.findViewById(R.id.iv_avatar_user);
        tvName = headerView.findViewById(R.id.tv_name_nav_header);


        tvName.setText(name);
        Picasso.with(getBaseContext()).load("https://graph.facebook.com/" + id
                + "/picture?type=large").into(avatarUser);
        avatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "abc", Toast.LENGTH_SHORT).show();
            }
        });


        ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.content_main);

        //load type food
        final GetFoodByType getFoodByType = RetrofitFactory.getInstance().create(GetFoodByType.class);
        getFoodByType.getFoodByType("1").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodMonChinh = response.body().getFood();
                }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });
        getFoodByType.getFoodByType("2").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodAnSang = response.body().getFood();
                Log.d("gaugau", "foodAnSang: " + foodAnSang);
            }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });
        getFoodByType.getFoodByType("3").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodAnVat = response.body().getFood();
                }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });
        getFoodByType.getFoodByType("4").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodMonBanh = response.body().getFood();
                }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });
        getFoodByType.getFoodByType("5").enqueue(new Callback<GetFoodRespondModel>() {
            @Override
            public void onResponse(Call<GetFoodRespondModel> call, Response<GetFoodRespondModel> response) {
                foodDoUong = response.body().getFood();
                }

            @Override
            public void onFailure(Call<GetFoodRespondModel> call, Throwable t) {
                Log.d("meomeo", "onFailure: Can not reach data");
            }
        });

        //Slider
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);


        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Main dishes", R.drawable.mainfood);
        file_maps.put("Breakfast", R.drawable.breakfast);
        file_maps.put("Snacks", R.drawable.snack);
        file_maps.put("Cake", R.drawable.cake);
        file_maps.put("Drink", R.drawable.drink);

        for (String idname : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(idname))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", idname);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getSupportFragmentManager(), new FragmentNewRecipes(), R.id.drawer_layout);
            }
        });

        //Permission

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case TAKE_PHOTO_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(getSupportFragmentManager().getBackStackEntryCount() != 1){
            super.onBackPressed();
        }else{
            if (doubleBackToExitPressedOnce) {
                System.exit(0);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.info_user) {
            ScreenManager.openFragment(getSupportFragmentManager(), new FragmentProfileAccount(), R.id.drawer_layout);
        } else if (id == R.id.dangxuat) {
            SharedPreferences preferences = getSharedPreferences("checkLogin", MODE_PRIVATE);
            preferences.edit().remove("isLogin").commit();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_shop) {
            ScreenManager.openFragment(getSupportFragmentManager(), new FragmentShopping(), R.id.drawer_layout);
        } else if (id == R.id.nav_home) {
            ScreenManager.openFragment(getSupportFragmentManager(), new HomeFragment(), R.id.content_main);
        } else if (id == R.id.nav_favorites) {
            ScreenManager.openFragment(getSupportFragmentManager(), new FragmentFavorites(), R.id.drawer_layout);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String nameType = (String) slider.getBundle().get("extra");
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
        if (nameType.equals("Main dishes")) {
            ScreenManager.openFragment(getSupportFragmentManager(),
                    new SeeMoreFragment(), R.id.drawer_layout, foodMonChinh, "Main dishes");
        } else if (nameType.equals("Breakfast")) {
            ScreenManager.openFragment(getSupportFragmentManager(),
                    new SeeMoreFragment(), R.id.drawer_layout, foodAnSang, "Breakfast");
        } else if (nameType.equals("Snacks")) {
            ScreenManager.openFragment(getSupportFragmentManager(),
                    new SeeMoreFragment(), R.id.drawer_layout, foodAnVat, "Snacks");
        } else if (nameType.equals("Drink")) {
            ScreenManager.openFragment(getSupportFragmentManager(),
                    new SeeMoreFragment(), R.id.drawer_layout, foodDoUong, "Drink");
        } else if (nameType.equals("Cake")) {
            ScreenManager.openFragment(getSupportFragmentManager(),
                    new SeeMoreFragment(), R.id.drawer_layout, foodMonBanh, "Cake");
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public static UserProfileModel userProfileModel;

    void getCurrentUserProfile() {
        final GetUserProfile getUserProfile = RetrofitFactory.getInstance().create(GetUserProfile.class);
        getUserProfile.getUserProfile(sharedPreferences.getString("id", "")).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(Call<UserProfileModel> call, Response<UserProfileModel> response) {
                userProfileModel = response.body();
            }

            @Override
            public void onFailure(Call<UserProfileModel> call, Throwable t) {

            }
        });
    }

}
