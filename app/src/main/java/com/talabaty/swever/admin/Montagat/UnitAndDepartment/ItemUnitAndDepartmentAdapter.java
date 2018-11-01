package com.talabaty.swever.admin.Montagat.UnitAndDepartment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.talabaty.swever.admin.Montagat.Additions.Item;
import com.talabaty.swever.admin.R;

import java.util.List;

public class ItemUnitAndDepartmentAdapter extends RecyclerView.Adapter<ItemUnitAndDepartmentAdapter.Vholder> {

    Context context;
    List<Item> agents;

    public ItemUnitAndDepartmentAdapter(Context context, List<Item> agents) {
        this.context = context;
        this.agents = agents;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_unit_dep, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.name.setText(agents.get(position).getName());
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


    }

    @Override
    public int getItemCount() {
        return agents.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        ImageButton delete;
        TextView name;

        public Vholder(View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            name = itemView.findViewById(R.id.name);
        }
    }
}
