package com.talabaty.swever.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button login;
    LoginDatabae loginDatabae;
    Cursor cursor;
    ProgressDialog progressDialog;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        loginDatabae = new LoginDatabae(this);
        cursor = loginDatabae.ShowData();
        while (cursor.moveToNext()) {
            Log.e("Id",cursor.getString(0));
            Log.e("name",cursor.getString(1));
            Log.e("userId",cursor.getString(2));
            Log.e("shopId",cursor.getString(3));
            Log.e("type",cursor.getString(4));
            Log.e("image",cursor.getString(5));
            Log.e("cat",cursor.getString(6));
            Log.e("Rule",cursor.getString(7));
            if (cursor.getString(4).equals("1")) {
                Intent intent = new Intent(Login.this, Temp.class);
                intent.putExtra("fragment", "mabi3at");
                startActivity(intent);
                finish();
            }
        }
        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Connecting To Server ... ");
                final String name = email.getText().toString();
                final String passwor = password.getText().toString();
                if (validate(name, passwor)) {
                    progressDialog.show();

                    RequestQueue queue = Volley.newRequestQueue(Login.this);
                    StringRequest request = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/Login/Login", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals("\"fail\"")) {
                                progressDialog.dismiss();

                                YoYo.with(Techniques.Tada)
                                        .duration(700)
                                        .repeat(1)
                                        .playOn(findViewById(R.id.log));

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_error,
                                        (ViewGroup) findViewById(R.id.lay));

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("أسم مستخدم أو كلمة مرور خطأ");

                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();

                            } else {
                                progressDialog.dismiss();

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_info,
                                        (ViewGroup) findViewById(R.id.lay));

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("مرحبا");

                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();

                                try {
                                    JSONObject object = new JSONObject(response);
                                    //JSONArray array = object.getJSONArray("user");
                                    JSONObject object1 = object.getJSONObject("users");
                                    int cat = object.getInt("cat");
                                    int rule = object1.getInt("Rule_Id");

                                    loginDatabae.UpdateData("1", object1.getString("UserName"), object1.getString("Id"), object1.getString("Shop_Id"), "1", "http://selltlbaty.rivile.com/" + object1.getString("Photo"), cat+"", rule+"");

                                    Intent intent = new Intent(Login.this, Temp.class);
                                    startActivity(intent);
                                    finish();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.toast_warning,
                                    (ViewGroup) findViewById(R.id.lay));

                            TextView text = (TextView) layout.findViewById(R.id.txt);

                            if (error instanceof ServerError)
                                text.setText("خطأ فى الاتصال بالخادم");
                            else if (error instanceof TimeoutError)
                                text.setText("خطأ فى مدة الاتصال");
                            else if (error instanceof NetworkError)
                                text.setText("شبكه الانترنت ضعيفه حاليا");

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Name", name);
                            params.put("Password", passwor);
                            params.put("token", "bKPNOJrob8x");

                            return params;
                        }
                    };
                    queue.add(request);


                }
            }
        });
    }

    private boolean validate(String name, String pass){
        boolean res = true;
        if (name.length()<1){
            email.setError("ادخل اسم مستخدم");
            res = false;
        }
        if (pass.isEmpty() || pass.equals(" ")){
            password.setError("كلمه مرور فارغه");
            res = false;
        }
        return res;
    }

}
