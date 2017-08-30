package team.khonnan.android.miccook.fragment.NewRecipes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import team.khonnan.android.miccook.R;
import team.khonnan.android.miccook.adapters.HowAdapter;
import team.khonnan.android.miccook.adapters.IngredientsAdapter;
import team.khonnan.android.miccook.networks.apis.CreateNewFood;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.createFoodModels.CookModel;
import team.khonnan.android.miccook.networks.createFoodModels.CreateFoodRequestModel;
import team.khonnan.android.miccook.networks.createFoodModels.MaterialModel;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by apple on 8/15/17.
 */

public class FragmentNewRecipes extends Fragment {

    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userInfo", MODE_PRIVATE);
    String id = sharedPreferences.getString("id", "");
    private String idFbUser = id;

    TextView tvSet,tvLevel,tvCategories;
    ImageView ivRecipes,ivEasy,ivMedium,ivHard,btnAddIngredients,btnAddHow;
    ImageView ivChooseImage;
    CheckBox cbMainFood,cbBreakfast,cbSnacks,cbCake,cbDrink;
    SeekBar sbSet;
    SeekBar sbLevel;
    EditText etNameRecipes,etIngredients,etTimeCook,etHow,etQuantum;
    Button btnSendRecipes;

    List<MaterialModel> listIngredients = new ArrayList<>();
    List<CookModel> listHow = new ArrayList<>();

    RecyclerView rvIngredients;
    RecyclerView rvHow;

    MaterialModel ingredientsModel;
    CookModel howModel;

    IngredientsAdapter adapterIngredients;
    HowAdapter howAdapter;


    private final int IMG_REQUEST = 1;
    private final int TAKE_PHOTO_CODE = 2;
    CameraPhoto cameraPhoto;
    Uri path;
    private String absolutelyPath;
    boolean isGalery;
    private Bitmap bitmap;
    boolean success;

    private Toolbar toolbar;

    public FragmentNewRecipes() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_recipes, container, false);

        toolbar = view.findViewById(R.id.toolbar_new_recipes);
        toolbar.setTitle("Create new recipes");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Log.d("new recipes", "onCreateView: " + idFbUser);

        setupUI(view);
        seekBar();
        checkBox();

        //RecycleView Ingredients
        adapterIngredients = new IngredientsAdapter(listIngredients,getApplicationContext());
        rvIngredients.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true));
        rvIngredients.setAdapter(adapterIngredients);
        adapterIngredients.notifyDataSetChanged();


        btnAddIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsModel = new MaterialModel(etIngredients.getText().toString(),etQuantum.getText().toString());
                listIngredients.add(ingredientsModel);
                Log.d("abc", "onClick: " + ingredientsModel);
                adapterIngredients.notifyDataSetChanged();

                etIngredients.setText("");
                etQuantum.setText("");
            }
        });

        //RecycleView How
        howAdapter = new HowAdapter(listHow,getApplicationContext());
        rvHow.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,true));
        rvHow.setAdapter(howAdapter);
        howAdapter.notifyDataSetChanged();

        btnAddHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                howModel = new CookModel(etHow.getText().toString());
                listHow.add(howModel);
                howAdapter.notifyDataSetChanged();
                etHow.setText("");
            }
        });


        //Choose Image
        ivChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        btnSendRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewFood();
            }
        });

        return view;
    }

    public void setupUI(View view){
        tvSet = (TextView) view.findViewById(R.id.tv_set);
        tvLevel = (TextView) view.findViewById(R.id.tv_level);
        tvCategories = (TextView) view.findViewById(R.id.tv_categories);


        ivRecipes = (ImageView) view.findViewById(R.id.iv_recipes);
        ivEasy = (ImageView) view.findViewById(R.id.iv_easy);
        ivMedium = (ImageView) view.findViewById(R.id.iv_medium);
        ivHard = (ImageView) view.findViewById(R.id.iv_hard);
        btnAddIngredients = (ImageView) view.findViewById(R.id.add_list_ingredients);
        btnAddHow = (ImageView) view.findViewById(R.id.add_list_how);

        ivChooseImage = (ImageView) view.findViewById(R.id.choose_image);

        cbMainFood = (CheckBox) view.findViewById(R.id.cb_mainfood);
        cbBreakfast= (CheckBox) view.findViewById(R.id.cb_breakfast);
        cbSnacks = (CheckBox) view.findViewById(R.id.cb_snack);
        cbCake = (CheckBox) view.findViewById(R.id.cb_cake);
        cbDrink = (CheckBox) view.findViewById(R.id.cb_drink);

        sbSet = (SeekBar) view.findViewById(R.id.sb_set);
        sbLevel = (SeekBar) view.findViewById(R.id.sb_level);

        etNameRecipes = (EditText) view.findViewById(R.id.et_name);
        etIngredients = (EditText) view.findViewById(R.id.et_ingredients);
        etTimeCook = (EditText) view.findViewById(R.id.et_time_cook);
        etHow = (EditText) view.findViewById(R.id.et_how);
        etQuantum = view.findViewById(R.id.et_matQuantum);

        btnSendRecipes = (Button) view.findViewById(R.id.btn_send_recipes);

        rvIngredients = (RecyclerView) view.findViewById(R.id.lv_ingredients);
        rvHow = (RecyclerView) view.findViewById(R.id.lv_how);
    }

    public void seekBar(){

        //seekbar set
        sbSet.setMax(12);
        final int min = 0;
        final int[] progress = new int[1];
        sbSet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress[0] = min + i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvSet.setText(String.valueOf(progress[0]));
                Log.d("abc", "onProgressChanged: " + sbSet.getProgress());
                Log.d("abc", "onStopTrackingTouch: " + progress[0]);
            }
        });

        //seekbar level
        sbLevel.setMax(3);
        final int minLevel = 0;
        final int[] progressLevel = new int[1];
        sbLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressLevel[0] = minLevel + i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(progressLevel[0] == 1){
                    tvLevel.setText("Easy");
                    ivMedium.setVisibility(View.GONE);
                    ivHard.setVisibility(View.GONE);
                }else if(progressLevel[0] == 2){
                    tvLevel.setText("Medium");
                    ivHard.setVisibility(View.GONE);
                    ivMedium.setVisibility(View.VISIBLE);
                }else if(progressLevel[0] == 3 ){
                    tvLevel.setText("Hard");
                    ivHard.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void checkBox(){

        cbMainFood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if(isCheck){
                    Toast.makeText(getApplicationContext(), "Main Food", Toast.LENGTH_SHORT).show();
                    tvCategories.setText("Main Food");
                }
            }
        });

        cbBreakfast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if(isCheck){
                    Toast.makeText(getApplicationContext(), "Breakfast", Toast.LENGTH_SHORT).show();
                    tvCategories.setText("Breakfast");
                }
            }
        });

        cbSnacks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if(isCheck){
                    Toast.makeText(getApplicationContext(), "Snacks", Toast.LENGTH_SHORT).show();
                    tvCategories.setText("Snacks");
                }
            }
        });

        cbDrink.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if(isCheck){
                    Toast.makeText(getApplicationContext(), "Drink", Toast.LENGTH_SHORT).show();
                    tvCategories.setText("Drink");
                }
            }
        });

        cbCake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if(isCheck){
                    Toast.makeText(getApplicationContext(), "Cake", Toast.LENGTH_SHORT).show();
                    tvCategories.setText("Cake");
                }
            }
        });

    }




    private void createNewFood() {

        boolean createSuccessful = true;

        String name = etNameRecipes.getText().toString();
        String author = idFbUser;

        String categories = tvCategories.getText().toString();
        int type = 0;

        if(categories.equals("Main Food")){
            type = 1;
        }else if(categories.equals("Breakfast")){
            type = 2;
        }else if(categories.equals("Snacks")){
            type = 3;
        }else if(categories.equals("Cake")){
            type = 4;
        }else if(categories.equals("Drink")){
            type = 5;
        }


        String time = etTimeCook.getText().toString() + " phút";
        int sets = Integer.parseInt(tvSet.getText().toString());
        String level = tvLevel.getText().toString();
        float rating = 0;
        int rateNum = 0;

        new UploadImage().execute();
        while (!success) {
        }
        String imageShow = "http://res.cloudinary.com/ddrpge3lm/image/upload/w_300,c_scale/" + nameOfImage + ".jpg";
        if (name.length() == 0 || author.length() == 0 || imageShow.length() == 0
                || time.equals(" phút") || sets <= 0 || listIngredients.size() == 0 || listHow.size() == 0)
            createSuccessful = false;


        CreateFoodRequestModel createFoodRequestModel = new CreateFoodRequestModel(name, author, imageShow, type, time, sets, level, rating, rateNum, listIngredients, listHow);

        final CreateNewFood createNewFood = RetrofitFactory.getInstance().create(CreateNewFood.class);
        Call<CreateFoodRequestModel> call = createNewFood.createNewFood(createFoodRequestModel);
        call.enqueue(new Callback<CreateFoodRequestModel>() {
            @Override
            public void onResponse(Call<CreateFoodRequestModel> call, Response<CreateFoodRequestModel> response) {

                Toast.makeText(getContext(), "Create successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CreateFoodRequestModel> call, Throwable t) {

                Toast.makeText(getContext(), "Connect fail", Toast.LENGTH_SHORT).show();
            }
        });


        if (createSuccessful = false) {
            Toast.makeText(getContext(), "Create fail", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            path = data.getData();
            Log.d("path", "onActivityResult: " + path);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                ivChooseImage.setImageBitmap(bitmap);
                ivRecipes.setVisibility(View.GONE);
            } catch (IOException e) {
                Log.d("haizzzzzzz", "onActivityResult: " + e);
            }

            isGalery = true;
        }
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {

            absolutelyPath = cameraPhoto.getPhotoPath();
//            }


            Log.d("path camera", "onActivityResult: " + absolutelyPath);
            Bitmap photo = null;
            try {
                photo = ImageLoader.init().from(absolutelyPath).requestSize(512, 512).getBitmap();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ivChooseImage.setImageBitmap(photo);
            ivRecipes.setVisibility(View.GONE);

            isGalery = false;
        }
    }

    public void chooseImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, IMG_REQUEST);
                                break;
                            case 1:
                                Toast.makeText(getContext(), "take camera now", Toast.LENGTH_SHORT).show();
                                cameraPhoto = new CameraPhoto(getActivity().getApplicationContext());
                                try {
                                    startActivityForResult(cameraPhoto.takePhotoIntent(), TAKE_PHOTO_CODE);
                                    cameraPhoto.addToGallery();
                                    Toast.makeText(getContext(), "take camera done", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.d("camera fail", "onClick: " + e);
                                    Toast.makeText(getContext(), "error " + e, Toast.LENGTH_SHORT).show();
                                }

                                break;

                            default:
                                break;
                        }
                    }
                });

        builder.show();
    }

    String nameOfImage;

    private void uploadImage() {
        success = false;
        Map config = new HashMap();
        config.put("cloud_name", "ddrpge3lm");
        config.put("api_key", "538532228758364");
        config.put("api_secret", "MLk-IOvJtFtKcLu8jksBVYmXuW0");
        Cloudinary cloudinary = new Cloudinary("cloudinary://538532228758364:MLk-IOvJtFtKcLu8jksBVYmXuW0@ddrpge3lm");
        //TODO: id fb of user
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentTime = Calendar.getInstance().getTime();
        String time = calendar.get(Calendar.YEAR) + "" + calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.DATE)
                + "" + currentTime.getHours() + "" + currentTime.getMinutes() + "" + currentTime.getSeconds();
        nameOfImage = idFbUser + time;
        cloudinary.url()
                .transformation(new Transformation().width(300).crop("fill"))
                .generate(nameOfImage + ".jpg");
        try {
//                InputStream inputStream = getContentResolver().openInputStream(path);
//                java.net.URI juri = new java.net.URI(path.toString());
//                InputStream inputStream = juri.toURL().openConnection().getInputStream();
            InputStream inputStream;
            if (isGalery)
                inputStream = getActivity().getContentResolver().openInputStream(path);
            else
                inputStream = new FileInputStream(absolutelyPath);
            cloudinary.uploader().upload(inputStream, ObjectUtils.asMap("public_id", nameOfImage));
            success = true;
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
        success = false;
    }

    class UploadImage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            uploadImage();
            return null;
        }
    }
}
