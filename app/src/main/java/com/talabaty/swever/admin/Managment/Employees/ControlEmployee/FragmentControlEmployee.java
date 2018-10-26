package com.talabaty.swever.admin.Managment.Employees.ControlEmployee;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Managment.Employees.Employee;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentControlEmployee extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Employee> employeeList;
    List<String> jobKind;
    List<String> jobNatural;
    List<String> jobPrivilage;
    List<String> jobManage;

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_control,container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        recyclerView.setLayoutManager(layoutManager);
        employeeList = new ArrayList<>();
        jobKind = new ArrayList<>();
        jobNatural = new ArrayList<>();
        jobPrivilage = new ArrayList<>();
        jobManage = new ArrayList<>();
        loginDatabae = new LoginDatabae(getActivity());
        cursor = loginDatabae.ShowData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
        ((Mabi3atNavigator) getActivity())
                .setActionBarTitle("عمليات الموظف");

        loadJobTitle(shopid);
    }

    private void loadJobTitle(final int ShopId) {

        if (employeeList.size() > 0) {
            for (int x = 0; x < employeeList.size(); x++) {
                employeeList.remove(x);
                jobKind.remove(x);
                jobNatural.remove(x);
                jobPrivilage.remove(x);
                jobManage.remove(x);
            }
        }
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sellsapi.rivile.com/Employee/list", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    Log.e("Res",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Employee");
                    if (jsonArray.length()>0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Employee employee = new Employee();
                            employee.setId(jsonObject1.getInt("Id"));
                            employee.setFullName(jsonObject1.getString("FullName"));
                            employee.setUserName(jsonObject1.getString("UserName"));
                            employee.setMail(jsonObject1.getString("Mail"));
                            employee.setPassword(jsonObject1.getString("Password"));
                            employee.setEmploymentTypeId(jsonObject1.getInt("EmploymentTypeId"));/***/
                            jobKind.add(jsonObject1.getString("EmployeeType"));
                            employee.setWorkingNaturalId(jsonObject1.getInt("WorkingNaturalId"));/***/
                            jobNatural.add(jsonObject1.getString("WorkingNatural"));
                            employee.setBranchName(jsonObject1.getString("BranchName"));
                            employee.setPhone(jsonObject1.getString("Phone"));
                            employee.setRulesId(jsonObject1.getInt("RulesId"));/***/
                            jobPrivilage.add(jsonObject1.getString("RuleName"));
                            employee.setManagmentId(jsonObject1.getInt("ManagmentId"));/***/
                            jobManage.add(jsonObject1.getString("Managment"));
                            employee.setUserId(jsonObject1.getInt("UserId")); //Todo: UserId
                            employee.setShopId(jsonObject1.getInt("ShopId")); //Todo: ShopId

                            employee.setWorkingStart(jsonObject1.getString("WorkingStart"));
                            employee.setWorkingEnd(jsonObject1.getString("WorkingEnd"));
                            employee.setCreditLimit(jsonObject1.getInt("CreditLimit"));
                            employee.setBalance(jsonObject1.getInt("Balance"));
                            employee.setSalary(jsonObject1.getInt("Salary"));
                            employee.setSalaryBonus(jsonObject1.getString("SalaryBonus"));
                            employee.setTransportationAllowance(jsonObject1.getInt("TransportationAllowance"));
                            employee.setHousingAllowance(jsonObject1.getInt("HousingAllowance"));
                            employee.setInsuranceAllowance(jsonObject1.getInt("InsuranceAllowance"));
                            employee.setInsuranceNum(jsonObject1.getInt("InsuranceNum"));
                            employee.setInfectionAllowance(jsonObject1.getInt("InfectionAllowance"));
                            employee.setWorkingHours(jsonObject1.getInt("WorkingHours"));
                            employee.setWorkingHoursBonus(jsonObject1.getInt("WorkingHoursBonus"));
                            employee.setImageCard(jsonObject1.getString("ImageCard"));
                            employee.setPhoto(jsonObject1.getString("Photo"));
                            employeeList.add(employee);

                        }
                        adapter = new ControlEmployeeAdapter(employeeList, jobKind, jobNatural, jobPrivilage
                                , jobManage, getActivity());
                        recyclerView.setAdapter(adapter);

                    }else {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_info,
                                (ViewGroup) getActivity().findViewById(R.id.lay));

                        TextView text = (TextView) layout.findViewById(R.id.txt);
                        text.setText("لا توجد بيانات موظفين");

                        Toast toast = new Toast(getActivity());
                        toast.setGravity(Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("ShopId",ShopId+"");
                map.put("token","bKPNOJrob8x");
                return map;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
