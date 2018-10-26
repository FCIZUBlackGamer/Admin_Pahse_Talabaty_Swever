package com.talabaty.swever.admin.Montagat.Market;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagModel;
import com.talabaty.swever.admin.Montagat.FragmentMontag;
import com.talabaty.swever.admin.Montagat.SpinnerModel;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddMarketMontage extends Fragment {

    // For Image
    RecyclerView image_rec;
    RecyclerView.Adapter image_adap;
    List<Bitmap> imageSources;
    List<Bitmap> imageTemp;


    //For Uploading To Server
    Sanf sanf;
    List<String> imageStrings;
    List<Uri> imageUri;
    List<Uri> tempimageUri;

    //Views
    Button back, add_image;

    FragmentManager fragmentManager;
    Button save, empty;
    // First CardView
    EditText sanf_name, buy_price;
    // Third CardView
    EditText buyex_price, sale_price, additions;
    Spinner department, package_id, unit_id; //Todo: Need API To Get package_id, unit_id
    List<String> DepatmentList, unit_idList;
    List<SpinnerModel> depmodel, unit_model;
    int finaldep, finalunit ;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    List<ImageSource> Gallary;
    String baseUrl = "http://www.selltlbaty.rivile.com/";
    private String UPLOAD_URL = baseUrl + "Uploads/UploadAndro";
    private String UPLOAD_LINK = "http://sellsapi.rivile.com/sampleproduct1/Add";

    private String KEY_IMAGE = "base64imageString";
    private String KEY_NAME = "name";

    // To Get Data
    ControlMontagModel montagModel = null;
    private static final int CAMERA_REQUEST = 1888;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;

    View Camera_view;
    ImageView close, minimize, cam, gal;
    FloatingActionButton appear;
    int close_type;

    public static AddMarketMontage setData(ControlMontagModel x) {
        AddMarketMontage c = new AddMarketMontage();
        c.montagModel = x;
        return c;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_market_montage, container, false);
        // Normal Views (Edittext, Spinner, Buttons)
        Gson gson = new Gson();
        Log.e("MontageModel", gson.toJson(montagModel));
        sanf_name = view.findViewById(R.id.sanf_name);
        additions = view.findViewById(R.id.additions);
        buyex_price = view.findViewById(R.id.buyex_price);
        department = view.findViewById(R.id.department);
        package_id = view.findViewById(R.id.package_id);
        unit_id = view.findViewById(R.id.unit_id);
        sale_price = view.findViewById(R.id.sale_price);
        buy_price = view.findViewById(R.id.buy_price);
        appear = view.findViewById(R.id.appear);
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();

        image_rec = (RecyclerView) view.findViewById(R.id.image_rec);
        image_rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        imageSources = new ArrayList<>();
        imageTemp = new ArrayList<>();

        add_image = view.findViewById(R.id.add_image);


        back = view.findViewById(R.id.back);
        save = view.findViewById(R.id.save);
        empty = view.findViewById(R.id.empty);


        imageStrings = new ArrayList<>();
        imageUri = new ArrayList<>();
        tempimageUri = new ArrayList<>();
//        bytes = new ArrayList<>();
        Gallary = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentManager = getFragmentManager();
        depmodel = unit_model = new ArrayList<>();

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
        appear.setVisibility(View.GONE);
        requestStoragePermission();
        DepatmentList = new ArrayList<>();
        unit_idList = new ArrayList<>();
        loadDepartment();
        loadUint();
        if (montagModel != null) {
            sanf_name.setText(montagModel.getName());;
            buyex_price.setText(montagModel.getBuyPrice() + "");
            UPLOAD_LINK = "http://sellsapi.rivile.com/sampleproduct1/EditProducts";
            save.setText("تعديل");
            if (!TextUtils.isEmpty(montagModel.getDescription())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    additions.setText(Html.fromHtml(montagModel.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    additions.setText(Html.fromHtml(montagModel.getDescription()));
                }
            }
//            additions.setText(montagModel.getDescription());
            additions.setEnabled(false);

            if (montagModel.getGallary().size() > 0) {
                //Todo: Download Image With Picaso (Finished But Still Test Today 30/7/2018)
                imageSources = new ArrayList<>();
                imageUri = null;
                for (int x=0; x<montagModel.getGallary().size(); x++){
                    Picasso.with(getActivity())
                            .load(montagModel.getGallary().get(x).getPhoto())
                            .into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    imageSources.add(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });
                }
                image_adap = new ImageAdapter(getActivity(), imageSources, imageUri);
                image_rec.setAdapter(image_adap);

            }

            //Todo: Forgot To Get Data For Spinner From WebService As so Create it's Own Value into Model
            // Here To Set Item To Spinner
        }

        sanf = new Sanf();

        package_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (package_id.getSelectedItem().toString().equals("نسبة")){
                    sanf.setSaleType(false);
                }else {
                    sanf.setSaleType(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int x=0; x<depmodel.size(); x++){
                    if (department.getSelectedItem().toString().equals(depmodel.get(x).getName())){
                        finaldep = depmodel.get(x).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        unit_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int x=0; x<unit_model.size(); x++){
                    if (unit_id.getSelectedItem().toString().equals(unit_model.get(x).getName())){
                        finalunit = unit_model.get(x).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                Camera_view = inflater.inflate(R.layout.camera_view, null);

                close = Camera_view.findViewById(R.id.close);
                minimize = Camera_view.findViewById(R.id.minimize);
                cam = Camera_view.findViewById(R.id.cam);
                gal = Camera_view.findViewById(R.id.gal);

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false)
                        .setView(Camera_view);

                final AlertDialog dialog = builder.create();
                dialog.show();

                gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGalary();
                        dialog.dismiss();
                    }
                });

                cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        close_type =0;
                        dialog.dismiss();
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                appear.setVisibility(View.GONE);
                            }
                        });

                    }
                });

                minimize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        close_type =1;
                        dialog.dismiss();
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                appear.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                });
            }
        });


        appear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                Camera_view = inflater.inflate(R.layout.camera_view, null);

                close = Camera_view.findViewById(R.id.close);
                minimize = Camera_view.findViewById(R.id.minimize);
                cam = Camera_view.findViewById(R.id.cam);
                gal = Camera_view.findViewById(R.id.gal);

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false)
                        .setView(Camera_view);

                final AlertDialog dialog = builder.create();
                dialog.show();

                gal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGalary();
                        dialog.dismiss();
                    }
                });

                cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                        dialog.dismiss();
                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        close_type =0;
                        dialog.dismiss();
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                appear.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                minimize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        close_type =1;
                        dialog.dismiss();
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                appear.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Validate Inputs
                if (montagModel == null) {
                    sanf.setId(0);
                    sanf.setShop_Id(shopid);
                    sanf.setUserId(userid);
                    sanf.setEditUserId(userid);
                    sanf.setSampleCatogoriesId(3);
                } else {
                    sanf.setId(montagModel.getId());
                    sanf.setShop_Id(montagModel.getShop_Id());
                    sanf.setUserId(montagModel.getUserId());
                    //Todo: User Id From Sqlite
                    sanf.setEditUserId(userid);
                    sanf.setSampleCatogoriesId(montagModel.getSampleCatogoriesId());
                }
                for (int x=0; x<unit_model.size(); x++){
                    if (unit_id.getSelectedItem().toString().equals(unit_model.get(x).getName())){
                        finalunit = unit_model.get(x).getId();
                    }
                }
                if (sanf_name.getText().toString().isEmpty()){
                    sanf_name.setError("ادخل اسم المنتج");
                }else if (buy_price.getText().toString().isEmpty()){
                    buy_price.setError("اضف سعر الشراء");
                }else if (buyex_price.getText().toString().isEmpty()){
                    buyex_price.setError("اضف عر للبيع");
                }else if (finalunit < 1){
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_error,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);
                    text.setText("برجاء ادخال وحدات ");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }else {
                    sanf.setName(sanf_name.getText().toString());
                    sanf.setDescription(additions.getText().toString());
                    sanf.setBuyPrice(Float.parseFloat(buy_price.getText().toString()));
                    sanf.setSellPrice(Float.parseFloat(buyex_price.getText().toString()));
                    sanf.setSale(Float.parseFloat(sale_price.getText().toString()));
                    sanf.setUnitsId(finalunit);
                    sanf.setSampleCatogoriesId(finaldep);

//                sanf.setInsertDate("3");
                    uploadImage();
                }

            }
        });

        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sanf_name.setText("");
                buyex_price.setText("");

                // Empty Images
                final int sizeImages = imageSources.size();
                if (sizeImages > 0) {
                    for (int i = 0; i < sizeImages; i++) {
                        imageSources.remove(0);
                        imageUri.remove(0);
                    }
                    image_adap.notifyItemRangeRemoved(0, sizeImages);
                }
                image_adap = new ImageAdapter(getActivity(), imageSources, imageUri);
                image_rec.setAdapter(image_adap);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentMontag()).addToBackStack("FragmentMontag").addToBackStack("FragmentMontag").commit();
            }
        });
    }

    private void uploadMontage(final String jsonInString) {
        Log.e("Connection UploadMontag", "Here");
//        Log.e("Id",);
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Data: ", s);
                        Log.e("Data: ", s);
                        if (s.equals("\"Success\"")) {

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_info,
                                    (ViewGroup) getActivity().findViewById(R.id.lay));

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("تمت تنفيذ العملية");

                            Toast toast = new Toast(getActivity());
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentMontag()).commit();

                        } else {

                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_error,
                                    (ViewGroup) getActivity().findViewById(R.id.lay));

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("حدث خطأ اثناء اجراء العمليه");

                            Toast toast = new Toast(getActivity());
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_warning,
                                (ViewGroup) getActivity().findViewById(R.id.lay));

                        TextView text = (TextView) layout.findViewById(R.id.txt);

                        if (volleyError instanceof ServerError)
                            text.setText("خطأ فى الاتصال بالخادم");
                        else if (volleyError instanceof TimeoutError)
                            text.setText("خطأ فى مدة الاتصال");
                        else if (volleyError instanceof NetworkError)
                            text.setText("شبكه الانترنت ضعيفه حاليا");

                        Toast toast = new Toast(getActivity());
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
//                for (int x= 0; x<imageSources.size(); x++) {
//                    String image = getStringImage(bitmap);
//                }

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("Product", jsonInString);

                params.put("token", "bKPNOJrob8x");

                //returning parameters
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void openGalary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void openCamera() {
        //Todo: Open Camera

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
    }

    private void uploadImage() {
        final Gson gson = new Gson();
        Log.e("Connection UploadImage", "Here");
        final String allImages = gson.toJson(imageStrings);
//        Log.e("Start: ", allImages);
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Path: ", s);
                        try {

                            JSONObject object = new JSONObject(s);
                            JSONArray array = object.getJSONArray("Images");
                            for (int x = 0; x < array.length(); x++) {
                                String object1 = array.getString(x);
                                Gallary.add(new ImageSource(object1));
                            }
                            sanf.setGallary(Gallary);

                            final String jsonInString = gson.toJson(sanf);
                            Log.e("Data", jsonInString);
                            Log.e("Gallary", gson.toJson(Gallary));
                            uploadMontage(jsonInString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_warning,
                                (ViewGroup) getActivity().findViewById(R.id.lay));

                        TextView text = (TextView) layout.findViewById(R.id.txt);

                        if (volleyError instanceof ServerError)
                            text.setText("خطأ فى الاتصال بالخادم");
                        else if (volleyError instanceof TimeoutError)
                            text.setText("خطأ فى مدة الاتصال");
                        else if (volleyError instanceof NetworkError)
                            text.setText("شبكه الانترنت ضعيفه حاليا");

                        Toast toast = new Toast(getActivity());
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
//                for (int x= 0; x<imageSources.size(); x++) {
//                    String image = getStringImage(bitmap);
//                }

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, allImages);

                params.put(KEY_NAME, "Mohamed");

                //returning parameters
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }


    private void loadDepartment() {

        DepatmentList = new ArrayList<>();
        depmodel = new ArrayList<>();

        if (DepatmentList.size() > 0) {
            for (int x = 0; x < DepatmentList.size(); x++) {
                DepatmentList.remove(x);
                depmodel.remove(x);
            }
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/SampleProduct1/SelectSampleCatogories?token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("SampleCatogory");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        int id = jsonObject1.getInt("Id");
                        DepatmentList.add(fname);
                        depmodel.add(new SpinnerModel(id,fname));

                    }

                    department.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, DepatmentList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) getActivity().findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                if (error instanceof ServerError)
                    text.setText("خطأ فى الاتصال بالخادم");
                else if (error instanceof TimeoutError)
                    text.setText("خطأ فى مدة الاتصال");
                else if (error instanceof NetworkError)
                    text.setText("شبكه الانترنت ضعيفه حاليا");

                Toast toast = new Toast(getActivity());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadUint() {

        unit_idList = new ArrayList<>();
        unit_model = new ArrayList<>();

        if (unit_idList.size() > 0) {
            for (int x = 0; x < unit_idList.size(); x++) {
                unit_idList.remove(x);
                unit_model.remove(x);
            }
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, " http://sellsapi.rivile.com/SampleProduct1/SelectUnits?token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Units");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        int id = jsonObject1.getInt("Id");
                        unit_idList.add(fname);
                        unit_model.add(new SpinnerModel(id,fname));

                    }

                    unit_id.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, unit_idList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_warning,
                        (ViewGroup) getActivity().findViewById(R.id.lay));

                TextView text = (TextView) layout.findViewById(R.id.txt);

                if (error instanceof ServerError)
                    text.setText("خطأ فى الاتصال بالخادم");
                else if (error instanceof TimeoutError)
                    text.setText("خطأ فى مدة الاتصال");
                else if (error instanceof NetworkError)
                    text.setText("شبكه الانترنت ضعيفه حاليا");

                Toast toast = new Toast(getActivity());
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    void displayImage(Bitmap path, Uri uri) {
        final int size = imageSources.size();
//        ContactItem []x = new ContactItem[size];
        if (size > 0) {
//            temp.add(colorCodes);
            for (int i = 0; i < size; i++) {
                imageTemp.add(imageSources.get(0));
                imageSources.remove(0);
                tempimageUri.add(imageUri.get(0));
                imageUri.remove(0);
            }

            imageSources = imageTemp;
            imageUri = tempimageUri;
            imageSources.add(path);
            imageUri.add(uri);
//            Log.e("Path",path);
            imageStrings.add(getStringImage(path));

            image_adap.notifyItemRangeRemoved(0, size);
        } else {
            imageSources.add(path);
            imageStrings.add(getStringImage(path));
            imageUri.add(uri);
        }
//        Toast.makeText(getActivity(),String.format("Current color: 0x%08x", color),Toast.LENGTH_SHORT).show();

        image_adap = new ImageAdapter(getActivity(), imageSources, imageUri);
        image_rec.setAdapter(image_adap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }//end onRequestPermissionsResult

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri filePath;
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath= data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                displayImage(bitmap, filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            filePath = null;
            displayImage(bitmap, filePath);
//            imageView.setImageBitmap(photo);
        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


}
