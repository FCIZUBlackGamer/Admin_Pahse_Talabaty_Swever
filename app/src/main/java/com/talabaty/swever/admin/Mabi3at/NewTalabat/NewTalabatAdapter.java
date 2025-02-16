package com.talabaty.swever.admin.Mabi3at.NewTalabat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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
import com.talabaty.swever.admin.DetailsModel;
import com.talabaty.swever.admin.LoginDatabae;
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.Options.Details.DetailsAdapter;
import com.talabaty.swever.admin.R;
import com.talabaty.swever.admin.Mabi3atDatabase;
import com.talabaty.swever.admin.Mabi3atPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTalabatAdapter extends RecyclerView.Adapter<NewTalabatAdapter.Vholder> {

    Context context;
    List<Talabat> talabats;

    Button submit;
    ImageButton close;
    View details, message, reject, transfer;

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

    LoginDatabae loginDatabae;
    Cursor cursor;
    int userid, shopid;

    View view;
    int position;

    Mabi3atDatabase systemDatabase;
    Cursor sysCursor;
    Mabi3atPermission permission;

    public NewTalabatAdapter(Context context, List<Talabat> talabats, int temp_first, int temp_last) {
        this.context = context;
        this.talabats = talabats;
        this.temp_last = temp_last;
        this.temp_first = temp_first;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_talabat_row_item, parent, false);
        loginDatabae = new LoginDatabae(context);
        cursor = loginDatabae.ShowData();
        while (cursor.moveToNext()) {
            userid = Integer.parseInt(cursor.getString(2));
            shopid = Integer.parseInt(cursor.getString(3));

        }
        systemDatabase = new Mabi3atDatabase(context);
        sysCursor = systemDatabase.ShowData();

        permission = new Mabi3atPermission();
//        while (sysCursor.moveToNext()) {
//            permission.setView(Boolean.valueOf(sysCursor.getString(1)));
//            permission.setDetalis(Boolean.valueOf(sysCursor.getString(2)));
//            permission.setSends(Boolean.valueOf(sysCursor.getString(3)));
//            permission.setRefuse(Boolean.valueOf(sysCursor.getString(4)));
//            permission.setPreAndDirect(Boolean.valueOf(sysCursor.getString(5)));
//            permission.setPreCancel(Boolean.valueOf(sysCursor.getString(6)));
//            permission.setAccept(Boolean.valueOf(sysCursor.getString(7)));
//            permission.setPreCancelToNewOrder(Boolean.valueOf(sysCursor.getString(8)));
//            permission.setReceived(Boolean.valueOf(sysCursor.getString(9)));
//            permission.setTransport(Boolean.valueOf(sysCursor.getString(10)));
//            permission.setTransportAccept(Boolean.valueOf(sysCursor.getString(11)));
//            permission.setScreans2Id(Integer.parseInt(sysCursor.getString(12)));
//        }

        while (sysCursor.moveToNext()) {
            if (sysCursor.getString(12).equals("1")) {
                permission.setDetalis(Boolean.valueOf(sysCursor.getString(2)));
                permission.setSends(Boolean.valueOf(sysCursor.getString(3)));
                permission.setRefuse(Boolean.valueOf(sysCursor.getString(4)));
                permission.setPreAndDirect(Boolean.valueOf(sysCursor.getString(5)));
            }
        }

        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int positio) {

        holder.id.setText(talabats.get(positio).getId());
        holder.name.setText(talabats.get(positio).getName());
        holder.phone.setText(talabats.get(positio).getPhone());
        holder.date.setText(talabats.get(positio).getEstlam_date());
        holder.time.setText(talabats.get(positio).getEstlam_time());
        holder.num.setText(talabats.get(positio).getNum());
        holder.address.setText(talabats.get(positio).getAddress());
        current = Integer.parseInt(talabats.get(positio).getNum());

        EmpoyeeList = new ArrayList<>();
        indexOfEmpoyeeList = new ArrayList<>();

        if (!permission.isDetalis()) {
            holder.show.setVisibility(View.GONE);
        }
        if (!permission.isRefuse()) {
            holder.no.setVisibility(View.GONE);
        }
        if (!permission.isPreAndDirect()) {
            holder.redygo.setVisibility(View.GONE);
        }
        if (!permission.isSends()) {
            holder.start.setVisibility(View.GONE);
        }

        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                refuseOptions = new ArrayList<>();
                indexOfrefuseOptions = new ArrayList<>();
                reject = inflater.inflate(R.layout.dialog_reject_talabat, null);

                message_type = reject.findViewById(R.id.messagetype);
                message_content = reject.findViewById(R.id.messagecontent);
                message_send = reject.findViewById(R.id.send);
                close = reject.findViewById(R.id.close);

                loadSpinnerData();

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("رفض")
                        .setCancelable(false)
                        .setView(reject)
                        .setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do Nothing
                                clearRejectView();
                                dialog.dismiss();
                            }
                        });
                final AlertDialog dialog2 = builder.create();
                dialog2.show();
                dialog2.getWindow().setLayout(1200, 800);

                closeReject(dialog2);
                message_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitReject(dialog2, message_type.getSelectedItem().toString(), talabats.get(positio).getNum(), positio);
                    }
                });

            }
        });

        holder.redygo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                transfer = inflater.inflate(R.layout.dialog_taghez_tawgeeh_talabat, null);

                TO = transfer.findViewById(R.id.TO);
                to = transfer.findViewById(R.id.to);
                message_send = transfer.findViewById(R.id.send);
                close = transfer.findViewById(R.id.close);

                CountryList = new ArrayList<>();
                indexOfCountryList = new ArrayList<>();

                loadCountryData();

                TO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (TO.getSelectedItem().toString().equals("الكل")) {
                            to.setVisibility(View.GONE);
                        } else {
                            to.setVisibility(View.VISIBLE);
                            loadEmployeeData(indexOfCountryList.get(CountryList.indexOf(TO.getSelectedItem().toString())));
                            Toast.makeText(context, indexOfCountryList.get(CountryList.indexOf(TO.getSelectedItem().toString())), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("تجهيز وتوجيه")
                        .setCancelable(false)
                        .setView(transfer)
                        .setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do Nothing
                                clearTaghezView();
                                dialog.dismiss();
                            }
                        });
                final AlertDialog dialog2 = builder.create();
                dialog2.show();
                dialog2.getWindow().setLayout(1200, 800);

                closeTaghez(dialog2);
                message_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TO.getSelectedItem().toString().equals("الكل")) {
                            submitTaghez(dialog2, "الكل", Integer.parseInt(talabats.get(positio).getNum()), "0", 0, positio);
                        } else {
                            submitTaghez(dialog2, TO.getSelectedItem().toString(), Integer.parseInt(talabats.get(positio).getNum()), indexOfCountryList.get(CountryList.indexOf(TO.getSelectedItem().toString())), Integer.parseInt(indexOfEmpoyeeList.get(EmpoyeeList.indexOf(to.getSelectedItem().toString()))), positio);
                        }
                    }
                });
            }
        });


        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = positio;
                Log.e("Position", position + "");
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                details = inflater.inflate(R.layout.dialog_details_talabat, null);
                submit = details.findViewById(R.id.done);
                close = details.findViewById(R.id.close);
//
                final TextView num_order = details.findViewById(R.id.order_num);
                final EditText total = details.findViewById(R.id.total);
                try {
                    num_order.setText(talabats.get(position).getNum() + "");

                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView = (RecyclerView) details.findViewById(R.id.details_rec);
                    recyclerView.setLayoutManager(layoutManager);
                }catch (Exception e){

                }
                detailsModels = new ArrayList<>();

                first = details.findViewById(R.id.first);
                next = details.findViewById(R.id.next);
                prev = details.findViewById(R.id.prev);
                last = details.findViewById(R.id.last);
//
//
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
                loadData(Integer.parseInt(talabats.get(positio).getNum()), "0", num_order, total, dialog);
                first.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFLData(-1, num_order, total);
                        num_order.setText(talabats.get(positio).getNum());
                    }
                });
                last.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadFLData(-2, num_order, total);
                        num_order.setText(talabats.get(positio).getNum());
                    }
                });
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Next Out", position + "");
                        if (position < temp_last && position < talabats.size() - 1) {
                            //position = positio;
                            loadData(Integer.parseInt(talabats.get(++position).getNum()), "2", num_order, total, dialog);
                            num_order.setText(talabats.get(position).getNum() + "");
                            Log.e("Next in", position + "");
                        } else {
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
                            View layout = inflater.inflate(R.layout.toast_info, null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("هذا أخر عنصر فى القائمه الحاليه");

                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }

//                        num_order.setText((Integer.parseInt(talabats.get(position).getNum())+1)+"");
                    }
                });
                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Prev Out", position + "");
                        if (0 < position) {
                            Log.e("Prev in", position + "");
                            //position = positio;
                            loadData(Integer.parseInt(talabats.get(--position).getNum()), "1", num_order, total, dialog);
                            num_order.setText(talabats.get(position).getNum() + "");
                        } else {
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                            View layout = inflater.inflate(R.layout.toast_info, null);

                            TextView text = (TextView) layout.findViewById(R.id.txt);
                            text.setText("هذا أول عنصر فى القائمه الحاليه");

                            Toast toast = new Toast(context);
                            toast.setGravity(Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
//                        num_order.setText((Integer.parseInt(talabats.get(position).getNum())-1)+"");
                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        transfer = inflater.inflate(R.layout.dialog_taghez_tawgeeh_talabat, null);

                        TO = transfer.findViewById(R.id.TO);
                        to = transfer.findViewById(R.id.to);
                        message_send = transfer.findViewById(R.id.send);
                        close = transfer.findViewById(R.id.close);

                        CountryList = new ArrayList<>();
                        indexOfCountryList = new ArrayList<>();
                        loadCountryData();


                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("تجهيز وتوجيه")
                                .setCancelable(false)
                                .setView(transfer)
                                .setNegativeButton("اغلاق", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do Nothing
                                        clearTaghezView();
                                        dialog.dismiss();
                                    }
                                });
                        final AlertDialog dialog2 = builder.create();
                        dialog2.show();
                        dialog2.getWindow().setLayout(1200, 800);

                        closeTaghez(dialog2);
                        message_send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TO.getSelectedItem().toString().equals("الكل")) {
                                    submitTaghez(dialog2, "الكل", Integer.parseInt(talabats.get(positio).getNum()), "0", 0, positio);
                                } else {
                                    submitTaghez(dialog2, TO.getSelectedItem().toString(), Integer.parseInt(talabats.get(positio).getNum()), indexOfCountryList.get(CountryList.indexOf(TO.getSelectedItem().toString())), Integer.parseInt(indexOfEmpoyeeList.get(EmpoyeeList.indexOf(to.getSelectedItem().toString()))), positio);
                                }
                            }
                        });
                    }
                });
                closeDetail(dialog);
                submitDetail(dialog);

            }
        });


        holder.start.setOnClickListener(new View.OnClickListener() {
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
                        submitMessage(dialog2, message_type.getSelectedItem().toString(), positio);
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
        Button start, show, no, redygo;
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
            no = itemView.findViewById(R.id.no);
            redygo = itemView.findViewById(R.id.readygo);
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

            submitMessage(message_content.getText().toString(), message_title.getText().toString(), message_template.getSelectedItem().toString(), 1,s);

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
            submitMessage(message_content.getText().toString(), message_title.getText().toString(), message_template.getSelectedItem().toString(), 3,s);

        } else if (message_type.getSelectedItem().toString().equals("رساله عبر الإيميل")) {
            submitMessage(message_content.getText().toString(), message_title.getText().toString(), message_template.getSelectedItem().toString(), 2,s);
        }

        clearMessageView();
        dialog.dismiss();

    }

    private void submitReject(AlertDialog dialog, String s, String num, int position) {
        clearRejectView();
        int rejectId = Integer.parseInt(indexOfrefuseOptions.get(refuseOptions.indexOf(s)));
        int orderId = Integer.parseInt(num);
        submitRefuse(message_content.getText().toString(), orderId, rejectId, position);
//        Toast.makeText(context, "Message: " + message_content.getText().toString() + "\n" +
//                "OrderId: " + orderId + "\n" +
//                "RejectId: " + rejectId, Toast.LENGTH_SHORT).show();
        dialog.dismiss();

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

    private void clearMessageView() {
        if (message != null) {
            ViewGroup parent = (ViewGroup) message.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }

    private void clearRejectView() {
        if (reject != null) {
            ViewGroup parent = (ViewGroup) reject.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
    }

    private void clearTaghezView() {
        if (transfer != null) {
            ViewGroup parent = (ViewGroup) transfer.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
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

    private void submitTaghez(final Dialog dialog, String x, int orderId, String regionId, int employeeId, int position) {
        clearTaghezView();
        if (x.equals("الكل")) {
            submitTaghez(orderId, position);
//            Toast.makeText(context,"OrderId: "+orderId+"\n",Toast.LENGTH_SHORT).show();
        } else {
            submitTaghez(orderId, Integer.parseInt(regionId), employeeId, position);
//            Toast.makeText(context,"EmployeeId: "+employeeId+"\n"+
//                    "OrderId: "+orderId+"\n"+
//                    "RegionId: "+regionId,Toast.LENGTH_SHORT).show();
        }
        dialog.dismiss();

    }

    private void clearDetailView() {
        if (details != null) {
            ViewGroup parent = (ViewGroup) details.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
        }
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

    private void closeTaghez(final Dialog dialog) {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTaghezView();
                dialog.dismiss();
            }
        });
    }

    private void loadData(final int item, final String state, final TextView c, final TextView d, final AlertDialog dialog) {

//        int x = 0;
//
//        if (state.equals("1")){
//            if (current <= temp_first){
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                View layout = inflater.inflate(R.layout.toast_info,null);
//
//                TextView text = (TextView) layout.findViewById(R.id.txt);
//                text.setText("هذا أول عنصر فى القائمه الحاليه");
//
//                Toast toast = new Toast(context);
//                toast.setGravity(Gravity.BOTTOM, 0, 0);
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.setView(layout);
//                toast.show();
//                x = -1;
//            }else {
//                x = 0;
//            }
//        } else if (state.equals("2")){
//            if (current >= temp_last){
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//                View layout = inflater.inflate(R.layout.toast_info,null);
//
//                TextView text = (TextView) layout.findViewById(R.id.txt);
//                text.setText("هذا أخر عنصر فى القائمه الحاليه");
//
//                Toast toast = new Toast(context);
//                toast.setGravity(Gravity.BOTTOM, 0, 0);
//                toast.setDuration(Toast.LENGTH_LONG);
//                toast.setView(layout);
//                toast.show();
//                x = -1;
//            }else {
//                x = 0;
//            }
//        }

//        if (x != -1) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("جارى تحميل البيانات ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/order/NewDet",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Response Details", response);
                            progressDialog.dismiss();
                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray array = object.getJSONArray("NewDet");
                                if (array.length() > 0) {
//                                    final int size = detailsModels.size();
//                                    if (size > 0) {
//                                        for (int i = 0; i < size; i++) {
//                                            detailsModels.remove(0);
//                                        }
//                                        adapter.notifyItemRangeRemoved(0, size);
//                                    }
                                    detailsModels = new ArrayList<>();
                                    for (int x = 0; x < array.length(); x++) {
                                        JSONObject object1 = array.getJSONObject(x);
                                        DetailsModel model = new DetailsModel(
                                                object1.getString("SampleProductId"),
                                                object1.getString("SampleProductName"),
                                                "علبه",
                                                object1.getString("Amount")
                                        );
                                        d.setText(Integer.parseInt(d.getText().toString()) + Integer.parseInt(object1.getString("Amount")) + "");
                                        detailsModels.add(model);
                                        c.setText(object1.getString("OrderId"));
                                    }
                                } else {
                                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                    View layout = inflater.inflate(R.layout.toast_info,null);

                                    TextView text = (TextView) layout.findViewById(R.id.txt);
                                    text.setText("لا توجد بيانات");

                                    Toast toast = new Toast(context);
                                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                                    toast.setDuration(Toast.LENGTH_LONG);
                                    toast.setView(layout);
                                    toast.show();
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
                    hashMap.put("ShopId", shopid + "");
                    Log.e("ShopId", shopid + "");
                    hashMap.put("UserId", userid + "");
                    Log.e("UserId", userid + "");
                    hashMap.put("Id", item + "");
                    Log.e("Id", item + "");
                    hashMap.put("token", "bKPNOJrob8x");
                    return hashMap;
                }
            };
//        Volley.newRequestQueue(getActivity()).add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                    2,  // maxNumRetries = 2 means no retry
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(stringRequest);
//        }

    }

    private int loadFLData(final int item, final TextView c, final TextView d) {

        int x = 0;


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("جارى تحميل البيانات ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/order/NewDet",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {

                            int amount = 0;
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("NewDet");
                            if (array.length() > 0) {

                                final int size = detailsModels.size();
                                if (size > 0) {
                                    for (int i = 0; i < size; i++) {
                                        detailsModels.remove(0);
                                    }
                                    adapter.notifyItemRangeRemoved(0, size);
                                }

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
                                d.setText(amount + "");
                            } else {
                                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                                View layout = inflater.inflate(R.layout.toast_info,null);

                                TextView text = (TextView) layout.findViewById(R.id.txt);
                                text.setText("لا توجد بيانات");

                                Toast toast = new Toast(context);
                                toast.setGravity(Gravity.BOTTOM, 0, 0);
                                toast.setDuration(Toast.LENGTH_LONG);
                                toast.setView(layout);
                                toast.show();
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
                hashMap.put("ShopId", shopid + "");
                Log.e("ShopId", shopid + "");
                hashMap.put("UserId", userid + "");
                Log.e("UserId", userid + "");
                hashMap.put("Id", item + "");
                Log.e("Id", item + "");
                hashMap.put("token", "bKPNOJrob8x");
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/order/SelectRefuse", new Response.Listener<String>() {
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
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/order/SelectSendsMessages", new Response.Listener<String>() {
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
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadCountryData() {

        CountryList = new ArrayList<>();
        indexOfCountryList = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/order/SelectRegion", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    CountryList.add("الكل");
                    indexOfCountryList.add("0");
                    JSONArray jsonArray = jsonObject.getJSONArray("Region");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String name = jsonObject1.getString("Name");
                        String id = jsonObject1.getString("Id");
                        CountryList.add(name);
                        indexOfCountryList.add(id);

                    }

                    TO.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, CountryList));

                    TO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (TO.getSelectedItem().toString().equals("الكل")) {
                                to.setVisibility(View.GONE);
                            } else {
                                to.setVisibility(View.VISIBLE);
                                Log.e("Id",indexOfCountryList.get(CountryList.indexOf(TO.getSelectedItem().toString())));
                                loadEmployeeData(indexOfCountryList.get(CountryList.indexOf(TO.getSelectedItem().toString())));

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadEmployeeData(final String id) {

        if (EmpoyeeList.size() > 0) {
            for (int x = 0; x < EmpoyeeList.size(); x++) {
                EmpoyeeList.remove(x);
                indexOfEmpoyeeList.remove(x);
            }
        }

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.sellsapi.rivile.com/order/SelectCustomers?Id=" + id + "&token=bKPNOJrob8x&ShopId=" + shopid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Customers");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String fname = jsonObject1.getString("CustomerName");
                            String id = jsonObject1.getString("CustomerId");
                            EmpoyeeList.add(fname);
                            indexOfEmpoyeeList.add(id);

                        }
                        to.setVisibility(View.VISIBLE);
                        to.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, EmpoyeeList));
                    } else {
                        to.setVisibility(View.GONE);
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View layout = inflater.inflate(R.layout.toast_warning, null);

                        TextView text = (TextView) layout.findViewById(R.id.txt);

                        text.setText("لا يوجد موظفين");

                        Toast toast = new Toast(context);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Id", id);
                Log.e("Id", id);
                hashMap.put("ShopId", shopid + "");
                Log.e("ShopId", shopid + "");
                hashMap.put("token", "bKPNOJrob8x");
                return hashMap;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void submitRefuse(final String Refuse, final int Id, final int RejectId, final int position) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/order/Refuse",
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
                            talabats.remove(position);
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
                hashMap.put("Refuse", Refuse);
                hashMap.put("Id", Id + "");
                hashMap.put("RejectId", RejectId + "");
                hashMap.put("token", "bKPNOJrob8x");
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

    private void submitMessage(final String Mes, final String sub, final String Res, final int option, final int position) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/Send/Send",
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
                hashMap.put("Mes", Mes);
                hashMap.put("Res", Res + "");
                hashMap.put("options", option + "");
                hashMap.put("Sub", sub + "");
                hashMap.put("UserId", userid + "");
                hashMap.put("ShopId", shopid + "");
                hashMap.put("OrderId", talabats.get(position).getNum() + "");
                hashMap.put("token", "bKPNOJrob8x");
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

    private void submitTaghez(final int orderId, final int regionId, final int employeeId, final int position) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/order/PreAndDirectToLocation",
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
                            talabats.remove(position);
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
                hashMap.put("Region", regionId + "");
                Log.e("Region", regionId + "");
                hashMap.put("Id", orderId + "");
                Log.e("Id", orderId + "");
                hashMap.put("ShopId", shopid + "");
                Log.e("ShopId", shopid + "");
                hashMap.put("CustomerId", employeeId + "");
                Log.e("CustomerId", employeeId + "");
                hashMap.put("token", "bKPNOJrob8x");
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

    private void submitTaghez(final int orderId,final int position) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("انتظر من فضلك ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.sellsapi.rivile.com/order/PreAndDirectToLocation",
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
                            talabats.remove(position);
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
                hashMap.put("Id", orderId + "");
                Log.e("Id", orderId + "");
                hashMap.put("ShopId", shopid + "");
                Log.e("ShopId", shopid + "");
                hashMap.put("token", "bKPNOJrob8x");
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
