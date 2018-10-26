package com.talabaty.swever.admin.Mabi3at.Delevry.Kilo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.talabaty.swever.admin.Mabi3at.Delevry.Delevry;
import com.talabaty.swever.admin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WithKilo extends Fragment {

    static String ids, values;
    static boolean type;
    EditText ed_value;
    Button save;
    static int shopi;
    Delevry model;
    View view;

    public static WithKilo setData(String id, String Value, boolean Type, int shopid){

        WithKilo withKilo = new WithKilo();
//        Toast.makeText(withKilo.getActivity(),"sdfvs",Toast.LENGTH_SHORT).show();
        ids = id;
        values = Value;
        type = Type;
        shopi = shopid;
        System.out.println("ids "+id);
        System.out.println("Values "+Value);
        System.out.println("Types "+Type+"");
        System.out.println("shopids "+shopid+"");
        return withKilo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delevry_kilo,container,false);
        ed_value = view.findViewById(R.id.value);
        save = view.findViewById(R.id.save);
        System.out.println("id"+ids);
        System.out.println("Value"+values);
        System.out.println("Type"+type+"");
        System.out.println("shopid"+shopi+"");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (values != null) {
            ed_value.setText(values + "");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ed_value.getText().toString().isEmpty()) {
                    saveSubmit();
                }else {
                    Snackbar.make(view,"رجاء ادخال قيمه للتوصيل", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveSubmit() {

//        model.setRegionId(Integer.parseInt(ids));
        model = new Delevry();
        model.setId(Integer.parseInt(ids));
        model.setValue(Double.parseDouble(ed_value.getText().toString()));
        model.setType(false);
        model.setShopId(shopi);
        Gson gson = new Gson();
        final String m = gson.toJson(model);
        Log.e("Moel",m);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/order/EditDeliveryValue",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("RESPONSE",response);
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("Success").equals("Success")) {

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
