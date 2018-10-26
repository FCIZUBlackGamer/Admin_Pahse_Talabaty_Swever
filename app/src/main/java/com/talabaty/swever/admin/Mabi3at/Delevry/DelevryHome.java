package com.talabaty.swever.admin.Mabi3at.Delevry;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.Delevry.Capital.WithCapital;
import com.talabaty.swever.admin.Mabi3at.Delevry.Kilo.WithKilo;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DelevryHome extends Fragment {

    FragmentManager fragmentManager;
    View view;
    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;
    ImageButton capital, kilo;
    String Id, Value, RegionId;
    Boolean Type;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delevry_home,container,false);
        capital = view.findViewById(R.id.capital);
        kilo = view.findViewById(R.id.kilo);
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentManager = getFragmentManager();
        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("تعاملات الديلفرى");

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
//        fragmentManager.beginTransaction().replace(R.id.frame_delevry,new WithKilo()).commit();
        loadData(shopid);



    }

    private void loadData(final int shopId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/order/DeliveryValue?shopid=" + shopId + "&token=bKPNOJrob8x",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {

                            JSONObject object = new JSONObject(response);
                            JSONObject array = object.getJSONObject("DeliveryValue");
                            Log.e("Model", array.toString());
                            Log.e("Model", array.getInt("Value") + "");
                            Id = array.getInt("Id") + "";
                            Value = array.getDouble("Value") + "";
                            Type = array.getBoolean("Type");
                                if (array.getBoolean("Type")){
                                    fragmentManager.beginTransaction().replace(R.id.frame_delevry, new WithKilo().setData(array.getInt("Id") + "", array.getInt("Value") + "", array.getBoolean("Type"), shopid)).commit();
                                }else {
                                    fragmentManager.beginTransaction().replace(R.id.frame_delevry,new WithCapital().setData( array.getInt("Id")+"", array.getInt("Value")+"", array.getBoolean("Type"))).commit();
                                }

                            kilo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("Chosen", "Kilo");
                                    fragmentManager.beginTransaction().replace(R.id.frame_delevry, new WithKilo().setData(Id, Value, Type, shopid)).commit();
                                }
                            });

                            capital.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("Chosen", "Capital");
                                    fragmentManager.beginTransaction().replace(R.id.frame_delevry,new WithCapital().setData( Id, Value, Type)).commit();
                                }
                            });


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
                hashMap.put("ShopId", shopId+"");
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
}
