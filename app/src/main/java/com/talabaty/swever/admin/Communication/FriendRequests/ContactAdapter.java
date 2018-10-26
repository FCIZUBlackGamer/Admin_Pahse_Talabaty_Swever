package com.talabaty.swever.admin.Communication.FriendRequests;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import com.talabaty.swever.admin.Communication.Contacts.ContactItem;
import com.talabaty.swever.admin.Communication.Contacts.FragmentContactItem;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.Vholder> {

    List<ContactItem> contactItems;

    Context context;
    FragmentManager fragmentManager;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;


    public ContactAdapter(List<ContactItem> contactItems, Context context) {
        this.contactItems = contactItems;
        this.context = context;
        loginDatabae = new LoginDatabae(context);
        cursor = loginDatabae.ShowData();
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.freindrequest_contact_item,parent,false);
        fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.contact_name.setText(contactItems.get(position).getName());
        if (contactItems.get(position).getImage() != null) {
            Picasso.with(context)
                    .load(contactItems.get(position).getImage())
                    .into(holder.contact_image);
        }
        holder.contact_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at,new FragmentContactItem()).addToBackStack("FragmentContactItem").commit();
            }
        });

        holder.contact_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction().replace(R.id.frame_mabi3at,new FragmentContactItem()).addToBackStack("FragmentContactItem").commit();
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept(Integer.parseInt(contactItems.get(position).getId()), position);
            }
        });
        
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reject(Integer.parseInt(contactItems.get(position).getId()),shopid, position);
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return contactItems.size();
    }

    public class Vholder extends RecyclerView.ViewHolder{
        ImageView contact_image;
        TextView contact_name;
        Button accept, reject;
        public Vholder(View itemView) {
            super(itemView);
            contact_image = itemView.findViewById(R.id.contact_image);
            contact_name = itemView.findViewById(R.id.contact_name);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
        }

    }

    private void accept(final int id, final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("جارى تنفيذ العمليه ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/Shops/AddShopAccept",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Response",response);
                        if (response.equals("\"Success\"")) {

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_info,null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("تمت تنفيذ العملية");

                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                            contactItems.remove(position);
                            notifyItemRemoved(position);

                        } else {

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_error,null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("حدث خطأ اثناء اجراء العمليه");

                            Toast toast = new Toast(context);
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
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.toast_warning,null);

                TextView text = (TextView) layout.findViewById(R.id.txt);

                if (error instanceof ServerError)
                    text.setText("خطأ فى الاتصال بالخادم");
                else if (error instanceof TimeoutError)
                    text.setText("خطأ فى مدة الاتصال");
                else if (error instanceof NetworkError)
                    text.setText("شبكه الانترنت ضعيفه حاليا");

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("Id", id);
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
//        Volley.newRequestQueue(context).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
    }
    
    private void reject(final int id , final int ShopId, final int position) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("جارى تنفيذ العمليه ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/Shops/RemoveShop",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("Response",response);
                        if (response.equals("\"Success\"")) {

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_info,null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("تمت تنفيذ العملية");

                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                            contactItems.remove(position);
                            notifyItemRemoved(position);

                        } else {

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_error,null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("حدث خطأ اثناء اجراء العمليه");

                            Toast toast = new Toast(context);
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
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.toast_warning,null);

                TextView text = (TextView) layout.findViewById(R.id.txt);

                if (error instanceof ServerError)
                    text.setText("خطأ فى الاتصال بالخادم");
                else if (error instanceof TimeoutError)
                    text.setText("خطأ فى مدة الاتصال");
                else if (error instanceof NetworkError)
                    text.setText("شبكه الانترنت ضعيفه حاليا");

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("ShopId", ShopId);
                hashMap.put("Id", id);
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
//        Volley.newRequestQueue(context).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
