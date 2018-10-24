package com.talabaty.swever.admin.Montagat.ControlBaseFood_Additions;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Montagat.AddMontag.ImageSource;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontag;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagModel;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentControlBaseFood_Additions extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ControlMontagModel> models;
    View view;

    static String Type;
    public static FragmentControlBaseFood_Additions setType(String type){
        FragmentControlBaseFood_Additions controlMontag = new FragmentControlBaseFood_Additions();
        Type = type;
        Log.e("Type","FragmentControlBaseFood_Additions "+Type);
        return controlMontag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_basefood_additions,container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        models = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("عمليات المنتجات");

        Log.e("Start","Hello "+Type);
        if (Type.equals("3"))
            loadBaseFood();
        else if (Type.equals("4"))
            loadAditions();

//        Toast.makeText(getActivity(),"additions",Toast.LENGTH_SHORT).show();
//        for (int x = 0; x < 10; x++) {
//
//            List<ImageSource> sourceList = new ArrayList<>();
//
//            ControlMontagModel item = new ControlMontagModel(x + 1);
//            item.setId(x);
//            item.setName(("Name"));
//            item.setSellPrice(12);
//            item.setShop_Id(2);
//
//            sourceList.add(new ImageSource(
//                    0,
//                    "",
//                    0
//            ));
//
//            item.setGallary(sourceList);
//
//            models.add(item);
//        }
//        adapter = new ControlBaseFood_AdditionsAdapter(models, getActivity(), 3);
//        recyclerView.setAdapter(adapter);

    }

    private void loadBaseFood(){
        final int size = models.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                models.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, size);
        }
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.sweverteam.com/BaseFood/List?token=bKPNOJrob8x",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Data: ", s);
                        if (s.equals("\"fail\"")) {
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_error,
                                    (ViewGroup) getActivity().findViewById(R.id.lay));

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("لا يوجد بيانات");

                            Toast toast = new Toast(getActivity());
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                        }else {
                            try {
                                JSONObject object = new JSONObject(s);
                                JSONArray array = object.getJSONArray("List");
                                for (int x = 0; x < array.length(); x++) {
                                    JSONObject object1 = array.getJSONObject(x);

                                    List<ImageSource> sourceList = new ArrayList<>();

                                    ControlMontagModel item = new ControlMontagModel(x + 1);
                                    item.setId(object1.getInt("Id"));
                                    item.setName(object1.getString("Name"));
                                    item.setSellPrice(object1.getInt("Price"));
                                    item.setShop_Id(object1.getInt("ShopId"));

                                    sourceList.add(new ImageSource(
                                            0,
                                            object1.getString("Photo"),
                                            0
                                    ));

                                    item.setGallary(sourceList);

                                    models.add(item);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new ControlBaseFood_AdditionsAdapter(models, getActivity(), 3);
                            recyclerView.setAdapter(adapter);
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
                HashMap hashMap = new HashMap();
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void loadAditions(){
        final int size = models.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                models.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, size);
        }

        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.sweverteam.com/Additions/List?token=bKPNOJrob8x",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Data: ", s);
                        if (s.equals("\"fail\"")) {
                            Toast.makeText(getActivity(),"لا يوجد بيانات",Toast.LENGTH_SHORT).show();
                        }else {
                            try {
                                JSONObject object = new JSONObject(s);
                                JSONArray array = object.getJSONArray("List");
                                for (int x = 0; x < array.length(); x++) {
                                    JSONObject object1 = array.getJSONObject(x);

                                    ControlMontagModel item = new ControlMontagModel(x + 1);
                                    item.setId(object1.getInt("Id"));
                                    item.setName(object1.getString("Name"));
                                    item.setSellPrice(object1.getInt("Price"));
                                    item.setShop_Id(object1.getInt("ShopId"));

                                    models.add(item);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new ControlBaseFood_AdditionsAdapter(models, getActivity(), 4);
                            recyclerView.setAdapter(adapter);
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
                HashMap hashMap = new HashMap();
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}
