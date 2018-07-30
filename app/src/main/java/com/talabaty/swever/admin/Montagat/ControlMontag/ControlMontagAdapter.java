package com.talabaty.swever.admin.Montagat.ControlMontag;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlMontagAdapter extends RecyclerView.Adapter<ControlMontagAdapter.Vholder> {

    List<ControlMontagModel> models;
    Intent intent;

    Context context;
    FragmentManager fragmentManager;

    public ControlMontagAdapter(List<ControlMontagModel> models, Context context) {
        this.models = models;
        this.context = context;
        intent = new Intent(context, Mabi3atNavigator.class);
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.control_montag_row_item, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.id.setText(models.get(position).getNum()+"");
        holder.name.setText(models.get(position).getName()+"");
        holder.date.setText(models.get(position).getInsertDate()+"");
        holder.num.setText(models.get(position).getId()+"");
        holder.time.setText(models.get(position).getInsertTime()+"");
        holder.amount.setText(models.get(position).getAmount()+"");
        holder.price.setText(models.get(position).getSellPrice()+"");
        holder.emergency_amount.setText(models.get(position).getCriticalQuantity()+"");

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Home.class);
                //Todo: Send Data To Home.java
                intent.putExtra("fragment", "edit_control");
                ControlMontagModel model = new ControlMontagModel();
                model.setId(models.get(position).getNum());
                model.setName(models.get(position).getName());
                model.setBuyPrice(models.get(position).getBuyPrice());
                model.setSellPrice(models.get(position).getSellPrice());
                model.setSummary(models.get(position).getSummary());
                model.setDescription(models.get(position).getDescription());
                model.setNotes(models.get(position).getNotes());
                model.setShop_Id(models.get(position).getShop_Id());
                model.setUserId(models.get(position).getUserId());
                model.setEditUserId(models.get(position).getEditUserId());
                model.setCriticalQuantity(models.get(position).getCriticalQuantity());
                model.setAmount(models.get(position).getAmount());
                model.setSampleCatogoriesId(models.get(position).getSampleCatogoriesId());
                model.setSize(models.get(position).getSize());
                model.setColor(models.get(position).getColor());
                model.setGallary(models.get(position).getGallary());
                intent.putExtra("Class", model);
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMontages(models.get(position).getNum());
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {

        TextView id, num, name, time, date, emergency_amount, price, amount;
        ImageButton edit, delete;

        public Vholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            id = itemView.findViewById(R.id.id);
            num = itemView.findViewById(R.id.num);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            emergency_amount = itemView.findViewById(R.id.emergencyammount);
            price = itemView.findViewById(R.id.price);
            amount = itemView.findViewById(R.id.amount);

        }

    }

    private void loadMontages(final int id){
        final ProgressDialog loading = ProgressDialog.show(context, "Updating...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sellsapi.sweverteam.com/sampleproduct/Delete",
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Data: ", s);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
//                        Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        if (volleyError instanceof ServerError)
                            Log.e("Error: ", "Server Error");
                        else if (volleyError instanceof TimeoutError)
                            Log.e("Error: ", "Timeout Error");
                        else if (volleyError instanceof NetworkError)
                            Log.e("Error: ", "Bad Network");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("Id",id+"");
                return map;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
