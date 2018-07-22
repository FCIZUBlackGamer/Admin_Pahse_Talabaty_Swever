package com.talabaty.swever.admin.Mabi3at.ReturnedTalabat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.talabaty.swever.admin.DetailsModel;
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.Options.Details.DetailsAdapter;
import com.talabaty.swever.admin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReturnedTalabatAdapter extends RecyclerView.Adapter<ReturnedTalabatAdapter.Vholder> {

    Context context;
    List<Talabat> talabats;

    Button submit;
    ImageButton close;
    View details, message, rejection, transfer;

    Button message_send;
    EditText message_title, message_content;
    Spinner message_type, message_template;
    Spinner TO, to;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<DetailsModel> detailsModels;
    Button first, last, next, prev;
    int temp_first, temp_last, current;
    ArrayList<String> refuseOptions, indexOfrefuseOptions;
    ArrayList<String> CountryList, indexOfCountryList;
    ArrayList<String> EmpoyeeList, indexOfEmpoyeeList;
    ArrayList<String> MessageList, indexOfMessageList;

    public ReturnedTalabatAdapter(Context context, List<Talabat> talabats, int temp_first, int temp_last) {
        this.context = context;
        this.talabats = talabats;
        this.temp_last = temp_last;
        this.temp_first = temp_first;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.returned_talabat_row_item, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {


        holder.id.setText(talabats.get(position).getId());
        holder.name.setText(talabats.get(position).getName());
        holder.phone.setText(talabats.get(position).getPhone());
        holder.date.setText(talabats.get(position).getEstlam_date());
        holder.time.setText(talabats.get(position).getEstlam_time());
        holder.num.setText(talabats.get(position).getNum());
        holder.address.setText(talabats.get(position).getAddress());
        holder.return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accept(talabats.get(position).getNum());
            }
        });



        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                details = inflater.inflate(R.layout.dialog_details_talabat, null);
                submit = details.findViewById(R.id.done);
                close = details.findViewById(R.id.close);

                final TextView num_order = details.findViewById(R.id.order_num);
                final EditText total = details.findViewById(R.id.total);
                num_order.setText(talabats.get(position).getNum());

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recyclerView = (RecyclerView) details.findViewById(R.id.details_rec);
                recyclerView.setLayoutManager(layoutManager);
                detailsModels = new ArrayList<>();

                first = details.findViewById(R.id.first);
                next = details.findViewById(R.id.next);
                prev = details.findViewById(R.id.prev);
                last = details.findViewById(R.id.last);

                first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFLData(-1, num_order, total);
                        num_order.setText(talabats.get(position).getNum());
                    }
                });
                last.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFLData(-2, num_order, total);
                        num_order.setText(talabats.get(position).getNum());
                    }
                });
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData(Integer.parseInt(talabats.get(position).getNum()) + 1, "2", num_order, total);
//                        num_order.setText((Integer.parseInt(talabats.get(position).getNum())+1)+"");
                    }
                });
                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData(Integer.parseInt(talabats.get(position).getNum()) - 1, "1", num_order, total);
//                        num_order.setText((Integer.parseInt(talabats.get(position).getNum())-1)+"");
                    }
                });
                loadData(Integer.parseInt(talabats.get(position).getNum()), "0", num_order, total);

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("التفاصيل")
                        .setCancelable(false)
                        .setView(details)
                        .setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do Nothing
                                clearDetailView();
                                dialog.dismiss();
                            }
                        });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setLayout(1200, 800);

            }
        });


        holder.start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                message = inflater.inflate(R.layout.dialog_message_talabat, null);

                message_type = message.findViewById(R.id.messagetype);
                message_template = message.findViewById(R.id.message_type);
                message_title = message.findViewById(R.id.messagetitle);
                message_content = message.findViewById(R.id.messagecontent);
                message_send = message.findViewById(R.id.send);
                close = message.findViewById(R.id.close);

                loadSpinnerTemplate();

                message_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (message_type.getSelectedItem().toString().equals("الكل")) {

                            message_title.setVisibility(View.VISIBLE);
                            message_content.setVisibility(View.VISIBLE);

                        } else if (message_type.getSelectedItem().toString().equals("رساله نصيه")) {

                            message_title.setVisibility(View.GONE);
                            message_content.setVisibility(View.VISIBLE);
                            message_send.setText("إرسال");
//                            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
//                                message_send.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_shape));
//                            } else {
//                                message_send.setBackground(ContextCompat.getDrawable(context, R.drawable.button_shape));
//                            }
                        } else if (message_type.getSelectedItem().toString().equals("رساله عبر الإيميل")) {
                            message_title.setVisibility(View.VISIBLE);
                            message_content.setVisibility(View.VISIBLE);
                            message_send.setText("إرسال");
//                            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
//                                message_send.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_shape));
//                            } else {
//                                message_send.setBackground(ContextCompat.getDrawable(context, R.drawable.button_shape));
//                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("مراسله")
                        .setCancelable(false)
                        .setView(message)
                        .setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do Nothing
                                clearMessageView();
                                dialog.dismiss();
                            }
                        });
                final AlertDialog dialog2 = builder.create();
                dialog2.show();
                dialog2.getWindow().setLayout(1200, 800);

                closeMessage(dialog2);
                message_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitMessage(dialog2, message_type.getSelectedItem().toString(), position);
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return talabats.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        Button start, show, return_back;
        TextView id, num, address, name, phone, date, time;

        public Vholder(View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.start);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.client_name);
            num = itemView.findViewById(R.id.num);
            address = itemView.findViewById(R.id.client_city);
            show = itemView.findViewById(R.id.show);
            phone = itemView.findViewById(R.id.client_phone);
            return_back = itemView.findViewById(R.id.return_back);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }

    }

    private void submitMessage(AlertDialog dialog, String c, int s) {

        if (message_type.getSelectedItem().toString().equals("الكل")) {

//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("message/rfc822");
//            intent.putExtra(Intent.EXTRA_EMAIL, "momen.shahen2020@gmail.com");
//            intent.putExtra(Intent.EXTRA_SUBJECT, message_title.getText().toString());
//            intent.putExtra(Intent.EXTRA_TEXT, message_content.getText().toString());
//            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + talabats.get(s).getPhone()));
//            intent1.putExtra("sms_body", message_content.getText().toString());
//            try {
//                context.startActivity(Intent.createChooser(intent, "إرسال عبر ..."));
//                context.startActivity(Intent.createChooser(intent1, "إرسال عبر ..."));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(context, "لا يتواجد اى بريد الكترونى هنا!", Toast.LENGTH_SHORT).show();
//            }

            submitMessage(message_content.getText().toString(),message_title.getText().toString(),message_template.getSelectedItem().toString(),1);

        } else if (message_type.getSelectedItem().toString().equals("رساله نصيه")) {

//            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + talabats.get(s).getPhone()));
//            intent1.putExtra("sms_body", message_content.getText().toString());
//            try {
//                context.startActivity(Intent.createChooser(intent1, "إرسال عبر ..."));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(context, "لا يتواجد اى بريد الكترونى هنا!", Toast.LENGTH_SHORT).show();
//            }
//
//        } else if (message_type.getSelectedItem().toString().equals("رساله عبر الإيميل")) {
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.setType("message/rfc822");
//            intent.putExtra(Intent.EXTRA_EMAIL, "momen.shahen2020@gmail.com");
//            intent.putExtra(Intent.EXTRA_SUBJECT, message_title.getText().toString());
//            intent.putExtra(Intent.EXTRA_TEXT, message_content.getText().toString());
//            try {
//                context.startActivity(Intent.createChooser(intent, "إرسال عبر ..."));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(context, "لا يتواجد اى بريد الكترونى هنا!", Toast.LENGTH_SHORT).show();
//            }
            submitMessage(message_content.getText().toString(),message_title.getText().toString(),message_template.getSelectedItem().toString(),3);

        }else if (message_type.getSelectedItem().toString().equals("رساله عبر الإيميل")){
            submitMessage(message_content.getText().toString(),message_title.getText().toString(),message_template.getSelectedItem().toString(),2);
        }

        clearMessageView();
        dialog.dismiss();

    }

    private void submitMessage(final String Mes, final String sub, final String Res, final int option) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.sweverteam.com/Send/Send",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        if (response.equals("\"Success\"")) {
                            Toast toast = Toast.makeText(context, "تمت تنفيذ العملية", Toast.LENGTH_SHORT);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                            v.setTextColor(Color.GREEN);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(context, "حدث خطأ اثناء اجراء العمليه", Toast.LENGTH_SHORT);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                            v.setTextColor(Color.RED);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText(context, "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(context, "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(context, "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("Mes", Mes);
                hashMap.put("Res", Res + "");
                hashMap.put("options", option + "");
                hashMap.put("Sub", sub + "");
                hashMap.put("UserId", "5");
                return hashMap;
            }
        };
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);

    }

    private void closeDetail(final Dialog dialog) {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDetailView();
                dialog.dismiss();
            }
        });
    }

    private void closeReject(final Dialog dialog) {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRejectView();
                dialog.dismiss();
            }
        });
    }

    private void closeMessage(final Dialog dialog) {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearMessageView();
                dialog.dismiss();
            }
        });
    }

    private void submitDetail(final Dialog dialog) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDetailView();
                dialog.dismiss();
            }
        });
    }

    private void submitReject(final Dialog dialog) {
        message_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRejectView();
                dialog.dismiss();
            }
        });
    }


    private void clearDetailView() {
        if (details != null) {
            ViewGroup parent = (ViewGroup) details.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }

    private void clearRejectView() {
        if (rejection != null) {
            ViewGroup parent = (ViewGroup) rejection.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }

    private void clearMessageView() {
        if (message != null) {
            ViewGroup parent = (ViewGroup) message.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }

    private int loadData(final int item, final String state, final TextView c, final TextView d) {

        int x = 0;
        final int size = detailsModels.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                detailsModels.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, size);
        }
        if (state.equals("1")) {
            if (current <= temp_first) {
                Toast.makeText(context, "هذا أول عنصر فى القائمه الحاليه", Toast.LENGTH_LONG).show();
                x = -1;
            } else {
                x = 0;
            }
        } else if (state.equals("2")) {
            if (current >= temp_last) {
                Toast.makeText(context, "هذا أخر عنصر فى القائمه الحاليه", Toast.LENGTH_LONG).show();
                x = -1;
            } else {
                x = 0;
            }
        }

        if (x != -1) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("جارى تحميل البيانات ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.sweverteam.com/order/PreCancelDet",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();
                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray array = object.getJSONArray("PreCancelDet");
                                for (int x = 0; x < array.length(); x++) {
                                    JSONObject object1 = array.getJSONObject(x);
                                    DetailsModel model = new DetailsModel(
                                            object1.getString("SampleProductId"),
                                            object1.getString("SampleProductName"),
                                            "علبه",
                                            object1.getString("Amount")
                                    );
                                    d.setText(Integer.parseInt(d.getText().toString())+ Integer.parseInt(object1.getString("Amount"))+"");
                                    detailsModels.add(model);
                                    c.setText(object1.getString("OrderId"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            adapter = new DetailsAdapter(context, detailsModels);
                            recyclerView.setAdapter(adapter);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    if (error instanceof ServerError)
                        Toast.makeText(context, "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                    else if (error instanceof NetworkError)
                        Toast.makeText(context, "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
                    else if (error instanceof TimeoutError)
                        Toast.makeText(context, "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    HashMap hashMap = new HashMap();
                    hashMap.put("ShopId", "3");
                    hashMap.put("UserId", "5");
                    hashMap.put("Id", item + "");
                    return hashMap;
                }
            };
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    2,  // maxNumRetries = 2 means no retry
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(stringRequest);
        }
        return x;
    }

    private int loadFLData(final int item, final TextView c, final TextView d) {

        int x = 0;

        final int size = detailsModels.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                detailsModels.remove(0);
            }
            adapter.notifyItemRangeRemoved(0, size);
        }

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.sweverteam.com/order/PreCancelDet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {

                            int amount = 0;
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("PreCancelDet");
                            for (int x = 0; x < array.length(); x++) {
                                JSONObject object1 = array.getJSONObject(x);
                                DetailsModel model = new DetailsModel(
                                        object1.getString("SampleProductId"),
                                        object1.getString("SampleProductName"),
                                        "علبه",
                                        object1.getString("Amount")
                                );
                                detailsModels.add(model);
                                amount += Integer.parseInt(object1.getString("Amount"));

                                c.setText(object1.getString("OrderId"));
                            }
                            d.setText(amount+"");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new DetailsAdapter(context, detailsModels);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText(context, "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(context, "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(context, "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("ShopId", "3");
                hashMap.put("UserId", "5");
                hashMap.put("Id", item + "");
                return hashMap;
            }
        };
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
        return x;
    }

    private void loadSpinnerData() {

        refuseOptions = new ArrayList<>();
        indexOfrefuseOptions = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.sweverteam.com/order/SelectRefuse", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("RejectReasons");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.getString("Name");
                        String id = jsonObject1.getString("Id");
                        indexOfrefuseOptions.add(id);
                        refuseOptions.add(name);

                    }

                    message_type.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, refuseOptions));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError)
                    Toast.makeText(context, "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(context, "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(context, "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadSpinnerTemplate() {

        MessageList = new ArrayList<>();
        indexOfMessageList = new ArrayList<>();

        MessageList.add("--اختر--");
        indexOfMessageList.add("0");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.sweverteam.com/order/SelectSendsMessages", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("SendsMessages");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.getString("Name");
                        String id = jsonObject1.getString("Id");
                        indexOfMessageList.add(id);
                        MessageList.add(name);

                    }

                    message_template.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, MessageList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError)
                    Toast.makeText(context, "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(context, "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(context, "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void accept(final String id){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("جارى تنفيذ العمليه ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.sweverteam.com/order/PreparationCancelToNewOrder",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        if (response.equals("\"Success\"")) {
                            Toast toast = Toast.makeText(context, "تمت تنفيذ العملية", Toast.LENGTH_SHORT);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                            v.setTextColor(Color.GREEN);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(context, "حدث خطأ اثناء اجراء العمليه", Toast.LENGTH_SHORT);
                            TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                            v.setTextColor(Color.RED);
                            toast.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof ServerError)
                    Toast.makeText(context, "خطأ إثناء الاتصال بالخادم", Toast.LENGTH_SHORT).show();
                else if (error instanceof NetworkError)
                    Toast.makeText(context, "خطأ فى شبكه الانترنت", Toast.LENGTH_SHORT).show();
                else if (error instanceof TimeoutError)
                    Toast.makeText(context, "خطأ فى مده الانتظار", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("Id", id);
                return hashMap;
            }
        };
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                2,  // maxNumRetries = 2 means no retry
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(stringRequest);
    }
}
