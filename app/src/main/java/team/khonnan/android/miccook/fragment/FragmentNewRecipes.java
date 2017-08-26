package team.khonnan.android.miccook.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import java.io.File;
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
import team.khonnan.android.miccook.networks.apis.CreateNewFood;
import team.khonnan.android.miccook.networks.apis.RetrofitFactory;
import team.khonnan.android.miccook.networks.createFoodModels.CookModel;
import team.khonnan.android.miccook.networks.createFoodModels.CreateFoodRequestModel;
import team.khonnan.android.miccook.networks.createFoodModels.MaterialModel;

import static android.app.Activity.RESULT_OK;

/**
 * Created by apple on 8/15/17.
 */

public class FragmentNewRecipes extends Fragment {
    //    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//    private String idFbUser = sharedPreferences.getString("id", "");
    private String idFbUser = "1497";
    private Button btCreateFood;
    private EditText etNameFood;
    private LinearLayout lnChooseImage;
    private View view1;
    private TextView tvTitleImage;
    private View view2;
    private ImageView ivImageShow;
    private Spinner spinnerType;
    private Spinner spinnerLevel;
    private EditText etTime;
    private EditText etSets;
    private EditText etDefaultMaterial;
    private EditText etDefaultCookStep;


    private final int IMG_REQUEST = 1;
    private final int TAKE_PHOTO_CODE = 2;
    CameraPhoto cameraPhoto;
    Uri path;
    private String absolutelyPath;
    boolean isGalery;
    private Bitmap bitmap;
    boolean success;


    private LinearLayout parentLinerLayout;
    private LinearLayout parentLinerLayoutAddCongThuc;

    private ImageView ivDel;
    private ImageView ivDelStepCook;

    private Toolbar toolbar;

    private ImageView ivAddNguyenLieu;
    private ImageView ivAddCongThuc;
    private ImageView ivAddImageCongThuc;

    LinearLayout lnDialogType;

    private static int RESULT_LOAD_IMAGE = 1;
    private static int ACTION_REQUEST_GALLERY = 1;
    private static int ACTION_REQUEST_CAMERA = 2;

    Spinner snType, snLevel;

    String type[] = {
            "Món chính",
            "Món ăn sáng",
            "Món ăn vặt",
            "Đồ uống", "Bánh"};

    String level[] = {
            "Khó", "Dễ", "Trung bình"
    };

    List<View> listMaterialAdded = new ArrayList<>();
    List<View> listCookStepAdded = new ArrayList<>();


    int i = 0;


    public FragmentNewRecipes() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_recipes, container, false);
        parentLinerLayout = view.findViewById(R.id.parent_linear_layout);
        parentLinerLayoutAddCongThuc = view.findViewById(R.id.parent_linear_layout_cong_thuc);

        setupUI(view);
        setHasOptionsMenu(true);

        return view;
    }

    public void setupUI(View view) {
        toolbar = view.findViewById(R.id.toolbar_new_recipes);
        toolbar.setTitle("Create new recipes");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btCreateFood = view.findViewById(R.id.bt_create_food);
        etNameFood = view.findViewById(R.id.et_name_food);
        view1 = view.findViewById(R.id.v_view1);
        tvTitleImage = view.findViewById(R.id.tv_title_image);
        view2 = view.findViewById(R.id.v_view2);
        ivImageShow = view.findViewById(R.id.iv_image_show);
        spinnerType = view.findViewById(R.id.spinner_type);
        spinnerLevel = view.findViewById(R.id.spinner_level);
        etTime = view.findViewById(R.id.et_time);
        etSets = view.findViewById(R.id.et_sets);
        etDefaultMaterial = view.findViewById(R.id.et_default_material);
        etDefaultCookStep = view.findViewById(R.id.et_default_cook_step);

        /*Cong thuc*/
        ivAddCongThuc = view.findViewById(R.id.add_field_button_cong_thuc);
        ivAddCongThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddViewCongThuc(view);
            }
        });


        /*Nguyen lieu*/
        ivAddNguyenLieu = view.findViewById(R.id.add_field_button);
        ivAddNguyenLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddView(view);
            }
        });

        //Choose Image
        lnChooseImage = view.findViewById(R.id.ln_choose_image);
        lnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });


        //Choose Image Cong Thuc
        ivAddImageCongThuc = view.findViewById(R.id.iv_add_step_food);
        ivAddImageCongThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        //Spinner
        snType = view.findViewById(R.id.spinner_type);
        snLevel = view.findViewById(R.id.spinner_level);

        ArrayAdapter<String> adapterType = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, type);
        adapterType.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        snType.setAdapter(adapterType);
        snType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), snType.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapterLevel = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, level);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        snLevel.setAdapter(adapterLevel);
        snLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), snLevel.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btCreateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewFood();
            }
        });
    }

    private void createNewFood() {

        boolean createSuccessful = true;
//                try {
        String name = etNameFood.getText().toString();
        String author = idFbUser;
        int type = (spinnerType.getSelectedItemPosition() + 1);
        String time = etTime.getText().toString() + " phút";
        int sets = Integer.parseInt(etSets.getText().toString());
        String level = spinnerLevel.getSelectedItem().toString();
        float rating = 0;
        int rateNum = 0;

        List<MaterialModel> material = new ArrayList<MaterialModel>();
        String matNameDefault = etDefaultMaterial.getText().toString();
        //TODO: create eddit text for this value
        String matQuantumDefault = "1";
        MaterialModel materialDefault = new MaterialModel(matNameDefault, matQuantumDefault);
        if (!matNameDefault.equals(""))
            material.add(materialDefault);
        for (View viewItemList : listMaterialAdded) {
            EditText etContent = viewItemList.findViewById(R.id.et_material);
            String matName = etContent.getText().toString();
            String matQuantum = "1";
            MaterialModel materialModel = new MaterialModel(matName, matQuantum);
            if (!matName.equals(""))
                material.add(materialModel);
        }

        List<CookModel> cook = new ArrayList<CookModel>();
        String imageDefault = "https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy-facebook_s.jpg";
        String noteDefault = etDefaultCookStep.getText().toString();
        CookModel cookDefault = new CookModel(imageDefault, noteDefault);
        if (!noteDefault.equals(""))
            cook.add(cookDefault);
        for (View viewItemList : listCookStepAdded) {
            EditText etContent = viewItemList.findViewById(R.id.et_cook_step);
            String image = "https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy-facebook_s.jpg";
            //TODO: create eddit text for this value
            String note = etContent.getText().toString();
            CookModel cookModel = new CookModel(imageDefault, noteDefault);
            if (!note.equals(""))
                cook.add(cookModel);
        }


        new UploadImage().execute();
        while (!success) {
        }
        String imageShow = "http://res.cloudinary.com/ddrpge3lm/image/upload/" + nameOfImage + ".jpg";
        if (name.length() == 0 || author.length() == 0 || imageShow.length() == 0
                || time.equals(" phút") || sets <= 0 || material.size() == 0 || cook.size() == 0)
            createSuccessful = false;


        CreateFoodRequestModel createFoodRequestModel = new CreateFoodRequestModel(name, author, imageShow, type, time, sets, level, rating, rateNum, material, cook);

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


//                } catch (Exception e) {
//                    createSuccessful = false;
//                    Toast.makeText(getContext(), "Create fail: " + e, Toast.LENGTH_SHORT).show();
//                }
        if (createSuccessful = false) {
            Toast.makeText(getContext(), "Create fail", Toast.LENGTH_SHORT).show();
        }
    }


    static int numCookStep = 0;

    //Add them view de tao cong thuc
    public void onAddViewCongThuc(View v) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_child_add_cong_thuc, null);
        parentLinerLayoutAddCongThuc.addView(v);
        v.setId(numCookStep++);
        listCookStepAdded.add(v);

        ivDelStepCook = v.findViewById(R.id.iv_delete_cook_step);
        ivDelStepCook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteCookStep(view);
            }
        });

    }

    public void onDeleteCookStep(View v) {
        parentLinerLayoutAddCongThuc.removeView((View) v.getParent());
        int id = ((View) v.getParent()).getId();
        for (View view : listCookStepAdded) {
            if (view.getId() == id) {
                listCookStepAdded.remove(id);
                numCookStep--;
                break;
            }
        }
        for (int i = 0; i < listCookStepAdded.size(); i++) {
            listCookStepAdded.get(i).setId(i);
        }
    }

    static int numNguyenLieu = 0;

    public void onAddView(View v) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_child_add_nguyen_lieu, null);
        parentLinerLayout.addView(v);

        v.setId(numNguyenLieu++);
        Log.d("abc", "onAddView: id view add " + v.getId());
//
//        EditText editText = new EditText(getActivity());
//        editText.setId(editText.generateViewId());

        listMaterialAdded.add(v);
        Log.d("abc", "onAddViewList: list size" + listMaterialAdded.size());


//        Log.d("abc", "onAddView: " + editText.generateViewId());
        ivDel = v.findViewById(R.id.iv_delete_material);
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteMaterial(view);
            }
        });
    }

    public void onDeleteMaterial(View v) {
        parentLinerLayout.removeView((View) v.getParent());
        int id = ((View) v.getParent()).getId();
        Log.d("abc", "onDeleteMaterial: id view need delete " + id);
        for (View view : listMaterialAdded) {
            Log.d("abc", "onDeleteMaterial: id view in for loop " + view.getId());
            if (view.getId() == id) {
                listMaterialAdded.remove(id);
                Log.d("abc", "onDeleteView: size list" + id + " " + listMaterialAdded.size());
                numNguyenLieu--;
                break;
            }
        }
        for (int i = 0; i < listMaterialAdded.size(); i++) {
            listMaterialAdded.get(i).setId(i);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_recipes, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                Toast.makeText(getContext(), "abc", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//
//        }

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            path = data.getData();
            Log.d("path", "onActivityResult: " + path);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);

                tvTitleImage.setVisibility(View.GONE);
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                ivImageShow.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.d("haizzzzzzz", "onActivityResult: " + e);
            }

            isGalery = true;
        }
        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {


//            if (hasImageCaptureBug()) {
//                File fi = new File("/sdcard/tmp");
//                try {
//                    path = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(getContentResolver(), fi.getAbsolutePath(), null, null));
//                    if (!fi.delete()) {
//                        Log.i("logMarker", "Failed to delete " + fi);
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
            absolutelyPath = cameraPhoto.getPhotoPath();
//            }


            Log.d("path camera", "onActivityResult: " + absolutelyPath);
            Bitmap photo = null;
            try {
                photo = ImageLoader.init().from(absolutelyPath).requestSize(512, 512).getBitmap();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ivImageShow.setImageBitmap(photo);
            tvTitleImage.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);

            isGalery = false;

//            path = data.getData();
//            Log.d("path camera", "onActivityResult: "+path);
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
//                absolutelyPath = imageToString(bitmap);
//                Log.d("absolutelyPath camera", "onActivityResult: " + absolutelyPath);
//                ivSelected.setImageBitmap(bitmap);
//                ivSelected.setVisibility(View.VISIBLE);
//                etName.setVisibility(View.VISIBLE);
//            } catch (IOException e) {
//                Log.d("haizzzzzzz camera", "onActivityResult: " + e);
//            }
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

                                // GET IMAGE FROM THE GALLERY
//                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                                intent.setType("image/*");
//
//                                Intent chooser = Intent.createChooser(intent, "Choose a Picture");
//                                startActivityForResult(chooser, ACTION_REQUEST_GALLERY);

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intent, IMG_REQUEST);

                                break;

                            case 1:
//                                Intent getCameraImage = new Intent("android.media.action.IMAGE_CAPTURE");
//
//                                File cameraFolder;
//
//                                if (android.os.Environment.getExternalStorageState().equals
//                                        (android.os.Environment.MEDIA_MOUNTED))
//                                    cameraFolder = new File(android.os.Environment.getExternalStorageDirectory(),
//                                            "some_directory_to_save_images/");
//                                else
//                                    cameraFolder = getContext().getCacheDir();
//                                if (!cameraFolder.exists())
//                                    cameraFolder.mkdirs();
//
//
//                                startActivityForResult(getCameraImage, ACTION_REQUEST_CAMERA);
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
        nameOfImage = "1497" + time;
        cloudinary.url()
                .transformation(new Transformation().width(100).height(150).crop("fill"))
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
