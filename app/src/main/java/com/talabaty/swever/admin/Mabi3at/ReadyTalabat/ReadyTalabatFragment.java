package com.talabaty.swever.admin.Mabi3at.ReadyTalabat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
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
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Mabi3at.NewTalabat.NewTalabatAdapter;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReadyTalabatFragment extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Talabat> talabats;
    TextView next, num, last;
    int item_num, page_num;
    int temp_first, temp_last;
    HashMap<String, Talabat> holder_alpha, holder_date, holder_num;
    CheckBox order_alpha;
    Spinner order_up, order_down;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ready_talabat, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        talabats = new ArrayList<>();
        next = view.findViewById(R.id.next);
        last = view.findViewById(R.id.previous);
        num = view.findViewById(R.id.item_num);
        item_num = page_num = 0;
        num.setText(0 + "");
        holder_alpha = holder_num = holder_date = new HashMap<>();
        order_alpha = view.findViewById(R.id.order_alpha);
        order_up = view.findViewById(R.id.order_up);
        order_down = view.findViewById(R.id.order_down);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("الطلبات الجاهزه");

        temp_first = 0;
        temp_last = 10;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(item_num, "1");
            }
        });

        order_alpha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    orderDate("alpha");
                }
            }
        });

        order_up.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                order_alpha.setChecked(false);
                if (order_up.getSelectedItem().toString().equals("الرقم")) {
                    orderDate("up_num");
                } else {
                    orderDate("up_date");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        order_down.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                order_alpha.setChecked(false);
                if (order_down.getSelectedItem().toString().equals("الرقم")) {
                    orderDate("down_num");
                } else {
                    orderDate("down_date");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page_num > 1) {
                    loadData(item_num, "0");
                } else {
                    Snackbar.make(v, "بدايه الطلبات", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        loadData(0, "1");


    }

    private void orderDate(String type) {

        // Remove All Previous Data
        final int size = talabats.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                talabats.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, size);
        }

        if (type.equals("alpha")) {
            List<String> name = new ArrayList<>();
            Iterator myVeryOwnIterator = holder_alpha.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                name.add(holder_alpha.get(key).getName());
            }

            Collator collator = Collator.getInstance(new Locale("ar"));
            Collections.sort(name, collator);

            // Fill New Data
            for (int x = 0; x < name.size(); x++) {
                talabats.add(holder_alpha.get(name.get(x)));
            }


        } else if (type.equals("up_num")) {
            List<String> num = new ArrayList<>();
            Iterator myVeryOwnIterator = holder_num.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                num.add(holder_alpha.get(key).getNum());
            }

            Collections.sort(num);

            // Fill New Data
            for (int x = 0; x < num.size(); x++) {
                talabats.add(holder_num.get(num.get(x)));
            }

        } else if (type.equals("up_date")) {
            List<String> datetime = new ArrayList<>();
            Iterator myVeryOwnIterator = holder_date.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                datetime.add(holder_date.get(key).getName());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            Collections.sort(datetime);

            // Fill New Data
            for (int x = 0; x < datetime.size(); x++) {
                talabats.add(holder_date.get(datetime.get(x)));
            }


        } else if (type.equals("down_num")) {

            List<String> num = new ArrayList<>();
            Iterator myVeryOwnIterator = holder_num.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                num.add(holder_alpha.get(key).getNum());
            }

            Collections.sort(num);

            // Fill New Data
            for (int x = num.size()-1 ; x >= 0 ; x--) {
                talabats.add(holder_num.get(num.get(x)));
            }

        } else if (type.equals("down_date")) {
            List<String> datetime = new ArrayList<>();
            Iterator myVeryOwnIterator = holder_date.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                datetime.add(holder_date.get(key).getName());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            Collections.sort(datetime);

            // Fill New Data
            for (int x = datetime.size()-1; x >= 0; x--) {
                talabats.add(holder_date.get(datetime.get(x)));
            }
        }

        adapter = new NewTalabatAdapter(getActivity(), talabats, temp_first, temp_last);
        recyclerView.setAdapter(adapter);

    }

    private void loadData(final int item, final String r) {
        final int size = talabats.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                talabats.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, size);
        }
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.sweverteam.com/order/PreparationOrderList",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int temp = 0;
                        progressDialog.dismiss();
                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("PreparationOrder");
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object1 = array.getJSONObject(x);
                                if (x == 0) {
                                    temp_first = Integer.parseInt(object1.getString("Id"));
                                } else if (x == array.length() - 1) {
                                    temp_last = Integer.parseInt(object1.getString("Id"));
                                }
                                Talabat talabat = new Talabat
                                        ((x + 1) + "",
                                                object1.getString("CustomerName"),
                                                "",
                                                object1.getString("Id"),
                                                object1.getString("Time"),
                                                object1.getString("Date"),
                                                object1.getString("Address"),
                                                object1.getString("Phone")
                                        );

                                // Fill Data For Sort in orderDate()
                                holder_num.put(object1.getString("Id"), talabat);
                                holder_alpha.put(object1.getString("CustomerName"), talabat);
                                holder_date.put(object1.getString("Date") + " " + object1.getString("Time"), talabat);

                                talabats.add(talabat);
                                temp = Integer.parseInt(object1.getString("Id"));
                            }
                            if (r.equals("1")) {
                                page_num++;
                            } else if (r.equals("0")) {
                                page_num--;
                            } else {

                            }
                            num.setText(page_num + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new ReadyTalabatAdapter(getActivity(), talabats, temp_first, temp_last);
                        recyclerView.setAdapter(adapter);
                        item_num = temp;
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
                hashMap.put("ShopId", "3");
                hashMap.put("UserId", "5");
                hashMap.put("x", item + "");
                hashMap.put("type", r);
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
