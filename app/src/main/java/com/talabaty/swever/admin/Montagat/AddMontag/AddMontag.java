package com.talabaty.swever.admin.Montagat.AddMontag;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.talabaty.swever.admin.Montagat.FragmentMontag;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.app.Activity.RESULT_OK;

public class AddMontag extends Fragment {
    // For Color
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ColorCode> colorCodes;
    List<ColorCode> temp;

    // For Image
    RecyclerView image_rec;
    RecyclerView.Adapter image_adap;
    List<ImageSource> imageSources;
    List<ImageSource> imageTemp;

    // For Size
    RecyclerView size_rec;
    RecyclerView.Adapter size_adap;
    List<Size> sizeDimention;
    List<Size> sizeTemp;


    //For Uploading To Server
    Sanf sanf;
    List<ColorCode> colorStrings;
    List<Size> sizeStrings;
    List<String> imageStrings;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Uri to store the image uri
    private Uri filePath;


    //Views
    Button choose_color, back, choose_size, add_image;
    static int color = 0xffffff00;
    FragmentManager fragmentManager;
    Button save, empty;
    // First CardView
    EditText sanf_name, initialamount, desc;
    // Second CardView
    EditText buy_price, critical_amount, summary;
    // Third CardView
    EditText buyex_price, notes;
    Spinner department;
    List<String> DepatmentList, indexOfDepatmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_montag, container, false);
        // Normal Views (Edittext, Spinner, Buttons)
        sanf_name = view.findViewById(R.id.sanf_name);
        initialamount = view.findViewById(R.id.initialamount);
        desc = view.findViewById(R.id.desc);
        buy_price = view.findViewById(R.id.buy_price);
        critical_amount = view.findViewById(R.id.critical_amount);
        summary = view.findViewById(R.id.summary);
        buyex_price = view.findViewById(R.id.buyex_price);
        notes = view.findViewById(R.id.notes);
        department = view.findViewById(R.id.department);


        // RecycleViews
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.color_rec);
        recyclerView.setLayoutManager(layoutManager);
        colorCodes = new ArrayList<>();
        temp = new ArrayList<>();

        image_rec = (RecyclerView) view.findViewById(R.id.image_rec);
        image_rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        imageSources = new ArrayList<>();
        imageTemp = new ArrayList<>();

        size_rec = (RecyclerView) view.findViewById(R.id.size_rec);
        size_rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        sizeDimention = new ArrayList<>();
        sizeTemp = new ArrayList<>();

        choose_color = view.findViewById(R.id.choose_color);
        choose_size = view.findViewById(R.id.size);
        add_image = view.findViewById(R.id.add_image);


        back = view.findViewById(R.id.back);
        save = view.findViewById(R.id.save);
        empty = view.findViewById(R.id.empty);


        colorStrings = new ArrayList<>();
        sizeStrings = new ArrayList<>();
        imageStrings = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragmentManager = getFragmentManager();
        temp = colorCodes;

        requestStoragePermission();

        DepatmentList = new ArrayList<>();
        indexOfDepatmentList = new ArrayList<>();

        loadDepartment();
        sanf = new Sanf();
        choose_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(false);
            }
        });

        choose_size.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                final EditText size = new EditText(getActivity());
                size.setHint("اضف مقاس المنتج");
                // Text Type
//                size.setInputType(InputType.TYPE_CLASS_NUMBER);
                size.setTextColor(R.color.colorPrimaryDark);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("المقاس")
                        .setView(size)
                        .setCancelable(false)
                        .setPositiveButton(" تم ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (size.getText().toString().isEmpty()) {
                                    Toast.makeText(getActivity(), "من فضلك قم بإدخال مقاس مناسب", Toast.LENGTH_SHORT).show();
                                } else {
                                    displaySize(size.getText().toString());
                                }
                                if (size != null) {
                                    ViewGroup parent = (ViewGroup) size.getParent();
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
                                if (size != null) {
                                    ViewGroup parent = (ViewGroup) size.getParent();
                                    if (parent != null) {
                                        parent.removeAllViews();
                                    }
                                }
                            }
                        }).show();
            }
        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                sanf.setName(sanf_name.getText().toString());
                sanf.setAmount(initialamount.getText().toString());
                sanf.setBuyPrice(buy_price.getText().toString());
                sanf.setCriticalQuantity(critical_amount.getText().toString());
                sanf.setDescription(desc.getText().toString());
//                sanf.setEditDate("fbfb");
                sanf.setEditUserId("3");
                sanf.setId(0);
                sanf.setUserId("3");
                sanf.setSummary(summary.getText().toString());
                sanf.setShop_Id("5");
                sanf.setSampleCatogoriesId("3");
                sanf.setNotes(notes.getText().toString());
                sanf.setInsertDate("3");
                sanf.setSellPrice(buyex_price.getText().toString());
                sanf.setColorCodes(colorStrings);
                sanf.setSizeList(sizeStrings);
                final String jsonInString = gson.toJson(sanf);
                Log.e("Data", jsonInString);
            }
        });

        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sanf_name.setText("");
                initialamount.setText("");
                desc.setText("");
                buy_price.setText("");
                critical_amount.setText("");
                summary.setText("");
                buyex_price.setText("");
                notes.setText("");

                // Empty Colors
                final int size = colorCodes.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        colorCodes.remove(0);
                    }
                    adapter.notifyItemRangeRemoved(0, size);
                }
                adapter = new ColorAdapter(getActivity(), colorCodes);
                recyclerView.setAdapter(adapter);

                // Empty Images
                final int sizeImages = imageSources.size();
                if (sizeImages > 0) {
                    for (int i = 0; i < sizeImages; i++) {
                        imageSources.remove(0);
                    }
                    image_adap.notifyItemRangeRemoved(0, sizeImages);
                }
                image_adap = new ImageAdapter(getActivity(), imageSources);
                image_rec.setAdapter(image_adap);

                // Empty Sizes
                final int sizeSizes = sizeDimention.size();
                if (sizeSizes > 0) {
                    for (int i = 0; i < sizeSizes; i++) {
                        sizeDimention.remove(0);
                    }
                    size_adap.notifyItemRangeRemoved(0, size);
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


    private void loadDepartment() {

        DepatmentList = new ArrayList<>();
        indexOfDepatmentList = new ArrayList<>();

        if (DepatmentList.size()>0){
            for (int x=0; x<DepatmentList.size(); x++){
                DepatmentList.remove(x);
                indexOfDepatmentList.remove(x);
            }
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.sellsapi.sweverteam.com/SampleProduct/SelectSampleCatogories", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("SampleCatogory");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        String id = jsonObject1.getString("Id");
                        DepatmentList.add(fname);
                        indexOfDepatmentList.add(id);

                    }

                    department.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, DepatmentList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError)
                    Toast.makeText(getActivity(), "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(getActivity(), "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(getActivity(), "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    void openDialog(boolean supportsAlpha) {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), color, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
                AddMontag.color = color;
                //Todo: Check List To Upload
                colorStrings.add(new ColorCode(0,String.format("0x%08x", color),3,5));
                displayColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    void displaySize(String sizetype) {
        final int size = sizeDimention.size();
//        ContactItem []x = new ContactItem[size];
        if (size > 0) {
//            temp.add(colorCodes);
            for (int i = 0; i < size; i++) {
                sizeTemp.add(sizeDimention.get(0));
                sizeDimention.remove(0);
            }

            sizeDimention = sizeTemp;
            sizeDimention.add(new Size(sizetype));
            sizeStrings.add(new Size(sizetype,"1","6"));

            size_adap.notifyItemRangeRemoved(0, size);
        } else {
            sizeDimention.add(new Size(sizetype));
        }
//        Toast.makeText(getActivity(),String.format("Current color: 0x%08x", color),Toast.LENGTH_SHORT).show();

        size_adap = new SizeAdapter(getActivity(), sizeDimention);
        size_rec.setAdapter(size_adap);
    }

    void displayColor(int color) {
        final int size = colorCodes.size();
//        ContactItem []x = new ContactItem[size];
        if (size > 0) {
//            temp.add(colorCodes);
            for (int i = 0; i < size; i++) {
                temp.add(colorCodes.get(0));
                colorCodes.remove(0);
            }

            colorCodes = temp;
            colorCodes.add(new ColorCode(color));

            adapter.notifyItemRangeRemoved(0, size);
        } else {
            colorCodes.add(new ColorCode(color));
        }
//        Toast.makeText(getActivity(),String.format("Current color: 0x%08x", color),Toast.LENGTH_SHORT).show();

        adapter = new ColorAdapter(getActivity(), colorCodes);
        recyclerView.setAdapter(adapter);
    }

    void displayImage(Uri path) {
        final int size = imageSources.size();
//        ContactItem []x = new ContactItem[size];
        if (size > 0) {
//            temp.add(colorCodes);
            for (int i = 0; i < size; i++) {
                imageTemp.add(imageSources.get(0));
                imageSources.remove(0);
            }

            imageSources = imageTemp;
            imageSources.add(new ImageSource(path));

            imageStrings.add(getPath(path));
            image_adap.notifyItemRangeRemoved(0, size);
        } else {
            imageSources.add(new ImageSource(path));
        }
//        Toast.makeText(getActivity(),String.format("Current color: 0x%08x", color),Toast.LENGTH_SHORT).show();

        image_adap = new ImageAdapter(getActivity(), imageSources);
        image_rec.setAdapter(image_adap);
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
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            displayImage(filePath);

//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
//                image.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
}
