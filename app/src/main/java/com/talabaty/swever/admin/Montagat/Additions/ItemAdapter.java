package com.talabaty.swever.admin.Montagat.Additions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.talabaty.swever.admin.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Vholder> {

    Context context;
    List<Item> agents;

    public ItemAdapter(Context context, List<Item> agents) {
        this.context = context;
        this.agents = agents;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_additions, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.name.setText(agents.get(position).getName());
        holder.price.setText(agents.get(position).getNum()+"");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (agents.size() > 0) {
                        agents.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Can't Remove", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception s) {
//                    Toast.makeText(context,"Can't Remove",Toast.LENGTH_SHORT).show();

                    for (int x = 0; x < agents.size(); x++) {
                        agents.remove(x);
                        notifyItemRemoved(x);
                    }
                }
            }
        });

        holder.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout view = new LinearLayout(context);
                final EditText name = new EditText(context);
                final EditText price = new EditText(context);
                price.setInputType(InputType.TYPE_CLASS_NUMBER);
                name.setText(agents.get(position).getName());
                price.setText(agents.get(position).getNum()+"");
                view.setOrientation(LinearLayout.VERTICAL);
                view.addView(name);
                view.addView(price);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("تعديل")
                        .setView(view)
                        .setPositiveButton("تعديل", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                agents.get(position).setName(name.getText().toString());
                                agents.get(position).setPrice(Float.parseFloat(price.getText().toString()));
                                notifyItemRemoved(position);
                            }
                        }).setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return agents.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        ImageButton delete;
        TextView name, price;
        LinearLayout line;

        public Vholder(View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            line = itemView.findViewById(R.id.line);
        }
    }
}
