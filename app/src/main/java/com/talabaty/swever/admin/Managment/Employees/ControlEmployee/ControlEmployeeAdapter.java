package com.talabaty.swever.admin.Managment.Employees.ControlEmployee;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Managment.Employees.Employee;
import com.talabaty.swever.admin.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlEmployeeAdapter extends RecyclerView.Adapter<ControlEmployeeAdapter.Vholder> {

    List<Employee> employeeList;
    List<String> jobKind;
    List<String> jobNatural;
    List<String> jobPrivilage;
    List<String> jobManage;
    Intent intent;

    Context context;
    FragmentManager fragmentManager;

    public ControlEmployeeAdapter(List<Employee> employeeList, List<String> jobKind,List<String> jobNatural,List<String> jobPrivilage
            ,List<String> jobManage,   Context context) {
        this.employeeList = employeeList;
        this.context = context;
        this.jobKind = jobKind;
        this.jobNatural = jobNatural;
        this.jobPrivilage = jobPrivilage;
        this.jobManage = jobManage;
        intent = new Intent(context, Mabi3atNavigator.class);
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.control_employee_row_item, parent, false);
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.id.setText(employeeList.get(position).getId()+"");
        holder.name.setText(employeeList.get(position).getFullName());
        holder.jobNaatural.setText(jobNatural.get(position));
        holder.privilage.setText(jobPrivilage.get(position));
        holder.jobKind.setText(jobKind.get(position));
        holder.manage.setText(jobManage.get(position));
        holder.place_name.setText(employeeList.get(position).getBranchName());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Home.class);
                intent.putExtra("fragment","edit_emp");
                intent.putExtra("Class",employeeList.get(position));
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Delete Action
                deleteEmployee(employeeList.get(position).getId(), position);
            }
        });


    }

    private void deleteEmployee(final int id, final int poition) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.sweverteam.com/Employee/Delete", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equals("\"false\"")) {

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View layout = inflater.inflate(R.layout.toast_error, null);

                    TextView text = (TextView) layout.findViewById(R.id.txt);
                    text.setText("لا يمكن مسح العامل");

                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();


                } else {

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View layout = inflater.inflate(R.layout.toast_info, null);

                    TextView text = (TextView) layout.findViewById(R.id.txt);
                    text.setText("تمت تنفيذ العمليه بنجاح");

                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                    employeeList.remove(poition);
                    notifyItemChanged(poition);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View layout = inflater.inflate(R.layout.toast_warning, null);

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("Id",id+"");
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {

        TextView id, name, jobKind, jobNaatural, privilage, place_name, manage ;
        ImageButton edit, delete;

        public Vholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            id = itemView.findViewById(R.id.id);
            privilage = itemView.findViewById(R.id.duration);
            jobKind = itemView.findViewById(R.id.time);
            jobNaatural = itemView.findViewById(R.id.job_title);
            manage = itemView.findViewById(R.id.manage);
            place_name = itemView.findViewById(R.id.place_name);

        }

    }
}
