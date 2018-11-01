package com.talabaty.swever.admin.Managment.Employees.AddEmployee;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
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
import com.squareup.picasso.Picasso;
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Managment.Employees.Employee;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class PersonalInfo extends Fragment {
    Button second;
    FragmentManager fragmentManager;
    private static final int CAMERA_REQUEST = 1888;
    private int PICK_IMAGE_REQUEST = 1;
    int state = 0;
    private Bitmap bitmap;
    List<String> imageStrings;
    CircleImageView imageView;
    ImageView add_employee_fragment_employeeIdCopy;
    EditText add_employee_fragment_employeenameTxt, add_employee_fragment_mailTxt, add_employee_fragment_employmentName,
            add_employee_fragment_employeeManagement, add_employee_fragment_employeeBranchName, add_employee_fragment_employeePhone,
            mail, last_name, address;

    Spinner add_employee_fragment_employeeResponsibilities, add_employee_fragment_employeeWorkplaceName;

    List<String> jobKind, indexOfjobKind;
    List<String> jobTitle, indexOfjobTitle;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;

    View Camera_view;
    ImageView close, minimize, cam, gal;
    FloatingActionButton appear;
    int close_type;
    RadioGroup radiogroup;
    boolean var_gender = true;

    static Employee employee = null;
    public static PersonalInfo setData(Employee data){
        PersonalInfo personalInfo = new PersonalInfo();
        employee = data;
        return personalInfo;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addemployee1, container, false);
        second = view.findViewById(R.id.first);
        imageView = view.findViewById(R.id.add_employee_fragment_employeePp);
        add_employee_fragment_employeeIdCopy = view.findViewById(R.id.add_employee_fragment_employeeIdCopy);
        appear = view.findViewById(R.id.appear);
        add_employee_fragment_employeenameTxt = view.findViewById(R.id.add_employee_fragment_employeenameTxt);
        mail = view.findViewById(R.id.add_employee_fragment_mailTxt);
        add_employee_fragment_mailTxt = view.findViewById(R.id.user_name);
        last_name = view.findViewById(R.id.last_name);
        address = view.findViewById(R.id.address);
        radiogroup = view.findViewById(R.id.radiogroup);
        add_employee_fragment_employmentName = view.findViewById(R.id.add_employee_fragment_employmentName);
        add_employee_fragment_employeeManagement = view.findViewById(R.id.add_employee_fragment_employeeManagement);
        add_employee_fragment_employeeBranchName = view.findViewById(R.id.add_employee_fragment_employeeBranchName);
        add_employee_fragment_employeePhone = view.findViewById(R.id.add_employee_fragment_employeePhone);
        add_employee_fragment_employeeResponsibilities = view.findViewById(R.id.add_employee_fragment_employeeResponsibilities);
        add_employee_fragment_employeeWorkplaceName = view.findViewById(R.id.add_employee_fragment_employeeWorkplaceName);
        imageStrings = new ArrayList<>();
        jobKind = new ArrayList<>();
        indexOfjobKind = new ArrayList<>();
        jobTitle = new ArrayList<>();
        indexOfjobTitle = new ArrayList<>();
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((Home) getActivity())
                .setActionBarTitle("إضافه موظف");

        requestStoragePermission();

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }

        loadJobKind(shopid);
        loadJobTitle(shopid);

        if (employee != null){
            add_employee_fragment_employeenameTxt.setText(employee.getFirstName());
            last_name.setText(employee.getLastName());
            address.setText(employee.getAddress());
            if (employee.getGender()){
                radiogroup.check(R.id.male);
            }else {
                radiogroup.check(R.id.female);
            }
            add_employee_fragment_mailTxt.setText(employee.getUserName());
            mail.setText(employee.getMail());
            add_employee_fragment_employmentName.setText(employee.getPassword());

            add_employee_fragment_employeeBranchName.setText(employee.getBranchName());
            add_employee_fragment_employeePhone.setText(employee.getPhone());
            Log.e("Data1", employee.getPhoto());
            if (employee.getPhoto() != null && !employee.getPhoto().isEmpty()) {
                try {
                    Picasso.with(getActivity())
                            .load(employee.getPhoto())
                            .into(imageView);

                } catch (Exception e) {

                }
            }
            Log.e("Data2", employee.getImageCard());
            if (employee.getImageCard() != null && !employee.getImageCard().isEmpty()) {
                try {
                    Picasso.with(getActivity())
                            .load(employee.getImageCard())
                            .into(add_employee_fragment_employeeIdCopy);
                } catch (Exception e) {

                }
            }
        }

//        Picasso.with(getActivity())
//                .load("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg")
//                .into(imageView);

        appear.setVisibility(View.GONE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 1;
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
                state = 1;
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

        add_employee_fragment_employeeIdCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 2;
                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                Camera_view = inflater.inflate(R.layout.camera_view, null);

                close = Camera_view.findViewById(R.id.close);
                minimize = Camera_view.findViewById(R.id.minimize);
                minimize.setVisibility(View.GONE);
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
            }
        });

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male){

                    var_gender = true;
                }else if (checkedId == R.id.female){
                    var_gender = false;
                }
            }
        });

        fragmentManager = getFragmentManager();
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (employee == null) {
                    Employee employee = new Employee();

                    if (FUtilsValidation.isValidEmail(mail, "صيغه بريد الكترونى خاطئه") &&
                            FUtilsValidation.isEmpty(add_employee_fragment_employeenameTxt, "ادخل اسم الموظف") ||
                            FUtilsValidation.isEmpty(add_employee_fragment_mailTxt, "ادخل اسم مستخدم") ||
                            FUtilsValidation.isEmpty(add_employee_fragment_employmentName, "ادخل كلمه سر") ||
                            FUtilsValidation.isEmpty(add_employee_fragment_employeePhone, "ادخل رقم هاتف") ||
                            FUtilsValidation.isEmpty(add_employee_fragment_employeeBranchName, "ادخل اسم الفرع") ||
                            FUtilsValidation.isEmpty(last_name, "ادخل اسم الاخير للموظف") ||
                            FUtilsValidation.isEmpty(address, "ادخل عنوان للموظف") ||
                            FUtilsValidation.isEmpty(add_employee_fragment_employeeManagement, "تاكيد كلمه السر")) {


                    }else {
                        employee.setFullName(add_employee_fragment_employeenameTxt.getText().toString()+last_name.getText().toString());
                        employee.setFirstName(add_employee_fragment_employeenameTxt.getText().toString());
                        employee.setUserName(add_employee_fragment_mailTxt.getText().toString());
                        employee.setMail(mail.getText().toString());
                        employee.setLastName(last_name.getText().toString());
                        employee.setAddress(address.getText().toString());
                        employee.setGender(var_gender);
                        employee.setPassword(add_employee_fragment_employmentName.getText().toString());
                        employee.setEmploymentTypeId(Integer.parseInt(indexOfjobKind.get(jobKind.indexOf(add_employee_fragment_employeeResponsibilities.getSelectedItem().toString()))));
                        employee.setWorkingNaturalId(Integer.parseInt(indexOfjobTitle.get(jobTitle.indexOf(add_employee_fragment_employeeWorkplaceName.getSelectedItem().toString()))));
                        employee.setBranchName(add_employee_fragment_employeeBranchName.getText().toString());
                        employee.setPhone(add_employee_fragment_employeePhone.getText().toString());

                        if (add_employee_fragment_employmentName.getText().toString().equals(add_employee_fragment_employeeManagement.getText().toString())) {
                            fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new MoneyStaff().setData(employee, ((BitmapDrawable) imageView.getDrawable()).getBitmap(), ((BitmapDrawable) add_employee_fragment_employeeIdCopy.getDrawable()).getBitmap(), 0)).addToBackStack("MoneyStaff").commit();
                        } else {
                            add_employee_fragment_employeeManagement.setError("كلمه مرور غير متطابقه");
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("كلمه مرور غير متطابق")
                                    .setNegativeButton(" إخفاء ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setCancelable(false)
                                    .show();
                        }
                    }

                }else {
                    /** start*/
                    if (add_employee_fragment_employmentName.getText().toString().equals(add_employee_fragment_employeeManagement.getText().toString())) {
                        fragmentManager.beginTransaction().replace(
                                R.id.frame_mabi3at,
                                new MoneyStaff().setData(
                                        employee,
                                        ((BitmapDrawable) imageView.getDrawable()).getBitmap(),
                                        ((BitmapDrawable) add_employee_fragment_employeeIdCopy.getDrawable()).getBitmap(),1))
                                .addToBackStack("MoneyStaff").commit();
                    } else {
                        add_employee_fragment_employeeManagement.setError("كلمه مرور غير متطابقه");
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("كلمه مرور غير متطابق")
                                .setNegativeButton(" إخفاء ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setCancelable(false)
                                .show();
                    }
                    /** end*/
                }
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri filePath;
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                Log.e("1","c");
                loadImage(bitmap);
//                imageView.setImageBitmap(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            Log.e("1","h");
//            imageView.setImageBitmap(bitmap);
            loadImage(bitmap);
        }
    }

    private void loadImage(Bitmap bitmap) {
        imageStrings.add(getStringImage(bitmap));
        if (state == 1) {
            Log.e("2","c");
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);

//            Picasso.with(getActivity()).load("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg")
//                    .into(imageView);
        } else if (state == 2) {
            Log.e("3","c");
            add_employee_fragment_employeeIdCopy.setVisibility(View.VISIBLE);
            add_employee_fragment_employeeIdCopy.setImageBitmap(bitmap);

        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

    private void loadJobKind(int ShopId) {

        jobKind = new ArrayList<>();
        indexOfjobKind = new ArrayList<>();

        if (jobKind.size() > 0) {
            for (int x = 0; x < jobKind.size(); x++) {
                jobKind.remove(x);
                indexOfjobKind.remove(x);
            }
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.sellsapi.rivile.com/Employee/SelectEmploymentType?ShopId=" + ShopId+"&token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("EmploymentType");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        String id = jsonObject1.getString("Id");
                        jobKind.add(fname);
                        indexOfjobKind.add(id);

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, jobKind);

                    add_employee_fragment_employeeResponsibilities.setAdapter(adapter);
                    Log.e("EmployeeTypeId",indexOfjobKind.size()+"");
//                    Log.e("EmployeeTypeId",employee.getEmploymentTypeId()+"");
                    if (employee != null){
                        add_employee_fragment_employeeResponsibilities.setSelection(employee.getEmploymentTypeId()-1);
                    }
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

    private void loadJobTitle(int ShopId) {

        jobTitle = new ArrayList<>();
        indexOfjobTitle = new ArrayList<>();

        if (jobTitle.size() > 0) {
            for (int x = 0; x < jobTitle.size(); x++) {
                jobTitle.remove(x);
                indexOfjobTitle.remove(x);
            }
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.sellsapi.rivile.com/Employee/SelectWorkingNatural?ShopId=" + ShopId+"&token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("WorkingNatural");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        String id = jsonObject1.getString("Id");
                        jobTitle.add(fname);
                        indexOfjobTitle.add(id);

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, jobTitle);
                    add_employee_fragment_employeeWorkplaceName.setAdapter(adapter);

                    Log.e("WorkingNatural",indexOfjobTitle.size()+"");
//                    Log.e("WorkingNatural",employee.getWorkingNaturalId()+"");
                    if (employee != null){
                        add_employee_fragment_employeeWorkplaceName.setSelection(employee.getWorkingNaturalId()-1);
                    }
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
}
