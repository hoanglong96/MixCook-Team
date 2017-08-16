package team.khonnan.android.miccook.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import team.khonnan.android.miccook.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by apple on 8/15/17.
 */

public class FragmentNewRecipes extends Fragment {

    private LinearLayout parentLinerLayout;
    private LinearLayout parentLinerLayoutAddCongThuc;
    private LinearLayout lnChooseImage;

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

    Spinner snType,snLevel;

    String type[]={
            "Món chính",
            "Món ăn sáng",
            "Món ăn vặt",
    "Đồ uống","Bánh"};

    String level[]={
            "Khó","Dễ","Trung bình"
    };

    List<View> listEditText = new ArrayList<>();

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

        ArrayAdapter<String> adapterType = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,type);
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

        ArrayAdapter<String> adapterLevel = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,level);
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




    }


    public void loadData(){
        for(View view: listEditText){
            EditText etContent = view.findViewById(R.id.number_edit_text);
            String s = etContent.getText().toString();
            Log.d("ahuhu", "loadData: "+s);
        }
    }


    //Add them view de tao cong thuc
    public void onAddViewCongThuc(View v) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_child_add_cong_thuc, null);
        parentLinerLayoutAddCongThuc.addView(v);
        ivDelStepCook = v.findViewById(R.id.add_field_button_cong_thuc);
//        ivDelStepCook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onDelete(view);
//            }
//        });

    }

    static int numNguyenLieu=0;

    public void onAddView(View v) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.view_child_add_nguyen_lieu, null);
        parentLinerLayout.addView(v);

        v.setId(numNguyenLieu++);
        Log.d("abc", "onAddView: id view add "+v.getId());
//
//        EditText editText = new EditText(getActivity());
//        editText.setId(editText.generateViewId());

        listEditText.add(v);
        Log.d("abc", "onAddViewList: list size" + listEditText.size());


//        Log.d("abc", "onAddView: " + editText.generateViewId());
        ivDel = v.findViewById(R.id.delete_button);
        ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelete(view);
            }
        });
    }

    public void onDelete(View v) {
        parentLinerLayout.removeView((View) v.getParent());
        int id = ((View) v.getParent()).getId();
        Log.d("abc", "onDelete: id view need delete " + id);
        for(View view: listEditText){
            Log.d("abc", "onDelete: id view in for loop "+view.getId());
            if(view.getId()==id){
                listEditText.remove(id);
                Log.d("abc", "onDeleteView: size list" + id + " " + listEditText.size());
                numNguyenLieu--;
                break;
            }
        }
        for(int i=0; i<listEditText.size(); i++){
            listEditText.get(i).setId(i);
        }
        loadData();
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

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

        }


    }

    public void chooseImage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Image Source");
        builder.setItems(new CharSequence[]{"Gallery", "Camera"},
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                // GET IMAGE FROM THE GALLERY
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");

                                Intent chooser = Intent.createChooser(intent, "Choose a Picture");
                                startActivityForResult(chooser, ACTION_REQUEST_GALLERY);

                                break;

                            case 1:
                                Intent getCameraImage = new Intent("android.media.action.IMAGE_CAPTURE");

                                File cameraFolder;

                                if (android.os.Environment.getExternalStorageState().equals
                                        (android.os.Environment.MEDIA_MOUNTED))
                                    cameraFolder = new File(android.os.Environment.getExternalStorageDirectory(),
                                            "some_directory_to_save_images/");
                                else
                                    cameraFolder = getContext().getCacheDir();
                                if (!cameraFolder.exists())
                                    cameraFolder.mkdirs();


                                startActivityForResult(getCameraImage, ACTION_REQUEST_CAMERA);

                                break;

                            default:
                                break;
                        }
                    }
                });

        builder.show();
    }
}
