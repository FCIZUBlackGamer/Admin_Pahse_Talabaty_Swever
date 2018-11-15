package com.talabaty.swever.admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Temp extends AppCompatActivity {

    List<Mabi3atPermission> systemPremessions;
    List<SystemPermission> mabi3atPermissions;
    LoginDatabae loginDatabae;
    Mabi3atDatabase systemDatabase;
    SystemDatabase satabase;
    Cursor cursor/*, sysCursor, mCursor*/;
    String RuleId;
    boolean retry = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        loginDatabae = new LoginDatabae(this);
        systemDatabase = new Mabi3atDatabase(this);
        satabase = new SystemDatabase(this);
        cursor = loginDatabae.ShowData();
        while (cursor.moveToNext()) {
            RuleId = cursor.getString(7);
        }

//        while (!retry) {
//
//            synchronized (this) {

                RequestQueue queue = Volley.newRequestQueue(Temp.this);
                StringRequest request = new StringRequest(Request.Method.GET, "http://sellsapi.rivile.com/Permissions/ListPer?RuleId=" + RuleId + "&token=bKPNOJrob8x", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        systemPremessions = new ArrayList<>();
                        mabi3atPermissions = new ArrayList<>();
                        retry = true;
                        try {

                            JSONObject object = new JSONObject(response);
                            JSONArray Permission = object.getJSONArray("Permission");
                            JSONArray Permission2 = object.getJSONArray("Permission2");

                            if (Permission2.length() > 0) {
                                systemDatabase.EmptyData(); // Empty All Previous Permissions
                                for (int x = 0; x < Permission2.length(); x++) {
                                    JSONObject ob = Permission2.getJSONObject(x);
                                    Mabi3atPermission sPremession = new Mabi3atPermission();
                                    sPremession.setAccept(ob.getBoolean("Accept"));
                                    sPremession.setDetalis(ob.getBoolean("Detalis"));
                                    sPremession.setPreAndDirect(ob.getBoolean("PreAndDirect"));
                                    sPremession.setPreCancel(ob.getBoolean("PreCancel"));
                                    sPremession.setPreCancelToNewOrder(ob.getBoolean("PreCancelToNewOrder"));
                                    sPremession.setReceived(ob.getBoolean("Received"));
                                    sPremession.setRefuse(ob.getBoolean("Refuse"));
                                    sPremession.setScreans2Id(ob.getInt("Screens2Id"));
                                    sPremession.setSends(ob.getBoolean("Sends"));
                                    sPremession.setTransport(ob.getBoolean("Transport"));
                                    sPremession.setView(ob.getBoolean("View"));
                                    sPremession.setPreAndDirect(ob.getBoolean("PreAndDirect"));
                                    sPremession.setTransportAccept(ob.getBoolean("TransportAccept"));
                                    // Store new Permission list
                                    systemDatabase.InsertData(
                                            String.valueOf(sPremession.isView()),
                                            String.valueOf(sPremession.isDetalis()),
                                            String.valueOf(sPremession.isSends()),
                                            String.valueOf(sPremession.isRefuse()),
                                            String.valueOf(sPremession.isPreAndDirect()),
                                            String.valueOf(sPremession.isPreCancel()),
                                            String.valueOf(sPremession.isAccept()),
                                            String.valueOf(sPremession.isPreCancelToNewOrder()),
                                            String.valueOf(sPremession.isReceived()),
                                            String.valueOf(sPremession.isTransport()),
                                            String.valueOf(sPremession.isTransportAccept()),
                                            String.valueOf(sPremession.getScreans2Id())
                                    );

                                    systemPremessions.add(sPremession);
                                }
                            }

                            if (Permission.length() > 0) {
                                satabase.EmptyData();
                                for (int x = 0; x < Permission.length(); x++) {
                                    JSONObject ob = Permission.getJSONObject(x);
                                    SystemPermission mPremession = new SystemPermission();
                                    mPremession.setCreate(ob.getBoolean("Create"));
                                    mPremession.setDelete(ob.getBoolean("Delete"));
                                    mPremession.setScreensId(ob.getInt("ScreansId"));
                                    mPremession.setUpdate(ob.getBoolean("Update"));
                                    mPremession.setView(ob.getBoolean("View"));
                                    satabase.InsertData(String.valueOf(mPremession.isCreate()),
                                            String.valueOf(mPremession.isDelete()),
                                            String.valueOf(mPremession.isView()),
                                            String.valueOf(mPremession.isUpdate()),
                                            String.valueOf(mPremession.getScreensId()));
                                    mabi3atPermissions.add(mPremession);
                                }
                            }

//                    sysCursor = satabase.ShowData();
//                    mCursor = systemDatabase.ShowData();
//                    while (sysCursor.moveToNext()){
//                        Log.e("0",sysCursor.getString(0));
//                        Log.e("1",sysCursor.getString(1));
//                        Log.e("2",sysCursor.getString(2));
//                        Log.e("3",sysCursor.getString(3));
//                        Log.e("4",sysCursor.getString(4));
//                    }
//                    Log.e("SSSSSSSSSSSSSSS","TTTTTTTTTTTTTtttt");
//                    while (mCursor.moveToNext()){
//                        Log.e("0",mCursor.getString(0));
//                        Log.e("1",mCursor.getString(1));
//                        Log.e("2",mCursor.getString(2));
//                        Log.e("3",mCursor.getString(3));
//                        Log.e("4",mCursor.getString(4));
//                        Log.e("5",mCursor.getString(5));
//                        Log.e("6",mCursor.getString(6));
//                        Log.e("7",mCursor.getString(7));
//                        Log.e("8",mCursor.getString(8));
//                        Log.e("9",mCursor.getString(9));
//                        Log.e("10",mCursor.getString(10));
//                        Log.e("11",mCursor.getString(11));
//                        Log.e("12",mCursor.getString(12));
//                    }

                            Intent intent = new Intent(Temp.this, Home.class);
                            intent.putExtra("fragment", "mabi3at");
                            startActivity(intent);
                            Temp.this.finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent intent = new Intent(Temp.this, Temp.class);
                        intent.putExtra("fragment", "mabi3at");
                        startActivity(intent);

//                        LayoutInflater inflater = getLayoutInflater();
//                        View layout = inflater.inflate(R.layout.toast_warning,
//                                (ViewGroup) findViewById(R.id.lay));
//
//                        TextView text = (TextView) layout.findViewById(R.id.txt);
//
//                        if (error instanceof ServerError)
//                            text.setText("خطأ فى الاتصال بالخادم");
//                        else if (error instanceof TimeoutError)
//                            text.setText("خطأ فى مدة الاتصال");
//                        else if (error instanceof NetworkError)
//                            text.setText("شبكه الانترنت ضعيفه حاليا");
//
//                        Toast toast = new Toast(getApplicationContext());
//                        toast.setGravity(Gravity.BOTTOM, 0, 0);
//                        toast.setDuration(Toast.LENGTH_LONG);
//                        toast.setView(layout);
//                        toast.show();
                    }
                });
                queue.add(request);
            }
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//    }
}
