package com.talabaty.swever.admin.Communication.FriendRequests;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
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
import com.talabaty.swever.admin.Communication.Contacts.ContactItem;
import com.talabaty.swever.admin.Communication.Contacts.FragmentContactItem;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentViewFriendRequests extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ContactItem> contactItems;
    FragmentManager fragmentManager;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_contact, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.contact_rec);
        recyclerView.setLayoutManager(layoutManager);
        contactItems = new ArrayList<>();
        fragmentManager = getFragmentManager();
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentManager = getFragmentManager();
        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
        loadData(shopid);

    }

    private void loadData(final int ShopId) {
        final int size = contactItems.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                contactItems.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, size);
        }
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.sweverteam.com/Shops/FriendRequests?ShopId=" + ShopId+"&token=bKPNOJrob8x",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (!response.equals("[]")) {
                            try {
                                Log.e("FriendRequests", response);

                                JSONObject object = new JSONObject(response);
                                JSONArray array = object.getJSONArray("Mazenger");
                                if (array.length() > 0) {
                                    final int size = contactItems.size();
                                    if (size > 0) {
                                        for (int i = 0; i < size; i++) {
                                            contactItems.remove(0);
                                        }
                                        adapter.notifyItemRangeRemoved(0, size);
                                    }
                                    for (int x = 0; x < array.length(); x++) {
                                        JSONObject object1 = array.getJSONObject(x);
                                        ContactItem item = new ContactItem(
                                                object1.getInt("Id") + "",
                                                object1.getString("Name"),
                                                object1.getString("Photo"),
                                                object1.getString("Address"),
                                                object1.getString("Phone"),
                                                object1.getString("Descripation")
                                        );
                                        contactItems.add(item);
                                    }
                                } else {
                                    Toast toast = Toast.makeText(getActivity(), "لا توجد بيانات", Toast.LENGTH_SHORT);
                                    TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                                    v.setTextColor(Color.GREEN);
                                    toast.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_info,
                                    (ViewGroup) getActivity().findViewById(R.id.lay));

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("لا يوجد طلبات صداقه");

                            Toast toast = new Toast(getActivity());
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                        }
                        adapter = new ContactAdapter(contactItems, getActivity());
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
        }) {
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }
}
