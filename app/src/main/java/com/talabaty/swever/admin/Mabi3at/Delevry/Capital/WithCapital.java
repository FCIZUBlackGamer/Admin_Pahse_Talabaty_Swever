package com.talabaty.swever.admin.Mabi3at.Delevry.Capital;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.Delevry.Delevry;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels.Cities;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels.Regions;
import com.talabaty.swever.admin.Managment.Employees.AddEmployee.SpinnerModels.State;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithCapital extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Delevry> models;

    public static String ids, values;
    static boolean type;
    EditText ed_value;
    Button save;
    Spinner capital, city, region;

    List<State> states;
    List<String> stateNames;
    List<Cities> cities;
    List<String> cityNames;
    List<Regions> regions;
    List<String> regionNames;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;
    View view;

    public static WithCapital setData(String id, String Value, boolean Type){
        WithCapital withKilo = new WithCapital();
        ids = id;
        values = Value;
        type = Type;
        return withKilo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delevry_capital,container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        models = new ArrayList<>();
        ed_value = view.findViewById(R.id.value);
        save = view.findViewById(R.id.save);
        capital = view.findViewById(R.id.capital);
        city = view.findViewById(R.id.city);
        region = view.findViewById(R.id.region);
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ed_value.setText(values);

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
        loadDelvry();


        loadData(shopid);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ed_value.getText().toString().isEmpty()) {
                    saveSubmit(ed_value.getText().toString());
                }else {
                    Snackbar.make(view,"رجاء ادخال قيمه للتوصيل", Snackbar.LENGTH_SHORT).show();
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

    }

    private void loadDelvry() {
        states = new ArrayList<>();
        regions = new ArrayList<>();
        cities = new ArrayList<>();
        stateNames = new ArrayList<>();
        regionNames = new ArrayList<>();
        cityNames = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/States/Select", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

                    if (getActivity() != null)
                    capital.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, stateNames));
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

    private void loadData(final int shopid) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/order/DeliveryValueList?ShopId="+shopid+"&token=bKPNOJrob8x",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("DeliveryValueList");

                            final int size = models.size();
                            if (size > 0) {
                                for (int i = 0; i < size; i++) {
                                    models.remove(0);
                                }
                                adapter.notifyItemRangeRemoved(0, size);
                            }

                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object1 = array.getJSONObject(x);

                                Delevry item = new Delevry(object1.getInt("Id"),object1.getInt("Value"),
                                        object1.getBoolean("Type"),object1.getInt("RegionId"),
                                        object1.getInt("ShopId"),object1.getString("RegionName"));

                                models.add(item);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Gson gson = new Gson();
//                        Log.e("Key",gson.toJson(models));
                        adapter = new CapitalAdapter(models,getActivity());
                        recyclerView.setAdapter(adapter);

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
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void saveSubmit(String s) {

        Delevry model = new Delevry();
//        model.setRegionName(s);
        model.setValue(Double.parseDouble(ed_value.getText().toString()));
        model.setShopId(shopid);
        model.setRegionId(Integer.parseInt(ids));
        model.setType(true);
        Gson gson = new Gson();
        final String m = gson.toJson(model);
        Log.e("Moel",m);
        final ProgressDialog progressDialog = new ProgressDialog( getActivity());
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/order/AddDeliveryValue",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("RESPONSE",response);
                        progressDialog.dismiss();
                        try {
//                            JSONObject object = new JSONObject(response);

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
                        } catch (Exception e) {
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("delivery", m);
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue( getActivity()).add(stringRequest);

    }
}
