package com.talabaty.swever.admin.Montagat.AddReturanteMontage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagModel;
import com.talabaty.swever.admin.Montagat.FragmentMontag;
import com.talabaty.swever.admin.Montagat.SpinnerModel;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddReturanteMontage extends Fragment {

    // For Image
    RecyclerView image_rec , old_image_rec;
    RecyclerView.Adapter image_adap, old_image_adap;
    List<Bitmap> imageSources;
    List<Bitmap> imageTemp;

    // For Size
    RecyclerView size_rec;
    RecyclerView.Adapter size_adap;
    List<Size> sizeDimention;
    List<Size> sizeTemp;


    //For Uploading To Server
    Sanf sanf;
    List<Size> sizeStrings;
    List<String> imageStrings;
    List<Uri> imageUri;
    List<Uri> tempimageUri;


    //Views
    Button back, choose_size, add_image;

    FragmentManager fragmentManager;
    Button save, empty;
    // First CardView
    EditText sanf_name, additions;
    // Second CardView
    EditText buy_price;
    // Third CardView
    Spinner department;
    List<String> DepatmentList;
    List<SpinnerModel> depspin;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    List<ImageSource> Gallary;
    String baseUrl = "http://www.selltlbaty.rivile.com/";
    private String UPLOAD_URL = baseUrl + "Uploads/UploadAndro";
    private String UPLOAD_LINK = "http://sellsapi.rivile.com/sampleproduct2/Add";

    private String KEY_IMAGE = "base64imageString";
    private String KEY_NAME = "name";

    // To Get Data
    ControlMontagModel montagModel = null;
    private static final int CAMERA_REQUEST = 1888;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;

    List<SizeModel> sizeModels;

    View Camera_view;
    ImageView close, minimize, cam, gal;
    FloatingActionButton appear;
    int close_type;
    boolean reload = false;
//    ImageView imageView2;

    public static AddReturanteMontage setData(ControlMontagModel x) {
        AddReturanteMontage c = new AddReturanteMontage();
        c.montagModel = x;
        return c;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_montag_resturante, container, false);
        // Normal Views (Edittext, Spinner, Buttons)
        Gson gson = new Gson();
        Log.e("MontageModel", gson.toJson(montagModel));
        sanf_name = view.findViewById(R.id.sanf_name);
        additions = view.findViewById(R.id.additions);
        department = view.findViewById(R.id.department);
        appear = view.findViewById(R.id.appear);
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();

        image_rec = (RecyclerView) view.findViewById(R.id.image_rec);
        image_rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        old_image_rec = (RecyclerView) view.findViewById(R.id.old_image_rec);
        old_image_rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        imageSources = new ArrayList<>();
        imageTemp = new ArrayList<>();

        size_rec = (RecyclerView) view.findViewById(R.id.size_rec);
        size_rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        sizeDimention = new ArrayList<>();
        sizeTemp = new ArrayList<>();

        choose_size = view.findViewById(R.id.size);
        add_image = view.findViewById(R.id.add_image);


        back = view.findViewById(R.id.back);
        save = view.findViewById(R.id.save);
        empty = view.findViewById(R.id.empty);


        sizeStrings = new ArrayList<>();
        imageStrings = new ArrayList<>();
        imageUri = new ArrayList<>();
        tempimageUri = new ArrayList<>();
//        bytes = new ArrayList<>();
        Gallary = new ArrayList<>();
        sizeModels = new ArrayList<>();
        ((Home) getActivity())
                .setActionBarTitle("إضافه وجبه");
//        imageView2 = view.findViewById(R.id.img);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fragmentManager = getFragmentManager();

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));
        }

        appear.setVisibility(View.GONE);
        requestStoragePermission();
        DepatmentList = new ArrayList<>();
        loadDepartment();
        if (montagModel != null) {
            ((Home) getActivity())
                    .setActionBarTitle("تعديل وجبه");
            sanf_name.setText(montagModel.getName());
            UPLOAD_LINK = "http://sellsapi.rivile.com/sampleproduct2/EditProducts";
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
            if (montagModel.getSize().size() > 0) {
                sizeDimention = new ArrayList<>();
                for (int x = 0; x < montagModel.getSize().size(); x++) {
                    Size size = montagModel.getSize().get(x);
                    sizeDimention.add(size);
                }
                size_adap = new SizeAdapter(getActivity(), sizeDimention);
                size_rec.setAdapter(size_adap);
            }

            if (reload) {
                if (montagModel.getGallary().size() > 0) {
                    Log.e("Gallary Size", montagModel.getGallary().size() + "");
//                Log.e("Gallary Item", montagModel.getGallary().get(0).getPhoto());
                    imageSources = new ArrayList<>();
                    imageUri = new ArrayList<>();


                    for (int x = 0; x < montagModel.getGallary().size(); x++) {
                        Gallary.add(new ImageSource(montagModel.getGallary().get(x).getPhoto()));
                    }

                    old_image_adap = new OldImageAdapter(getActivity(), Gallary);
                    old_image_rec.setAdapter(old_image_adap);

                    //img_ed_index = Gallary.size();

//                imageView2.setVisibility(View.VISIBLE);
//                delete_image.setVisibility(View.VISIBLE);

//                        try{
//                            Thread.sleep(5000);
//                            bitmap = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
//                            imageSources.add(bitmap);
//                            imageStrings.add(getStringImage(bitmap));
//                            image_adap.notifyDataSetChanged();
//                        }catch (Exception e){
//
//                        }

//                delete_image.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.e("GALLERY SIZE", Gallary.size() + "");
//                        Gallary.remove(img_ed_index - 1);
////                            for (Object a : Gallary) {
////                                if (a == new ImageSource(montagModel.getGallary().get(0).getPhoto())) {
////                                    Gallary.remove(a);
////                                }
////                            }
////                            for (ImageSource a : new ArrayList<>(Gallary)) {
////                                if (a == new ImageSource(montagModel.getGallary().get(0).getPhoto())) {
////                                    Gallary.remove(a);
////                                }
////                            }
//
////                            for (Iterator<ImageSource> iter = Gallary.listIterator(); iter.hasNext(); ) {
////                                ImageSource a = iter.next();
////                                if (a == new ImageSource(montagModel.getGallary().get(0).getPhoto())) {
////                                    Gallary.remove(a);
////                                }
////                            }
//
////                            Gallary.remove(new ImageSource(montagModel.getGallary().get(0).getPhoto()));
//                        Log.e("GALLERY SIZE", Gallary.size() + "");
//                        imageView2.setVisibility(View.GONE);
//                        delete_image.setVisibility(View.GONE);
//                    }
//                });

//                    image_adap = new ImageAdapter(getActivity(), imageSources, imageUri);
//                    image_rec.setAdapter(image_adap);

                }
            }

            //Todo: Forgot To Get Data For Spinner From WebService As so Create it's Own Value into Model
            // Here To Set Item To Spinner
        }

        image_adap = new ImageAdapter(getActivity(), imageSources, imageUri);
        image_rec.setAdapter(image_adap);

        sanf = new Sanf();

        choose_size.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                final LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                // Price
                final EditText price = new EditText(getActivity());
                price.setHint("اضف سعر المنتج");
                // Size
                final EditText sizeData = new EditText(getActivity());
                sizeData.setHint("اضف حجم المنتج");
//                loadSizes();

                linearLayout.addView(price);
                linearLayout.addView(sizeData);
                // Text Type
                price.setInputType(InputType.TYPE_CLASS_NUMBER);
                price.setTextColor(R.color.colorPrimaryDark);


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("المقاس")
                        .setView(linearLayout)
                        .setCancelable(false)
                        .setPositiveButton(" تم ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (price.getText().toString().isEmpty() || sizeData.getText().toString().isEmpty()) {
                                    Toast.makeText(getActivity(), "من فضلك قم بإدخال سعر للمنتج", Toast.LENGTH_SHORT).show();
                                } else {

                                    displaySize(sizeData.getText().toString(), price.getText().toString());

                                }
                                if (linearLayout != null) {
                                    ViewGroup parent = (ViewGroup) linearLayout.getParent();
                                    if (parent != null) {
                                        parent.removeAllViews();
                                    }
                                }
                            }
                        })
                        .setNegativeButton(" إلغاء ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do Nothing
                                if (linearLayout != null) {
                                    ViewGroup parent = (ViewGroup) linearLayout.getParent();
                                    if (parent != null) {
                                        parent.removeAllViews();
                                    }
                                }
                            }
                        }).show();
            }
        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if (montagModel == null) {
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
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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
                            close_type = 0;
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
                            close_type = 1;
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
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
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
                if (montagModel == null) {
                    sanf.setId(0);
                    sanf.setShop_Id(shopid);
                    sanf.setUserId(userid);
                    sanf.setEditUserId(userid);
                } else {
                    sanf.setId(montagModel.getId());
                    sanf.setShop_Id(montagModel.getShop_Id());
                    sanf.setUserId(montagModel.getUserId());
                    sanf.setEditUserId(userid);
                    sanf.setSampleCatogoriesId(montagModel.getSampleCatogoriesId());
                }
                if (sanf_name.getText().toString().isEmpty()){
                    sanf_name.setError("ادخل اسم المنتج");
                }else if (sizeDimention.size() < 1){
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_error,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);
                    text.setText("برجاء ادخال وحدات احجام");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }else {
                    sanf.setName(sanf_name.getText().toString());
//                sanf.setBuyPrice(Integer.parseInt(buy_price.getText().toString()));

                    for (int x = 0; x < depspin.size(); x++) {
                        if (depspin.get(x).getName().equals(department.getSelectedItem().toString())){
                            sanf.setSampleCatogoriesId(depspin.get(x).getId());
                        }
                    }
                    sanf.setDescription(additions.getText().toString());
//                sanf.setInsertDate("3");
                    sanf.setSize(sizeDimention);
                    uploadImage();
                }

            }
        });

        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sanf_name.setText("");
                buy_price.setText("");

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

                // Empty Sizes
                final int sizeSizes = sizeDimention.size();
                if (sizeSizes > 0) {
                    for (int i = 0; i < sizeSizes; i++) {
                        sizeDimention.remove(0);
                    }
                    size_adap.notifyItemRangeRemoved(0, sizeSizes);
                }
                size_adap = new SizeAdapter(getActivity(), sizeDimention);
                size_rec.setAdapter(size_adap);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentMontag()).addToBackStack("FragmentMontag").addToBackStack("FragmentMontag").commit();
            }
        });
    }

//    private void loadSizes() {
//
//        sizeModels = new ArrayList<>();
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.sellsapi.rivile.com/SampleProduct/SelectSampleCatogories", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    //Todo: Load Size And Modify Keys
//                    JSONArray jsonArray = jsonObject.getJSONArray("SampleCatogory");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        String fname = jsonObject1.getString("Name");
//                        String id = jsonObject1.getString("Id");
//                        sizeModels.add(new SizeModel(id, fname));
//
//                    }
//
//                    department.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, DepatmentList));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (error instanceof ServerError)
//                    Toast.makeText(getActivity(), "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
//                else if (error instanceof NetworkError)
//                    Toast.makeText(getActivity(), "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
//                else if (error instanceof TimeoutError)
//                    Toast.makeText(getActivity(), "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap hashMap = new HashMap();
//                hashMap.put("token", "bKPNOJrob8x");
//                return hashMap;
//            }
//        };
//        int socketTimeout = 30000;
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(policy);
//        requestQueue.add(stringRequest);
//    }

    private void uploadMontage(final String jsonInString) {
        Log.e("Connection UploadMontag", jsonInString);
//        Log.e("Id",);
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_LINK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openGalary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
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

        depspin = new ArrayList<>();
        DepatmentList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.sellsapi.rivile.com/SampleProduct2/SelectSampleCatogories?ShopId="+shopid+"&token=bKPNOJrob8x", new Response.Listener<String>() {
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
                        depspin.add(new SpinnerModel(id, fname));
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
        }) {
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

    void displaySize(String sizetype, String price) {
        final int size = sizeDimention.size();
//        ContactItem []x = new ContactItem[size];
        if (size > 0) {
//            temp.add(colorCodes);
            for (int i = 0; i < size; i++) {
                sizeTemp.add(sizeDimention.get(0));
                sizeDimention.remove(0);
            }

            sizeDimention = sizeTemp;
            sizeDimention.add(new Size(sizetype, price));
            sizeStrings.add(new Size(1, sizetype, 6));

            size_adap.notifyItemRangeRemoved(0, size);
        } else {
            sizeDimention.add(new Size(sizetype, price));
        }
//        Toast.makeText(getActivity(),String.format("Current color: 0x%08x", color),Toast.LENGTH_SHORT).show();

        size_adap = new SizeAdapter(getActivity(), sizeDimention);
        size_rec.setAdapter(size_adap);
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
            filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                bitmap = getResizedBitmap(bitmap, 100);
                displayImage(bitmap, filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = getResizedBitmap(bitmap, 100);
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

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }


}
