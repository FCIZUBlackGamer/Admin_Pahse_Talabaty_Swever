package com.talabaty.swever.admin.Mabi3atTrend;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
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
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Mabi3at.NewTalabat.NewTalabatAdapter;
import com.talabaty.swever.admin.Mabi3at.SailedReports.SailedReportsTalabatAdapter;
import com.talabaty.swever.admin.Mabi3at.SearchModel;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Mabi3atTrendReports extends Fragment {

    Button to_talab, from_talab;
    DatePickerDialog.OnDateSetListener DatePicker1, DatePicker2;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<BestSell> bestSells;
    Button show_all;
    ImageButton search;
    TextView next, num, last;
    int item_num, page_num;
    int temp_first, temp_last;
//    HashMap<String, BestSell> holder_alpha, holder_date, holder_num;
//    CheckBox order_alpha;
    Spinner /*order_up, order_down,*/ montag_name;
    ArrayList<String> MontagList, indexOfMontagList;
    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;
    ImageButton show;
    int open_close = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trend_report_mabi3at, container, false);
        to_talab = view.findViewById(R.id.to_talab);
        from_talab = view.findViewById(R.id.from_talab);
        search = view.findViewById(R.id.search);
        show_all = view.findViewById(R.id.all);
        show = view.findViewById(R.id.show);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        bestSells = new ArrayList<>();
        next = view.findViewById(R.id.next);
        last = view.findViewById(R.id.previous);
        num = view.findViewById(R.id.item_num);
        montag_name = view.findViewById(R.id.client_name);
        item_num = page_num = 0;
        num.setText(0 + "");
//        holder_alpha = holder_num = holder_date = new HashMap<>();
//        order_alpha = view.findViewById(R.id.order_alpha);
//        order_up = view.findViewById(R.id.order_up);
//        order_down = view.findViewById(R.id.order_down);
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();

        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("المنتجات الأكثر طلبا");

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }

        final SearchModel model = new SearchModel(shopid+"", userid+"", "0", "1");
        final Gson gson = new Gson();
        Log.e("Json", gson.toJson(model));


        loadSpinnerData(shopid);

//        show.setBackgroundColor(R.color.colorPrimaryDark);
        show.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                open_close++;
                final Handler handler = new Handler();
                if (open_close%2==0){

                    show.setBackgroundResource(R.drawable.ic_right);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            montag_name.setAlpha(1);
                        }
                    }, 100);

                }else {

                    show.setBackgroundResource(R.drawable.ic_left);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 100ms
                            montag_name.setAlpha(0);
                        }
                    }, 100);

                }
            }
        });

        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Load All Data With SearchModel
                Log.e("Json", gson.toJson(model));
                loadData(model);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Load All Data With SearchModel
                Log.e("From", from_talab.getText().toString());
                Log.e("To", to_talab.getText().toString());
                if (from_talab.getText().toString().isEmpty() || to_talab.getText().toString().isEmpty()) {
                    model.setProductId(indexOfMontagList.get(MontagList.indexOf(montag_name.getSelectedItem().toString())));
                    loadData(model);
                } else if (montag_name.getSelectedItem().toString().equals("--اختر--")) {
                    loadData(model, from_talab.getText().toString(), to_talab.getText().toString());
                }
            }
        });

        from_talab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , DatePicker2
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        });
        DatePicker2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                from_talab.setText(year + "-" + month + "-" + dayOfMonth);
            }
        };

        to_talab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , DatePicker1
                        , year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        });
        DatePicker1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                to_talab.setText(year + "-" + month + "-" + dayOfMonth);
            }
        };

        temp_first = 0;
        temp_last = 10;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setType("1");
                loadData(model);
            }
        });

//        order_alpha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    orderDate("alpha");
//                }
//            }
//        });

//        order_up.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                order_alpha.setChecked(false);
//                if (order_up.getSelectedItem().toString().equals("الرقم")) {
//                    orderDate("up_num");
//                } else {
////                    orderDate("up_date");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        order_down.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                order_alpha.setChecked(false);
//                if (order_down.getSelectedItem().toString().equals("الرقم")) {
//                    orderDate("down_num");
//                } else {
////                    orderDate("down_date");
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page_num > 1) {
                    model.setType("0");
                    loadData(model);
                } else {
                    Snackbar.make(v, "بدايه الطلبات", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


        /** Initial Data*/
        loadData(model);
    }

    private void loadSpinnerData(final int id) {

        MontagList = new ArrayList<>();
        indexOfMontagList = new ArrayList<>();

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/sampleproduct/Select?ShopId=" + id+"&token=bKPNOJrob8x", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MontagList.add("--اختر--");
                indexOfMontagList.add("0");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Ziena");
                    if (jsonArray.length()>0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String name = jsonObject1.getString("Name");
                            String id = jsonObject1.getString("Id");
                            indexOfMontagList.add(id);
                            MontagList.add(name);

                        }
                    }
                    montag_name.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, MontagList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

//    private void orderDate(String type) {
//
//        // Remove All Previous Data
//        final int size = bestSells.size();
//        if (size > 0) {
//            for (int i = 0; i < size; i++) {
//                bestSells.remove(0);
//            }
//            adapter.notifyItemRangeRemoved(0, size);
//        }
//
//        if (type.equals("alpha")) {
//            List<String> name = new ArrayList<>();
//            Iterator myVeryOwnIterator = holder_alpha.keySet().iterator();
//            while (myVeryOwnIterator.hasNext()) {
//                String key = (String) myVeryOwnIterator.next();
//                name.add(holder_alpha.get(key).getProductName());
//            }
//
//            Collator collator = Collator.getInstance(new Locale("ar"));
//            Collections.sort(name, collator);
//
//            // Fill New Data
//            for (int x = 0; x < name.size(); x++) {
//                bestSells.add(holder_alpha.get(name.get(x)));
//            }
//
//
//        } else if (type.equals("up_num")) {
//            List<String> num = new ArrayList<>();
//            Iterator myVeryOwnIterator = holder_num.keySet().iterator();
//            while (myVeryOwnIterator.hasNext()) {
//                String key = (String) myVeryOwnIterator.next();
//                num.add(holder_num.get(key).getProductId() + "");
//            }
//
//            Collections.sort(num);
//
//            // Fill New Data
//            for (int x = 0; x < num.size(); x++) {
//                bestSells.add(holder_num.get(num.get(x)));
//            }
//
//        } else if (type.equals("up_date")) {
////            List<String> datetime = new ArrayList<>();
////            Iterator myVeryOwnIterator = holder_date.keySet().iterator();
////            while (myVeryOwnIterator.hasNext()) {
////                String key = (String) myVeryOwnIterator.next();
////                datetime.add(holder_date.get(key).getName());
////            }
////
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
////            Collections.sort(datetime);
////
////            // Fill New Data
////            for (int x = 0; x < datetime.size(); x++) {
////                bestSells.add(holder_date.get(datetime.get(x)));
////            }
//
//
//        } else if (type.equals("down_num")) {
//
//            List<String> num = new ArrayList<>();
//            Iterator myVeryOwnIterator = holder_num.keySet().iterator();
//            while (myVeryOwnIterator.hasNext()) {
//                String key = (String) myVeryOwnIterator.next();
//                num.add(holder_num.get(key).getProductId() + "");
//            }
//
//            Collections.sort(num);
//
//            // Fill New Data
//            for (int x = num.size() - 1; x >= 0; x--) {
//                bestSells.add(holder_num.get(num.get(x)));
//            }
//
//        } else if (type.equals("down_date")) {
////            List<String> datetime = new ArrayList<>();
////            Iterator myVeryOwnIterator = holder_date.keySet().iterator();
////            while (myVeryOwnIterator.hasNext()) {
////                String key = (String) myVeryOwnIterator.next();
////                datetime.add(holder_date.get(key).getName());
////            }
////
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
////            Collections.sort(datetime);
////
////            // Fill New Data
////            for (int x = datetime.size()-1; x >= 0; x--) {
////                bestSells.add(holder_date.get(datetime.get(x)));
////            }
//        }
//
//        adapter = new Mabi3atTrendAdapter(getActivity(), bestSells, temp_first, temp_last);
//        recyclerView.setAdapter(adapter);
//
//    }

    private void loadData(final SearchModel model) {
        Gson gson = new Gson();
        final String modelString = gson.toJson(model);
        Log.e("Model", modelString);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/sampleproduct/MostSells",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int temp = 0;
                        progressDialog.dismiss();
                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("BestSell");
                            if (array.length() > 0) {
//                                final int size = bestSells.size();
//                                if (size > 0) {
//                                    for (int i = 0; i < size; i++) {
//                                        bestSells.remove(0);
//                                    }
//                                    adapter.notifyItemRangeRemoved(0, size);
//                                }
                                bestSells = new ArrayList<>();
                                for (int x = 0; x < array.length(); x++) {
                                    JSONObject object1 = array.getJSONObject(x);
                                    if (x == 0) {
                                        temp_first = Integer.parseInt(object1.getString("ProductId"));
                                    } else if (x == array.length() - 1) {
                                        temp_last = Integer.parseInt(object1.getString("ProductId"));
                                    }
                                    BestSell sell = new BestSell
                                            (object1.getInt("ProductId"),
                                                    object1.getDouble("Amountseller"),
                                                    0,
                                                    object1.getDouble("SellPrice"),
                                                    object1.getString("ProductName")
                                            );

                                    // Fill Data For Sort in orderDate()
//                                    holder_num.put(object1.getString("ProductId"), sell);
//                                    holder_alpha.put(object1.getString("ProductName"), sell);

                                    bestSells.add(sell);
                                    temp = Integer.parseInt(object1.getString("ProductId"));
                                }
                                if (model.getType().equals("1")) {
                                    page_num++;
                                } else if (model.getType().equals("0")) {
                                    page_num--;
                                } else {

                                }
                                num.setText(page_num + "");
                            }else {
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_info,
                                        (ViewGroup) getActivity().findViewById(R.id.lay));

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("لا توجد بيانات");

                                Toast toast = new Toast(getActivity());
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new Mabi3atTrendAdapter(getActivity(), bestSells, temp_first, temp_last);
                        recyclerView.setAdapter(adapter);
                        item_num = temp;
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
                hashMap.put("Search", modelString);
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

    private void loadData(final SearchModel model, final String from, final String to) {
        Gson gson = new Gson();
        final String modelString = gson.toJson(model);
        Log.e("Model", modelString);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/sampleproduct/MostSells",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int temp = 0;
                        progressDialog.dismiss();
                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("BestSell");
                            if (array.length() > 0) {
                                final int size = bestSells.size();
                                if (size > 0) {
                                    for (int i = 0; i < size; i++) {
                                        bestSells.remove(0);
                                    }
                                    adapter.notifyItemRangeRemoved(0, size);
                                }
                                for (int x = 0; x < array.length(); x++) {
                                    JSONObject object1 = array.getJSONObject(x);
                                    if (x == 0) {
                                        temp_first = Integer.parseInt(object1.getString("ProductId"));
                                    } else if (x == array.length() - 1) {
                                        temp_last = Integer.parseInt(object1.getString("ProductId"));
                                    }
                                    BestSell sell = new BestSell
                                            (object1.getInt("ProductId"),
                                                    object1.getDouble("Amountseller"),
                                                    0,
                                                    object1.getDouble("SellPrice"),
                                                    object1.getString("ProductName")
                                            );

                                    // Fill Data For Sort in orderDate()
//                                    holder_num.put(object1.getString("ProductId"), sell);
//                                    holder_alpha.put(object1.getString("ProductName"), sell);

                                    bestSells.add(sell);
                                    temp = Integer.parseInt(object1.getString("ProductId"));
                                }
                                if (model.getType().equals("1")) {
                                    page_num++;
                                } else if (model.getType().equals("0")) {
                                    page_num--;
                                } else {

                                }
                                num.setText(page_num + "");
                            }else {
                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_info,
                                        (ViewGroup) getActivity().findViewById(R.id.lay));

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("لا توجد بيانات");

                                Toast toast = new Toast(getActivity());
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new Mabi3atTrendAdapter(getActivity(), bestSells, temp_first, temp_last);
                        recyclerView.setAdapter(adapter);
                        item_num = temp;
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
                hashMap.put("Search", modelString);
                hashMap.put("From", from);
                hashMap.put("To", to);
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
