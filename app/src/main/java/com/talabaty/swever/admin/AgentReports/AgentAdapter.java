package com.talabaty.swever.admin.AgentReports;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.talabaty.swever.admin.R;

import java.util.List;

public class AgentAdapter extends RecyclerView.Adapter<AgentAdapter.Vholder> {

    Context context;
    List<Agent> agents;

    View message;

    Button message_send;
    EditText message_title, message_content;
    Spinner message_type;
    ImageButton close;

    public AgentAdapter(Context context, List<Agent> agents) {
        this.context = context;
        this.agents = agents;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_report_row_item,parent,false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.id.setText(agents.get(position).getId());
        holder.name.setText(agents.get(position).getName());
        holder.num.setText(agents.get(position).getNum());
        
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                message = inflater.inflate(R.layout.dialog_message_talabat, null);

                final int sdk = Build.VERSION.SDK_INT;
                message_type = message.findViewById(R.id.messagetype);
                message_title = message.findViewById(R.id.messagetitle);
                message_content = message.findViewById(R.id.messagecontent);
                message_send = message.findViewById(R.id.send);
                close = message.findViewById(R.id.close);

                message_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (message_type.getSelectedItem().toString().equals("مكالمه تليفونيه")) {

                            message_title.setVisibility(View.GONE);
                            message_content.setVisibility(View.GONE);
                            message_send.setText("اتصال");
                            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                                message_send.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.call_shape));
                            } else {
                                message_send.setBackground(ContextCompat.getDrawable(context, R.drawable.call_shape));
                            }
                        } else if (message_type.getSelectedItem().toString().equals("رساله نصيه")) {

                            message_title.setVisibility(View.GONE);
                            message_content.setVisibility(View.VISIBLE);
                            message_send.setText("إرسال");
                            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                                message_send.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_shape));
                            } else {
                                message_send.setBackground(ContextCompat.getDrawable(context, R.drawable.button_shape));
                            }
                        } else if (message_type.getSelectedItem().toString().equals("رساله عبر الإيميل")) {
                            message_title.setVisibility(View.VISIBLE);
                            message_content.setVisibility(View.VISIBLE);
                            message_send.setText("إرسال");
                            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                                message_send.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.button_shape));
                            } else {
                                message_send.setBackground(ContextCompat.getDrawable(context, R.drawable.button_shape));
                            }
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
                        submitMessage(dialog2, message_type.getSelectedItem().toString());
                    }
                });
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return agents.size();
    }

    public class Vholder extends RecyclerView.ViewHolder{
        Button start;
        TextView id, name, num;
        public Vholder(View itemView) {
            super(itemView);
            start = itemView.findViewById(R.id.start);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            num = itemView.findViewById(R.id.num);
        }
    }

    private void submitMessage(AlertDialog dialog, String s) {
        clearMessageView();
        if (s.equals("مكالمه تليفونيه")) {

            dialog.dismiss();
        } else if (s.equals("رساله نصيه")) {

            dialog.dismiss();
        } else if (s.equals("رساله عبر الإيميل")) {

            dialog.dismiss();
        }
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
}
