package com.talabaty.swever.admin.Montagat.ControlMontag;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.talabaty.swever.admin.Home;
import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.Mabi3at.Mabi3atNavigator;
import com.talabaty.swever.admin.Managment.Privilages.ControlPrivilege.PrivilegeItem;
import com.talabaty.swever.admin.R;

import java.util.List;

public class ControlMontagAdapter extends RecyclerView.Adapter<ControlMontagAdapter.Vholder> {

    List<Talabat> talabats;
    Intent intent;

    Context context;
    FragmentManager fragmentManager;

    public ControlMontagAdapter(List<Talabat> talabats, Context context) {
        this.talabats = talabats;
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

        holder.id.setText(talabats.get(position).getId());
        holder.name.setText(talabats.get(position).getName());
        holder.date.setText(talabats.get(position).getEstlam_date());
        holder.num.setText(talabats.get(position).getNum());
        holder.time.setText(talabats.get(position).getEstlam_time());
        holder.amount.setText(talabats.get(position).getAmount());
        holder.price.setText(talabats.get(position).getPrice());
        holder.emergency_amount.setText(talabats.get(position).getEmergency_amount());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Home.class);
                intent.putExtra("fragment","edit_control");
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return talabats.size();
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
}
