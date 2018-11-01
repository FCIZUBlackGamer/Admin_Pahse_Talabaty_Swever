package com.talabaty.swever.admin.Offers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.fourhcode.forhutils.FUtilsValidation;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
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

public class Fragment_offers extends Fragment {

    View view, offer;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<OperOfferModel> agents, temp_list;

    FloatingActionButton add, save;

    Button offer_add;
    EditText offer_num;
    Spinner offer_product_name;
    TextView offer_total;
    ImageButton close;
    ArrayList<String> offer_product_nameList;
    List<SpinnerModel> productModels;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;
    EditText name, price, desc;


    private int PICK_IMAGE_REQUEST = 1;
    final int CAMERA_PIC_REQUEST = 1337;

    String baseUrl = "http://www.selltlbaty.rivile.com/";
    private String UPLOAD_URL = baseUrl + "Uploads/UploadAndro";
    private String UPLOAD_LINK = "http://onlineapi.rivile.com/Login/AddUser";

    private String KEY_IMAGE = "base64imageString";
    private String KEY_NAME = "name";

    private static final int CAMERA_REQUEST = 1888;
    Bitmap bitmap;
    List<String> imageStrings;

//    DatePickerDialog.OnDateSetListener DatePicker1;

    View Camera_view;
    ImageView display_image;
    
    ImageView close_v, minimize, cam, gal;
    static TotalOffer totalOffer = null;
    
    
//    Button start_date, end_date;
//    DatePickerDialog.OnDateSetListener DatePicker1, DatePicker2;

    public static Fragment_offers setData(TotalOffer totalOffe){
        Fragment_offers offers = new Fragment_offers();
        totalOffer = totalOffe;
        return offers;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_offer, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        add = (FloatingActionButton) view.findViewById(R.id.add);
        save = (FloatingActionButton) view.findViewById(R.id.save);
        name = view.findViewById(R.id.name);
        price = view.findViewById(R.id.price);
        desc = view.findViewById(R.id.desc);
        display_image = view.findViewById(R.id.photo);
//        start_date = view.findViewById(R.id.start_date);
//        end_date = view.findViewById(R.id.end_date);
        recyclerView.setLayoutManager(layoutManager);
        agents = new ArrayList<>();

        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        imageStrings = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestStoragePermission();
        try {
            ((Home) getActivity())
                    .setActionBarTitle("العروض");
        }catch (Exception e){
            ((Mabi3atNavigator) getActivity())
                    .setActionBarTitle("العروض");
        }
        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
        if (totalOffer != null){
            name.setText(totalOffer.getName());
            price.setText(totalOffer.getPrice()+"");
            desc.setText(totalOffer.getDescription());
            if (totalOffer.getPhoto() != null || !totalOffer.getPhoto().isEmpty()) {
                Picasso.with(getActivity()).load(totalOffer.getPhoto()).into(display_image);
            }

            agents = totalOffer.getOfferList();
            adapter = new OfferAdapter(getActivity(), agents);
            recyclerView.setAdapter(adapter);
        }

        display_image.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                Camera_view = inflater.inflate(R.layout.camera_view, null);

                close_v = Camera_view.findViewById(R.id.close);
                minimize = Camera_view.findViewById(R.id.minimize);
                cam = Camera_view.findViewById(R.id.cam);
                gal = Camera_view.findViewById(R.id.gal);
                minimize.setVisibility(View.GONE);
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

                close_v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        dialog.dismiss();

                    }
                });
            }
        });


//        end_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog dialog = new DatePickerDialog(
//                        getContext(),
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
//                        , DatePicker2
//                        , year, month, day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//
//            }
//
//        });
//        DatePicker2 = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                end_date.setText(year + "/" + month + "/" + dayOfMonth);
//            }
//        };
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        String date = df.format(Calendar.getInstance().getTime());
//        start_date.setText(date);
//
//        start_date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog dialog = new DatePickerDialog(
//                        getContext(),
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
//                        , DatePicker1
//                        , year, month, day);
//
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
//
//            }
//
//        });
//        DatePicker1 = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                month = month + 1;
//                start_date.setText(year + "/" + month + "/" + dayOfMonth);
//                Log.e("Date", dayOfMonth + "-" + month + "-" + year);
//            }
//        };

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Upload List */
                if (FUtilsValidation.isEmpty(name, "اضف اسم للعرض") ||
                        FUtilsValidation.isEmpty(price, "اضف سعر للعرض") ||
                        FUtilsValidation.isEmpty(desc, "اضف وصف للعرض")) {

                } else {
                    if (agents.size() > 0) {
                        totalOffer = new TotalOffer();
                        totalOffer.setName(name.getText().toString());
                        totalOffer.setPrice(Float.parseFloat(price.getText().toString()));
                        totalOffer.setDescription(desc.getText().toString());
                        totalOffer.setOfferList(agents);
                        totalOffer.setShopId(shopid);
                        uploadImage();
                    } else {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_error,
                                (ViewGroup) getActivity().findViewById(R.id.lay));

                        TextView text = (TextView) layout.findViewById(R.id.txt);

                        text.setText("اضف منتجات للعرض");

                        Toast toast = new Toast(getActivity());
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                offer = inflater.inflate(R.layout.dialog_add_offer, null);

                offer_product_name = offer.findViewById(R.id.offer_product_name);
                offer_num = offer.findViewById(R.id.offer_num);
                offer_total = offer.findViewById(R.id.offer_total);
                offer_add = offer.findViewById(R.id.offer_add);
                close = offer.findViewById(R.id.close);

                offer_num.setInputType(InputType.TYPE_CLASS_NUMBER);

                loadSpinnerProductName();

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("أضف عرض")
                        .setCancelable(false)
                        .setView(offer)
                        .setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do Nothing
                                clearMessageView();
                                dialog.dismiss();
                            }
                        });
                final AlertDialog dialog2 = builder.create();
                dialog2.show();
                dialog2.getWindow().setLayout(1200, 800);

                closeMessage(dialog2);
                offer_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Action","Add Offer");
                        for (int x = 0; x < temp_list.size(); x++) {
                            if (temp_list.get(x).getName().equals(offer_product_name.getSelectedItem().toString())) {
                                addItem((temp_list.get(x).getSampleProductId()) + "", offer_product_name.getSelectedItem().toString(), Integer.parseInt(offer_num.getText().toString()), Float.parseFloat(offer_total.getText().toString()));
                                dialog2.dismiss();
                                Log.e("Action","Found Offer");

                            }
                        }

                    }
                });
            }
        });

    }

    private void uploadImage() {
        final Gson gson = new Gson();
        Log.e("Connection UploadImage", "Here");

        final String allImages = gson.toJson(imageStrings);
        Log.e("Start: ", allImages);
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
                                totalOffer.setPhoto(object1);
                            }
                            final String jsonInString = gson.toJson(totalOffer);
                            Log.e("Data", jsonInString);
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

                        //Showing toast
//                        Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (volleyError instanceof ServerError)
                            Log.e("Error: ", "Server Error");
                        else if (volleyError instanceof TimeoutError)
                            Log.e("Error: ", "Timeout Error");
                        else if (volleyError instanceof NetworkError)
                            Log.e("Error: ", "Bad Network");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

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
    
    private void closeMessage(final Dialog dialog) {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessageView();
                dialog.dismiss();
            }
        });
    }

    private void clearMessageView() {
        if (offer != null) {
            ViewGroup parent = (ViewGroup) offer.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }

    private void uploadMontage(final String jsonInString) {
        Log.e("Connection UploadMontag", "Here");
        Log.e("Full Model",jsonInString);
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/Offers/Add",
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

                            Intent intent = new Intent(getActivity(), Home.class);
                            intent.putExtra("fragment","offer");
                            startActivity(intent);

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

                        //Showing toast
//                        Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (volleyError instanceof ServerError)
                            Log.e("Error: ", "Server Error");
                        else if (volleyError instanceof TimeoutError)
                            Log.e("Error: ", "Timeout Error");
                        else if (volleyError instanceof NetworkError)
                            Log.e("Error: ", "Bad Network");
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
                params.put("Offer", jsonInString);

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
                display_image.setImageBitmap(bitmap);
                if (display_image.getDrawable() == null){
                    imageStrings.add(getStringImage(bitmap));
                }else {
                    imageStrings.add(0,getStringImage(bitmap));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            display_image.setImageBitmap(bitmap);
            if (display_image.getDrawable() == null){
                imageStrings.add(getStringImage(bitmap));
            }else {
                imageStrings.add(0,getStringImage(bitmap));
            }

        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void addItem(String id, String name, int num, float price) {


        final int sizew = agents.size();
        if (sizew > 0) {
            for (int i = 0; i < sizew; i++) {
                agents.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, sizew);

            OperOfferModel item = new OperOfferModel(
                    Integer.parseInt(id), price, num, name
            );
            agents.add(item);
            Gson gson = new Gson();
            Log.e("Agent",gson.toJson(agents));

            adapter = new OfferAdapter(getActivity(), agents);
            recyclerView.setAdapter(adapter);
        }else {
            OperOfferModel item = new OperOfferModel(
                    Integer.parseInt(id), price, num, name
            );
            agents.add(item);
            Gson gson = new Gson();
            Log.e("Agent",gson.toJson(agents));

            adapter = new OfferAdapter(getActivity(), agents);
            recyclerView.setAdapter(adapter);
        }

    }

    private void loadSpinnerProductName() {

        offer_product_nameList = new ArrayList<>();
        temp_list = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/Offers/SelectProduct?ShopId=" + shopid + "&token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Products");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.getString("Name");
                        int id = jsonObject1.getInt("Id");
                        double price = jsonObject1.getDouble("SellPrice");
                        offer_product_nameList.add(name);
                        temp_list.add(new OperOfferModel(id, price, name));

                    }

                    offer_product_name.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, offer_product_nameList));

                    offer_num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {

                                for (int x = 0; x < temp_list.size(); x++) {
                                    if (temp_list.get(x).getName().equals(offer_product_name.getSelectedItem().toString())) {
                                        offer_total.setText(String.valueOf(Float.parseFloat(offer_num.getText().toString()) * temp_list.get(x).getPrice()));
                                    }
                                }

                                return true;
                            }
                            return false;
                        }
                    });

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
                hashMap.put("ShopId", shopid+"");
                return hashMap;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
//    private void loadData(final int UserId , final int ShopId, final int x, final int type) {
//
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("جارى تحميل البيانات ...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/Order/ReportCustomer",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        try {
//
//                            JSONObject object = new JSONObject(response);
//                            JSONArray array = object.getJSONArray("ReportCustomer");
//                            if (array.length()>0) {
//                                final int size = agents.size();
//                                if (size > 0) {
//                                    for (int i = 0; i < size; i++) {
//                                        agents.remove(0);
//                                    }
//                                    adapter.notifyItemRangeRemoved(0, size);
//                                }
//                                for (int x = 0; x < array.length(); x++) {
//                                    JSONObject object1 = array.getJSONObject(x);
//                                    Offer item = new Offer(
//                                            object1.getInt("CustomerId") + "",
//                                            object1.getString("CustomerName") + "",
//                                            object1.getInt("Count") + ""
//                                    );
//                                    agents.add(item);
//                                }
//                            }else {
//                                Toast toast = Toast.makeText(getActivity(), "لا توجد بيانات", Toast.LENGTH_SHORT);
//                                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//                                v.setTextColor(Color.GREEN);
//                                toast.show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        adapter = new OfferRestaurantAdapter(getActivity(),agents);
//                        recyclerView.setAdapter(adapter);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                if (error instanceof ServerError)
//                    Toast.makeText(getActivity(), "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
//                else if (error instanceof NetworkError)
//                    Toast.makeText(getActivity(), "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
//                else if (error instanceof TimeoutError)
//                    Toast.makeText(getActivity(), "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                HashMap hashMap = new HashMap();
//                hashMap.put("UserId", ""+UserId);
//                hashMap.put("ShopId", ""+ShopId);
//                hashMap.put("x", x+"");
//                hashMap.put("type", type+"");
//                hashMap.put("token", "bKPNOJrob8x");
//                return hashMap;
//            }
//        };
////        Volley.newRequestQueue(getActivity()).add(stringRequest);
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
//                2,  // maxNumRetries = 2 means no retry
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
//    }
}
