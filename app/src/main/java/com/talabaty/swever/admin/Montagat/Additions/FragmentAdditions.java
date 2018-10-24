package com.talabaty.swever.admin.Montagat.Additions;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Montagat.AddMontag.AddMontag;
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagModel;
import com.talabaty.swever.admin.Montagat.FragmentMontag;
import com.talabaty.swever.admin.R;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class FragmentAdditions extends Fragment {
    RecyclerView recyclerView; //rec_list
    RecyclerView.Adapter adapter;
    List<Item> items;
    private String UPLOAD_LINK = "http://sellsapi.sweverteam.com/Additions/Add";
    Button save, done;
    EditText name, price;

    LoginDatabae loginDatabae;
    Cursor cursor;
    View view;

    int shopId;
    FragmentManager fragmentManager;
    ControlMontagModel montagModel;

    public static FragmentAdditions setData(ControlMontagModel x) {
        FragmentAdditions c = new FragmentAdditions();
        c.montagModel = x;
        return c;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_additions, container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec_list);
        save = (Button) view.findViewById(R.id.save);
        done = (Button) view.findViewById(R.id.done);
        name = (EditText) view.findViewById(R.id.name);
        price = (EditText) view.findViewById(R.id.price);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        items = new ArrayList<>();
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        fragmentManager = getFragmentManager();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        while (cursor.moveToNext()){
            shopId = Integer.parseInt(cursor.getString(3));
        }

        if (montagModel != null){
            name.setText(montagModel.getName());
            price.setText(montagModel.getSellPrice() + "");
            UPLOAD_LINK = "http://sellsapi.sweverteam.com/Additions/Edit";
            save.setText("تعديل");

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()){
                    name.setError("ادخل اسم المنتج");
                }else {
                    if (price.getText().toString().isEmpty()){
                        price.setError("ادخل سعر المنتج");
                    }else {
                        items.add(new Item(name.getText().toString(), Float.parseFloat(price.getText().toString()), shopId));
                        adapter.notifyDataSetChanged();
                        name.setText("");
                        price.setText("");
                    }
                }

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (items.size() > 0) {
                    Gson json = new Gson();
                    final String jsonInString = json.toJson(items);
                    Log.e("Data", jsonInString);
                    final ProgressDialog loading = ProgressDialog.show(getActivity(), "Uploading...", "Please wait...", false, false);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_LINK,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {
                                    //Disimissing the progress dialog
                                    loading.dismiss();
                                    Log.e("Data: ", s);
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
                                        fragmentManager.beginTransaction().replace(R.id.frame_mabi3at, new FragmentMontag()).commit();

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
                            params.put("Addition", jsonInString);

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
                }else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_error,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText(" عذرا لا توجد منتجات لاضافتها");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        adapter = new ItemAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);
    }
}
