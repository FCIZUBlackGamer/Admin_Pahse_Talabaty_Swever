package com.talabaty.swever.admin.Communication.Contacts;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentContactItem extends Fragment {
    ImageView imageView;
    TextView contact_name, contact_address, contact_phone, contact;
    static boolean IsFriend;
    Button add_friend;
    static String scontact_name, scontact_address, scontact_phone, scontact, simageView, id;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;

    public static FragmentContactItem setData(ContactItem x){
        FragmentContactItem contactItem = new FragmentContactItem();
        scontact_name = x.getName();
        scontact_address = x.getAddress();
        scontact_phone = x.getPhone();
        scontact = x.getDestination();
        simageView = x.getImage();
        id = x.getId();
        IsFriend = x.IsFrind();
        return contactItem;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_item,container,false);
        imageView = view.findViewById(R.id.contact_image);
        contact_name = view.findViewById(R.id.contact_name);
        contact_address = view.findViewById(R.id.contact_address);
        contact_phone = view.findViewById(R.id.contact_phone);
        contact = view.findViewById(R.id.contact);
        add_friend = view.findViewById(R.id.add_friend);
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
        if (simageView != null) {
            Picasso.with(getActivity())
                    .load(simageView)
                    .into(imageView);
        }
        contact.setText(scontact);
        contact_name.setText(scontact_name);
        contact_address.setText(scontact_address);
        contact_phone.setText(scontact_phone);

        add_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend(Integer.parseInt(id),shopid);
            }
        });

        if (IsFriend){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.toast_info,null);

            TextView text = (TextView) layout.findViewById(R.id.txt);
            text.setText("جهه عمل صديقه");

            Toast toast = new Toast(getActivity());
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

            add_friend.setEnabled(false);
            add_friend.setText("صديق مقرب");
        }

    }

    private void addFriend(final int id , final int ShopId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تنفيذ العمليه ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/Shops/AddShop",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Response",response);
                        if (response.equals("\"Success\"")) {

                            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_info,null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("تمت تنفيذ العملية");

                            Toast toast = new Toast(getActivity());
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                        } else {

                            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_error,null);

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
                if (error instanceof ServerError)
                    Toast.makeText(getActivity(), "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(getActivity(), "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(getActivity(), "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("ShopId", ShopId+"");
                hashMap.put("Id", id+"");
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
