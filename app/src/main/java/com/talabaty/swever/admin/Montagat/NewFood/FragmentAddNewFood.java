package com.talabaty.swever.admin.Montagat.NewFood;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Montagat.AddMontag.ImageAdapter;
import com.talabaty.swever.admin.Montagat.AddMontag.ImageSource;
import com.talabaty.swever.admin.Montagat.Additions.FragmentAdditions;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagModel;
import com.talabaty.swever.admin.Montagat.FragmentMontag;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class FragmentAddNewFood extends Fragment {

    Button save, empty, add_image;
    EditText name, price;

    LoginDatabae loginDatabae;
    Cursor cursor;
    View view;

    int shopId;
    FragmentManager fragmentManager;

    private int PICK_IMAGE_REQUEST = 1;
    final int CAMERA_PIC_REQUEST = 1337;

    List<ImageSource> Gallary;
    String baseUrl = "http://www.selltlbaty.rivile.com/";
    private String UPLOAD_URL = baseUrl + "Uploads/UploadAndro";
    private String UPLOAD_LINK = "http://sellsapi.rivile.com/BaseFood/Add";

    private String KEY_IMAGE = "base64imageString";
    private String KEY_NAME = "name";
    //    List<byte[]> bytes;
    // To Get Data
    Basefood userModel = null;
    private static final int CAMERA_REQUEST = 1888;
    Bitmap bitmaP = null;
    List<String> imageStrings;

    ImageView imageView;

    View Camera_view;
    ImageView close, minimize, cam, gal;
    FloatingActionButton appear;
    int close_type;
    String final_image = "";


    ControlMontagModel montagModel;
    public static FragmentAddNewFood setData(ControlMontagModel x) {
        FragmentAddNewFood c = new FragmentAddNewFood();
        c.montagModel = x;
        return c;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_new_food, container, false);

        save = (Button) view.findViewById(R.id.save);
        empty = (Button) view.findViewById(R.id.empty);
        add_image = (Button) view.findViewById(R.id.add_image);
        name = (EditText) view.findViewById(R.id.sanf_name);
        price = (EditText) view.findViewById(R.id.price);
        imageView = view.findViewById(R.id.image);
        appear = view.findViewById(R.id.appear);

        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        fragmentManager = getFragmentManager();

        Gallary = new ArrayList<>();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        requestStoragePermission();
        appear.setVisibility(View.GONE);
        imageStrings = new ArrayList<>();
        ((Home) getActivity())
                .setActionBarTitle("إضافه وجبه");
        if (montagModel != null){
            ((Home) getActivity())
                    .setActionBarTitle("تعديل وجبه");
            name.setText(montagModel.getName());
            price.setText(montagModel.getSellPrice() + "");
            UPLOAD_LINK = "http://sellsapi.rivile.com/BaseFood/Edit";
            save.setText("تعديل");

            if (montagModel.getGallary().size() > 0) {

                    Log.e("Image Link",montagModel.getGallary().get(0).getPhoto());

                    if (final_image.isEmpty()) {
                        Picasso.with(getActivity())
                                .load("http://www.selltlbaty.rivile.com" + montagModel.getGallary().get(0).getPhoto())
                                .into(imageView);
                        final_image = montagModel.getGallary().get(0).getPhoto();
                    }

            }

        }
        while (cursor.moveToNext()) {
            shopId = Integer.parseInt(cursor.getString(3));
        }

        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                price.setText("");
                imageView.setBackgroundResource(R.drawable.addemp);

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

                userModel = new Basefood();
                if (montagModel!=null){
                    userModel.setId(montagModel.getId());
                }
                if (name.getText().toString().isEmpty()){
                    name.setError("ادخل اسم الوجبه");
                }else if (price.getText().toString().isEmpty()){
                    price.setError("اضف سعر الشراء");
                }else {
                    userModel.setName(name.getText().toString());
                    userModel.setPrice(Float.parseFloat(price.getText().toString()));
                    userModel.setShopId(shopId);
                    uploadImage();
                }


            }
        });

    }

    private void uploadMontage() {
        Gson json = new Gson();
        final String jsonInString = json.toJson(userModel);
        Log.e("Data", jsonInString);
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

                //Creating parameters
                Map<String, String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put("Basefood", jsonInString);

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
        if (bitmaP != null) {
            imageStrings.add(getStringImage(bitmaP));
            Log.e("Connection UploadImage", gson.toJson(imageStrings));
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
                                userModel.setPhoto(Gallary.get(0).getPhoto());

//                                final String jsonInString = gson.toJson(userModel);
//                                Log.e("Data", jsonInString);
                                Log.e("Gallary", gson.toJson(Gallary));
                                uploadMontage();
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
        }else {
            userModel.setPhoto(final_image);
            final String jsonInString = gson.toJson(userModel);
            Log.e("Data", jsonInString);
            Log.e("Gallary", gson.toJson(Gallary));
            uploadMontage();
        }
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
                //imageView.setImageDrawable(null);
                bitmaP = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                bitmaP = getResizedBitmap(bitmaP, 100);
                imageView.setImageBitmap(bitmaP);
//                Log.e("Bitmap1",getStringImage(bitmaP));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Log.e("Bitmap2","Exist");
            bitmaP = (Bitmap) data.getExtras().get("data");
            bitmaP = getResizedBitmap(bitmaP, 100);
            imageView.setImageBitmap(bitmaP);
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
