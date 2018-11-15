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
import com.talabaty.swever.admin.Mabi3atDatabase;
import com.talabaty.swever.admin.Mabi3atPermission;
import com.talabaty.swever.admin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


    Mabi3atDatabase systemDatabase;
    Cursor sysCursor;
    List<Mabi3atPermission> systemPermissions;

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

        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();

        systemDatabase = new Mabi3atDatabase(getActivity());
        sysCursor = systemDatabase.ShowData();

        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }

        systemPermissions = new ArrayList<>();
        while (sysCursor.moveToNext()) {
            Mabi3atPermission permission = new Mabi3atPermission();
            permission.setView(Boolean.valueOf(sysCursor.getString(1)));
            permission.setDetalis(Boolean.valueOf(sysCursor.getString(2)));
            permission.setSends(Boolean.valueOf(sysCursor.getString(3)));
            permission.setRefuse(Boolean.valueOf(sysCursor.getString(4)));
            permission.setPreAndDirect(Boolean.valueOf(sysCursor.getString(5)));
            permission.setPreCancel(Boolean.valueOf(sysCursor.getString(6)));
            permission.setAccept(Boolean.valueOf(sysCursor.getString(7)));
            permission.setPreCancelToNewOrder(Boolean.valueOf(sysCursor.getString(8)));
            permission.setReceived(Boolean.valueOf(sysCursor.getString(9)));
            permission.setTransport(Boolean.valueOf(sysCursor.getString(10)));
            permission.setTransportAccept(Boolean.valueOf(sysCursor.getString(11)));
            permission.setScreans2Id(Integer.parseInt(sysCursor.getString(12)));
            systemPermissions.add(permission);
        }

//        sysCursor = systemDatabase.ShowData();
        Log.e("S","Main Home");
        Log.e("Length", systemPermissions.size() + "");

//        while (sysCursor.moveToNext()) {
//            Log.e("0", sysCursor.getString(0));
//            Log.e("1", Boolean.valueOf(sysCursor.getString(1))+"");
//            Log.e("2", sysCursor.getString(2));
//            Log.e("3", sysCursor.getString(3));
//            Log.e("4", sysCursor.getString(4));
//            Log.e("5", sysCursor.getString(5));
//            Log.e("6", sysCursor.getString(6));
//            Log.e("7", sysCursor.getString(7));
//            Log.e("8", sysCursor.getString(8));
//            Log.e("9", sysCursor.getString(9));
//            Log.e("10", sysCursor.getString(10));
//            Log.e("11", sysCursor.getString(11));
//            Log.e("12", sysCursor.getString(12));
//        }
//
//        for (int x=0; x<systemPermissions.size(); x++){
//            Log.e(""+x, systemPermissions.get(x).isView()+"");
//        }

        loadData();


        final Intent intent = new Intent(getActivity(), Mabi3atNavigator.class);
        new_talabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "new");
                Log.e("Value", systemPermissions.get(0).isView() + "");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(0).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        ready_talabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Value", systemPermissions.get(1).isView() + "");
                intent.putExtra("fragment", "ready");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(1).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        pended_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "my_tasks");
                Log.e("Value", systemPermissions.get(4).isView() + "");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(4).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        returned_talabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "returned");
                Log.e("Value", systemPermissions.get(2).isView() + "");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(2).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });


        notification_firend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "notification");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(5).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        done_talabat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "done");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(6).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        rejected_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "rejected");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(8).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        my_tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "pend");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(3).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        delvery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Move To Navigator */
                intent.putExtra("fragment", "delivery");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(0).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        saled_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("fragment", "sailed");
//                longRunningTaskFuture.cancel(true);
                if (systemPermissions.get(7).isView()) {
                    startActivity(intent);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_warning,
                            (ViewGroup) getActivity().findViewById(R.id.lay));

                    TextView text = (TextView) layout.findViewById(R.id.txt);

                    text.setText("لا يمكن عرض الصفحه");

                    Toast toast = new Toast(getActivity());
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });
    }

    private void loadData() {
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
