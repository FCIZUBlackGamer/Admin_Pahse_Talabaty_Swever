package com.talabaty.swever.admin.Mabi3at.Delevry.Capital;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class CapitalAdapter extends RecyclerView.Adapter<CapitalAdapter.Vholder> {

    List<Delevry> models;


    Context context;
    FragmentManager fragmentManager;
    View edit_cap;
    EditText ed_value;
    Button save;
    ImageView close;
    Spinner capital, city, region;

    List<State> states;
    List<String> stateNames;
    List<Cities> cities;
    List<String> cityNames;
    List<Regions> regions;
    List<String> regionNames;
    
    public CapitalAdapter(List<Delevry> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_delivry_capital_fragment, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.captital_name.setText(models.get(position).getRegionName());
        holder.delivry_price.setText(models.get(position).getValue()+"");

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, Home.class);
//                intent.putExtra("fragment","edit_control");
//                context.startActivity(intent);

                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                edit_cap = inflater.inflate(R.layout.dialog_delevery_edit, null);

                ed_value = edit_cap.findViewById(R.id.value);
                capital = edit_cap.findViewById(R.id.capital);
                city = edit_cap.findViewById(R.id.city);
                region = edit_cap.findViewById(R.id.region);
                save = edit_cap.findViewById(R.id.save);
                close = edit_cap.findViewById(R.id.close);

                loadDelvry();


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

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("تعديل التوصيله")
                        .setCancelable(false)
                        .setView(edit_cap)
                        .setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do Nothing
                                clearTaghezView();
                                dialog.dismiss();
                            }
                        });
                final AlertDialog dialog2 = builder.create();
                dialog2.show();
                dialog2.getWindow().setLayout(1200, 800);


                closeMessage(dialog2);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveSubmit(dialog2, region.getSelectedItem().toString(), position);
                    }
                });
                
            }
        });
        
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });


    }

    private void saveSubmit(final AlertDialog dialog2, String s, int pos) {

        final Delevry model = new Delevry(
                models.get(pos).getId(),
                Integer.parseInt(ed_value.getText().toString()),
                false,
                regions.get(pos).getId(),
                3,
                regions.get(pos).getName()
        );
        Gson gson = new Gson();
        final String m = gson.toJson(model);
        Log.e("Moel",m);
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/order/EditDeliveryValue",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dialog2.dismiss();

                        Log.e("RESPONSE",response);
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("Success").equals("Success")) {

                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                View layout = inflater.inflate(R.layout.toast_info, null);

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("تمت تنفيذ العملية");

                                Toast toast = new Toast(context);
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();

                            } else {

                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                View layout = inflater.inflate(R.layout.toast_error, null);

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("حدث خطأ اثناء اجراء العمليه");

                                Toast toast = new Toast(context);
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                dialog2.dismiss();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.toast_warning,null);

                TextView text = (TextView) layout.findViewById(R.id.txt);

                if (error instanceof ServerError)
                    text.setText("خطأ فى الاتصال بالخادم");
                else if (error instanceof TimeoutError)
                    text.setText("خطأ فى مدة الاتصال");
                else if (error instanceof NetworkError)
                    text.setText("شبكه الانترنت ضعيفه حاليا");

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("model", m);
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);

    }

    private void delete(int pos) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/order/Delete?Id="+regions.get(pos).getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("RESPONSE",response);
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("Success").equals("Success")) {

                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                View layout = inflater.inflate(R.layout.toast_info, null);

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("تمت تنفيذ العملية");

                                Toast toast = new Toast(context);
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();

                            } else {

                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                View layout = inflater.inflate(R.layout.toast_error, null);

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("حدث خطأ اثناء اجراء العمليه");

                                Toast toast = new Toast(context);
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.toast_warning,null);

                TextView text = (TextView) layout.findViewById(R.id.txt);

                if (error instanceof ServerError)
                    text.setText("خطأ فى الاتصال بالخادم");
                else if (error instanceof TimeoutError)
                    text.setText("خطأ فى مدة الاتصال");
                else if (error instanceof NetworkError)
                    text.setText("شبكه الانترنت ضعيفه حاليا");

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
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
        Volley.newRequestQueue(context).add(stringRequest);

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {

        TextView captital_name, delivry_price;
        ImageButton edit, delete;

        public Vholder(View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            captital_name = itemView.findViewById(R.id.name);
            delivry_price = itemView.findViewById(R.id.value);

        }

    }

    private void loadDelvry() {
        states = new ArrayList<>();
        regions = new ArrayList<>();
        cities = new ArrayList<>();
        stateNames = new ArrayList<>();
        regionNames = new ArrayList<>();
        cityNames = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue( context);
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

                    if ( context != null)
                        capital.setAdapter(new ArrayAdapter<>( context, android.R.layout.simple_spinner_dropdown_item, stateNames));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.toast_warning,null);

                TextView text = (TextView) layout.findViewById(R.id.txt);

                if (error instanceof ServerError)
                    text.setText("خطأ فى الاتصال بالخادم");
                else if (error instanceof TimeoutError)
                    text.setText("خطأ فى مدة الاتصال");
                else if (error instanceof NetworkError)
                    text.setText("شبكه الانترنت ضعيفه حاليا");

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
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
        city.setAdapter(new ArrayAdapter<String>( context, android.R.layout.simple_spinner_dropdown_item, filteredCitys));
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
        region.setAdapter(new ArrayAdapter<String>( context, android.R.layout.simple_spinner_dropdown_item, filteredRegions));
    }

    private void clearTaghezView() {
        if (edit_cap != null) {
            ViewGroup parent = (ViewGroup) edit_cap.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }

    private void closeMessage(final Dialog dialog) {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTaghezView();
                dialog.dismiss();
            }
        });
    }
}
