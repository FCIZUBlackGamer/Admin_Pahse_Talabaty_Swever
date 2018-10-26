package com.talabaty.swever.admin.Mabi3at;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainHome extends Fragment {

    Button new_talabat, ready_talabat, pended_tasks, returned_talabat, notification_firend, done_talabat, rejected_report, saled_report;
    TextView num_new_talabat, num_ready_talabat, num_done_talabat, num_pend_tasks, num_returned_talabat, num_notification;

    Button my_tasks, delvery;
    TextView num_my_tasks;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mabi3at, container, false);
        new_talabat = (Button) view.findViewById(R.id.new_talabat);
        ready_talabat = view.findViewById(R.id.ready_talabat);
        pended_tasks = view.findViewById(R.id.pend_tasks);
        returned_talabat = view.findViewById(R.id.returned_talabat);
        notification_firend = view.findViewById(R.id.notification_friend);
        done_talabat = view.findViewById(R.id.done_talabat);
        my_tasks = view.findViewById(R.id.my_tasks);
        delvery = view.findViewById(R.id.delevry);
        rejected_report = view.findViewById(R.id.rejected_talabat_report);
        saled_report = view.findViewById(R.id.saled_talabat_report);

        num_new_talabat = view.findViewById(R.id.num_new_talabat);
        num_ready_talabat = view.findViewById(R.id.num_ready_talabat);
        num_done_talabat = view.findViewById(R.id.num_done_talabat);
        num_pend_tasks = view.findViewById(R.id.num_pend_tasks);
        num_returned_talabat = view.findViewById(R.id.num_returned_talabat);
        num_notification = view.findViewById(R.id.num_notification);
        num_my_tasks = view.findViewById(R.id.num_my_tasks);

        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        ((Home) getActivity())
                .setActionBarTitle("المبيعات");

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }

        Log.e("S","Main Home");
//        ExecutorService threadPoolExecutor = Executors.newSingleThreadExecutor();
//        Runnable longRunningTask = new Runnable() {
//            double time = System.currentTimeMillis() / 1000;
//
//            @Override
//            public void run() {
//                try {
//                    MainHome.this.wait(1000*7);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                do {
        loadData();
//                } while (System.currentTimeMillis() / 1000 - time == 7);

//            }
//        };
//
//        final Future longRunningTaskFuture = threadPoolExecutor.submit(longRunningTask);


        final Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
        new_talabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "new");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });

        ready_talabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "ready");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });

        pended_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "pend");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });

        returned_talabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "returned");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });


        notification_firend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "notification");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });

        done_talabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "done");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });

        rejected_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "rejected");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });

        my_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "my_tasks");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });

        delvery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "delivery");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });

        saled_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "sailed");
//                longRunningTaskFuture.cancel(true);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
//        final int size = homeItems.size();
//        if (size > 0) {
//            for (int i = 0; i < size; i++) {
//                homeItems.remove(0);
//            }
//            adapter.notifyItemRangeRemoved(0, size);
//        }
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/Order/OrdersCount",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {
                            if (response.isEmpty()) {
                                Toast.makeText(getActivity(), "No Items", Toast.LENGTH_SHORT).show();
                            } else {
                                JSONObject jsonObject = new JSONObject(response);
//                                JSONObject jsonArray = jsonObject.getJSONObject("OrdersCount");
//                                JSONArray jsonArray = jsonObject.getJSONArray("OrdersCount");

                                JSONObject object = jsonObject.getJSONObject("OrdersCount");
                                // Get NewOrderCount
                                final String NewOrderCount = object.getString("NewOrderCount");

                                num_new_talabat.setText(NewOrderCount);



//                                JSONObject object2 = jsonArray.getJSONObject(0);
                                // Get PreparationOrderCount
                                final String PreparationOrderCount = object.getString("PreparationOrderCount");

                                num_ready_talabat.setText(PreparationOrderCount);


//                                JSONObject object3 = jsonArray.getJSONObject(0);
                                // Get ReturnedOrderCount
                                final String ReturnedOrderCount = object.getString("ReturnedOrderCount");

                                num_returned_talabat.setText(ReturnedOrderCount);


//                                JSONObject object4 = jsonArray.getJSONObject(0);
                                // Get AcceptedOrderCount
                                final String AcceptedOrderCount = object.getString("AcceptedOrderCount");

                                num_pend_tasks.setText(AcceptedOrderCount);


//                                JSONObject object5 = jsonArray.getJSONObject(0);
                                // Get TransportOrderCount
                                final String TransportOrderCount = object.getString("TransportOrderCount");

                                num_notification.setText(TransportOrderCount);


//                                JSONObject object6 = jsonArray.getJSONObject(0);
                                // Get ReceivedOrderCount
                                final String ReceivedOrderCount = object.getString("ReceivedOrderCount");

                                num_done_talabat.setText(ReceivedOrderCount);


                                final String MyOrderCount = object.getString("MyOrderCount");

                                num_my_tasks.setText(MyOrderCount);



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
                hashMap.put("ShopId", shopid+"");
                hashMap.put("UserId", userid+"");
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
