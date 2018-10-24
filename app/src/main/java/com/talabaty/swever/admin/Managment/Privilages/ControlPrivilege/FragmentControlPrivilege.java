package com.talabaty.swever.admin.Managment.Privilages.ControlPrivilege;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
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
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FragmentControlPrivilege extends Fragment{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<PrivilegeItem> privilegeItems;
    HashMap<String, PrivilegeItem> holder_alpha;
    SearchView search_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_control_privilege, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.priv_rec);
        recyclerView.setLayoutManager(layoutManager);
        privilegeItems = new ArrayList<>();
        holder_alpha = new HashMap<>();
        search_name = view.findViewById(R.id.privilege_name);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("عمليات الصلاحيه");


        loadData(3);


        search_name.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                Log.e("Result",query.toString());
                if (query.toString().length()>0) {
                    List<String> name = new ArrayList<>();
                    Iterator myVeryOwnIterator = holder_alpha.keySet().iterator();
                    while (myVeryOwnIterator.hasNext()) {
                        String key = (String) myVeryOwnIterator.next();
                        if (query.equals(holder_alpha.get(key).getName())) {

                            name.add(holder_alpha.get(key).getName());
                            Log.e("Key", holder_alpha.get(key).getName());
                            Log.e("Szie", name.size()+"");
                        }
                    }

                    Collator collator = Collator.getInstance(new Locale("ar"));
                    Collections.sort(name, collator);


                    if (name.size() >= 1) {

                        for (int i = 0; i < privilegeItems.size(); i++) {
                            privilegeItems.remove(0);
                        }
                        adapter.notifyItemChanged(0, name.size());

                        // Fill New Data
                        for (int x = 0; x < name.size(); x++) {
                            privilegeItems.add(holder_alpha.get(name.get(x)));
                        }

                        adapter = new ControlPrivilegeAdapter(privilegeItems, getActivity());
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), "لا توجد بيانات مطابقه لنتيجه البحث", Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }

        });






    }

    private void loadData(final int shopId) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.sweverteam.com/Rules/list",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("RulesList");

                            final int size = privilegeItems.size();
                            if (size > 0) {
                                for (int i = 0; i < size; i++) {
                                    privilegeItems.remove(0);
                                }
                                adapter.notifyItemRangeRemoved(0, size);
                            }

                            for (int x=0; x<array.length(); x++){
                                JSONObject object1 = array.getJSONObject(x);

                                PrivilegeItem item = new PrivilegeItem(
                                        object1.getInt("Id"),
                                        object1.getString("Name"),
                                        object1.getBoolean("Flag"));
                                privilegeItems.add(item);

                                // Fill Data For Sort in orderDate()
                                holder_alpha.put(object1.getString("Name"), item);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new ControlPrivilegeAdapter(privilegeItems,getActivity());
                        recyclerView.setAdapter(adapter);
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
                //Todo: Shop Id
                hashMap.put("ShopId", shopId+"");
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
