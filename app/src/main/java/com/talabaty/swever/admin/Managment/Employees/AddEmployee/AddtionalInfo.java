package com.talabaty.swever.admin.Managment.Employees.AddEmployee;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.fourhcode.forhutils.FUtilsValidation;
import com.google.gson.Gson;
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels.Cities;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels.Mangment;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels.Regions;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels.Rules;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels.State;
import com.talabaty.swever.admin.Managment.Employees.Employee;
import com.talabaty.swever.admin.Managment.Employees.EmployeesHome;
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

public class AddtionalInfo extends Fragment {

    String baseUrl = "http://www.selltlbaty.rivile.com/";
    private String UPLOAD_URL = baseUrl + "Uploads/UploadAndro";

    private String KEY_IMAGE = "base64imageString";
    private String KEY_NAME = "name";

    Button second;
    Button save, ignore;
    FragmentManager fragmentManager;
    static Employee employee;

    Spinner add_employee_fragment_employeenameTxt, add_employee_fragment_mailTxt;
    EditText add_employee_fragment_employmentName, add_employee_fragment_employeeManagement,
            add_employee_fragment_employeeResponsibilities, add_employee_fragment_employeeWorkplaceName;

    CheckBox add_employee_fragment_employeeBranchNameTitle;
    Spinner capital, city, region;
    LinearLayout layout1, layout2, layout3;

    List<State> states;
    List<String> stateNames;
    List<Cities> cities;
    List<String> cityNames;
    List<Regions> regions;
    List<String> regionNames;
    List<Rules> rules;
    List<String> rulNames;
    List<Mangment> mangments;
    List<String> manageNames;

    static Bitmap photo, Card;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;

    static int Type = 0;
    String Link = null;

    static AddtionalInfo setData(Employee data, Bitmap phot, Bitmap Car, int type){
        AddtionalInfo info = new AddtionalInfo();
        employee = new Employee();
        employee = data;
        photo = phot;
        Card = Car;
        Type = type;
        return info;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addemployee3,container,false);
        second = view.findViewById(R.id.third);
        save = view.findViewById(R.id.save);
        ignore = view.findViewById(R.id.ignore);
        add_employee_fragment_employeenameTxt = view.findViewById(R.id.add_employee_fragment_employeenameTxt);
        add_employee_fragment_mailTxt = view.findViewById(R.id.add_employee_fragment_mailTxt);
        add_employee_fragment_employmentName = view.findViewById(R.id.add_employee_fragment_employmentName);
        add_employee_fragment_employeeManagement = view.findViewById(R.id.add_employee_fragment_employeeManagement);
        add_employee_fragment_employeeResponsibilities = view.findViewById(R.id.add_employee_fragment_employeeResponsibilities);
        add_employee_fragment_employeeWorkplaceName = view.findViewById(R.id.add_employee_fragment_employeeWorkplaceName);
        add_employee_fragment_employeeBranchNameTitle = view.findViewById(R.id.add_employee_fragment_employeeBranchNameTitle);
        capital = view.findViewById(R.id.capital);
        city = view.findViewById(R.id.city);
        region = view.findViewById(R.id.region);
        layout1 = view.findViewById(R.id.layout1);
        layout2 = view.findViewById(R.id.layout2);
        layout3 = view.findViewById(R.id.layout3);

        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((Home) getActivity())
                .setActionBarTitle("إضافه موظف");
        fragmentManager = getFragmentManager();

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }

        Link = "http://sellsapi.rivile.com/Employee/Add";
        loadPrivilage(shopid);
        loadManagment(shopid);
        loadDelvry();
        if (employee != null && Type == 1) {


            add_employee_fragment_employmentName.setText(employee.getWorkingStart() + "");
            add_employee_fragment_employeeManagement.setText(employee.getWorkingEnd() + "");
            add_employee_fragment_employeeResponsibilities.setText(employee.getCreditLimit() + "");
            add_employee_fragment_employeeWorkplaceName.setText(employee.getBalance() + "");
            Link = "http://sellsapi.rivile.com/Employee/Edit";
        }

        add_employee_fragment_employeeBranchNameTitle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                    layout3.setVisibility(View.VISIBLE);
                }else {
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.GONE);
                }
            }
        });


        capital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Capital",stateNames.get(position));
                fillCitys(stateNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("City",cityNames.get(position));
                fillRegions(cityNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new MoneyStaff()).addToBackStack("MoneyStaff").commit();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FUtilsValidation.isEmpty(add_employee_fragment_employmentName, "ادخل قيمه")&&
                        FUtilsValidation.isEmpty(add_employee_fragment_employeeManagement, "ادخل قيمه")&&
                        FUtilsValidation.isEmpty(add_employee_fragment_employeeResponsibilities, "ادخل قيمه")&&
                        FUtilsValidation.isEmpty(add_employee_fragment_employeeResponsibilities, "ادخل قيمه")&&
                        FUtilsValidation.isEmpty(add_employee_fragment_employeeWorkplaceName, "ادخل قيمه")
                        ){
                }else {
                    uploadImage();
                    fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new EmployeesHome()).addToBackStack("EmployeesHome").commit();
                }

            }
        });
    }

    private void loadPrivilage(int ShopId) {
        rulNames = new ArrayList<>();
        rules = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/Rules/Select?ShopId="+ShopId+"&token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
//                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
//                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        int id = jsonObject1.getInt("Id");
                        Rules rule = new Rules(id,fname);
                        rulNames.add(fname);
                        rules.add(rule);
                    }

                    add_employee_fragment_employeenameTxt.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, rulNames));
                    if (employee != null && Type == 1) {
                        add_employee_fragment_employeenameTxt.setSelection(employee.getRulesId() - 1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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

    private void loadManagment(int ShopId) {
        manageNames = new ArrayList<>();
        mangments = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/Employee/SelectManagment?ShopId="+ShopId+"&token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Managment");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        int id = jsonObject1.getInt("Id");
                        Mangment mangment = new Mangment(id,fname);
                        manageNames.add(fname);
                        mangments.add(mangment);
                    }

                    add_employee_fragment_mailTxt.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, manageNames));
                    if (employee != null && Type == 1) {
                        add_employee_fragment_mailTxt.setSelection(employee.getManagmentId() - 1);
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

    private void fillCitys(String capital) {
        List<String> filteredCitys = new ArrayList<>();
        for (int x= 0; x<stateNames.size(); x++){
            if (states.get(x).getName().equals(capital)){
                for (int y=0; y<cities.size(); y++){
                    if (cities.get(y).getStateId() == states.get(x).getId()){
                        filteredCitys.add(cities.get(y).getName());
                    }
                }
            }
        }
        city.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, filteredCitys));
    }

    private void fillRegions(String regio) {
        List<String> filteredRegions = new ArrayList<>();
        for (int x= 0; x<cityNames.size(); x++){
            if (cities.get(x).getName().equals(regio)){
                for (int y=0; y<regions.size(); y++){
                    if (regions.get(y).getCityId() == cities.get(x).getId()){
                        filteredRegions.add(regions.get(y).getName());
                    }
                }
            }
        }
        region.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, filteredRegions));
    }

    private void loadDelvry() {
        states = new ArrayList<>();
        regions = new ArrayList<>();
        cities = new ArrayList<>();
        stateNames = new ArrayList<>();
        regionNames = new ArrayList<>();
        cityNames = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/States/Select"+"?token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("State");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        int id = jsonObject1.getInt("Id");
                        State state = new State(id,fname);
                        stateNames.add(fname);
                        states.add(state);
                    }

                    JSONArray jsonArray1 = jsonObject.getJSONArray("Cities");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        int id = jsonObject1.getInt("Id");
                        int StateId = jsonObject1.getInt("StateId");
                        Cities citie = new Cities(id,StateId,fname);
                        cityNames.add(fname);
                        cities.add(citie);
                    }

                    JSONArray jsonArray2 = jsonObject.getJSONArray("Regions");
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        JSONObject jsonObject1 = jsonArray2.getJSONObject(i);
                        String fname = jsonObject1.getString("Name");
                        int id = jsonObject1.getInt("Id");
                        int CityId = jsonObject1.getInt("CityId");
                        Regions region = new Regions(id,CityId,fname);
                        regionNames.add(fname);
                        regions.add(region);
                    }

                    capital.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, stateNames));
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

    private void uploadImage() {
        final Gson gson = new Gson();
        Log.e("Connection UploadImage", "Here");
        final List<String> Gallary = new ArrayList<>();
        //getStringImage(((BitmapDrawable)imageView.getDrawable()).getBitmap())
//        Log.e("Card",getStringImage(Card));
//        Log.e("Photo",getStringImage(photo));
        Gallary.add(getStringImage(Card));
        Gallary.add(getStringImage(photo));
        final String allImages = gson.toJson(Gallary);
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
                                if (x == 0) {
                                    employee.setImageCard(object1);
                                }else if (x == 1) {
                                    employee.setPhoto(object1);
                                }
                            }

                            addEmployee();
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

    private void addEmployee() {
        Log.e("RulsId",rules.get(rulNames.indexOf(add_employee_fragment_employeenameTxt.getSelectedItem().toString())).getId()+"");
        employee.setRulesId(rules.get(rulNames.indexOf(add_employee_fragment_employeenameTxt.getSelectedItem().toString())).getId());
        Log.e("ManagmentId",mangments.get(manageNames.indexOf(add_employee_fragment_mailTxt.getSelectedItem().toString())).getId()+"");
        employee.setManagmentId(mangments.get(manageNames.indexOf(add_employee_fragment_mailTxt.getSelectedItem().toString())).getId());
        employee.setUserId(userid);
        employee.setShopId(shopid);
        employee.setWorkingStart(add_employee_fragment_employmentName.getText().toString());
        employee.setWorkingEnd(add_employee_fragment_employeeManagement.getText().toString());
        employee.setCreditLimit(Integer.parseInt(add_employee_fragment_employeeResponsibilities.getText().toString()));
        employee.setBalance(Integer.parseInt(add_employee_fragment_employeeWorkplaceName.getText().toString()));
        final Gson gson = new Gson();
        Log.e("Employee",gson.toJson(employee));

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تسجيل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equals("\"Success\"")) {

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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
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
                HashMap<String,String> map = new HashMap<>();
                map.put("Employee",gson.toJson(employee));
                map.put("token", "bKPNOJrob8x");
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

}
