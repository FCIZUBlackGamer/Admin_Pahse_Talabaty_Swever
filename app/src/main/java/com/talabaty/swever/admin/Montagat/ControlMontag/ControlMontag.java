package com.talabaty.swever.admin.Montagat.ControlMontag;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Montagat.AddMontag.ColorCode;
import com.talabaty.swever.admin.Montagat.AddMontag.ImageSource;
import com.talabaty.swever.admin.Montagat.AddMontag.Sanf;
import com.talabaty.swever.admin.Montagat.AddMontag.Size;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ControlMontag extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ControlMontagModel> models;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_montag,container,false);
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

        loadMontages();

    }

    private void loadMontages(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.sweverteam.com/sampleproduct/list",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Data: ", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONArray array = object.getJSONArray("List");
                            for (int x=0; x<array.length(); x++){
                                JSONObject object1 = array.getJSONObject(x);

                                List<ColorCode> codeList = new ArrayList<>();
                                List<ImageSource> sourceList = new ArrayList<>();
                                List<Size> sizeList = new ArrayList<>();

                                ControlMontagModel item = new ControlMontagModel(x+1);
                                item.setId(object1.getInt("Id"));
                                item.setName(object1.getString("Name"));
                                item.setBuyPrice(object1.getInt("BuyPrice"));
                                item.setSellPrice(object1.getInt("SellPrice"));
                                item.setSummary(object1.getString("Summary"));
                                item.setDescription(object1.getString("Description"));
                                item.setNotes(object1.getString("Notes"));
                                item.setInsertDate(object1.getString("InsertDateString"));
                                item.setInsertTime(object1.getString("InsertTimeString"));
                                item.setShop_Id(object1.getInt("Shop_Id"));
                                item.setUserId(object1.getInt("UserId"));
                                item.setEditUserId(object1.getInt("EditUserId"));
                                item.setCriticalQuantity(object1.getInt("CriticalQuantity"));
                                item.setAmount(object1.getInt("Amount"));
                                item.setSampleCatogoriesId(object1.getInt("SampleCatogoriesId"));

                                JSONArray color = new JSONArray(object1.getString("Color"));
//                                JSONArray colorarray = color.getJSONArray("Color");
                                if (color.length() > 0) {
                                    for (int i=0;i<color.length();i++){
                                        JSONObject object2 = color.getJSONObject(i);
                                        ColorCode code = new ColorCode(
                                                object2.getInt("Id"),
                                                object2.getString("Color"),
                                                object2.getInt("Amount"),
                                                object2.getInt("SampleProductId")
                                        );
                                        codeList.add(code);
                                    }
                                }

                                item.setColor(codeList);

                                JSONArray size = new JSONArray(object1.getString("Size"));
//                                JSONArray sizearray = size.getJSONArray("Size");
                                if (size.length() > 0) {
                                    for (int i=0;i<size.length();i++){
                                        JSONObject object2 = size.getJSONObject(i);
                                        Size code = new Size(
                                                object2.getInt("Id"),
                                                object2.getString("Size"),
                                                object2.getInt("SampleProductId")
                                        );
                                        sizeList.add(code);
                                    }
                                }

                                item.setSize(sizeList);

                                JSONArray image = new JSONArray(object1.getString("Gallary"));
//                                JSONArray imagearray = image.getJSONArray("Gallary");
                                if (image.length() > 0) {
                                    for (int i=0;i<image.length();i++){
                                        JSONObject object2 = image.getJSONObject(i);
                                        ImageSource code = new ImageSource(
                                                object2.getInt("Id"),
                                                object2.getString("Photo"),
                                                object2.getInt("SampleProductId")
                                        );
                                        sourceList.add(code);
                                    }
                                }

                                item.setGallary(sourceList);

                                models.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new ControlMontagAdapter(models,getActivity());
                        recyclerView.setAdapter(adapter);

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
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}
