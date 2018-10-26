package com.talabaty.swever.admin.Montagat.ControlBaseFood_Additions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.talabaty.swever.admin.Montagat.ControlMontag.ControlMontagModel;
import com.talabaty.swever.admin.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlBaseFood_AdditionsAdapter extends RecyclerView.Adapter<ControlBaseFood_AdditionsAdapter.Vholder> {

    List<ControlMontagModel> models;
    Intent intent;
    int Type;
    String DeleteLink;
    View view;

    Context context;
    FragmentManager fragmentManager;

    public ControlBaseFood_AdditionsAdapter(List<ControlMontagModel> models, Context context, int type) {
        this.models = models;
        this.context = context;
        Type = type;
        intent = new Intent(context, Home.class);
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_control_additions_baefood, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.id.setText(models.get(position).getNum()+"");
        holder.name.setText(models.get(position).getName()+"");
        holder.num.setText(models.get(position).getId()+"");
        holder.price.setText(models.get(position).getSellPrice()+"");

        if (Type == 3) {
            intent.putExtra("fragment", "edit_control3");
            DeleteLink = "http://sellsapi.rivile.com/BaseFood/delete";
        }else {
            intent.putExtra("fragment", "edit_control4");
            DeleteLink = "http://sellsapi.rivile.com/Additions/delete";
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, Home.class);
                //Todo: Send Data To Home.java

                ControlMontagModel model = new ControlMontagModel();
                model.setId(models.get(position).getId());
                model.setName(models.get(position).getName());
                model.setSellPrice(models.get(position).getSellPrice());
                model.setShop_Id(models.get(position).getShop_Id());
                model.setGallary(models.get(position).getGallary());
                intent.putExtra("Class", model);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMontages(models.get(position).getId(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {

        TextView id, num, name, price;
        ImageButton edit, delete;

        public Vholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            id = itemView.findViewById(R.id.id);
            num = itemView.findViewById(R.id.num);
            price = itemView.findViewById(R.id.price);

        }

    }

    private void deleteMontages(final int id, final int position){
        Log.e("Id", ""+id);
        final ProgressDialog loading = ProgressDialog.show(context, "Updating...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DeleteLink,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        Log.e("Data: ", s);
                        if (s.equals("\"false\"")) {

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_info, null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("لا يمكن مسح المنتج لوجود طلبات معلقه به");

                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                        } else if (s.equals("\"Success\"")){

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_info, null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("تم مسح المنتج");

                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();

                            models.remove(position);
                            notifyItemRemoved(position);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
//                        Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View layout = inflater.inflate(R.layout.toast_warning,null);

                        TextView text = (TextView) layout.findViewById(R.id.txt);

                        if (volleyError instanceof ServerError)
                            text.setText("خطأ فى الاتصال بالخادم");
                        else if (volleyError instanceof TimeoutError)
                            text.setText("خطأ فى مدة الاتصال");
                        else if (volleyError instanceof NetworkError)
                            text.setText("شبكه الانترنت ضعيفه حاليا");

                        Toast toast = new Toast(context);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("token", "bKPNOJrob8x");
                hashMap.put("Id",id+"");
                return hashMap;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
