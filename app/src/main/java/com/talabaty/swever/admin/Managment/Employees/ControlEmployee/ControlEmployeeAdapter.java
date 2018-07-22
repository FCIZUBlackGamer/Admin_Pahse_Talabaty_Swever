package com.talabaty.swever.admin.Managment.Employees.ControlEmployee;

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
import com.talabaty.swever.admin.R;

import java.util.List;

public class ControlEmployeeAdapter extends RecyclerView.Adapter<ControlEmployeeAdapter.Vholder> {

    List<Talabat> talabats;
    Intent intent;

    Context context;
    FragmentManager fragmentManager;

    public ControlEmployeeAdapter(List<Talabat> talabats, Context context) {
        this.talabats = talabats;
        this.context = context;
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

        holder.id.setText(talabats.get(position).getId());
        holder.name.setText(talabats.get(position).getName());
        holder.job_title.setText(talabats.get(position).getJob_title());
        holder.duration.setText(talabats.get(position).getDuration());
        holder.time.setText(talabats.get(position).getTime());
        holder.manage.setText(talabats.get(position).getManage());
        holder.place_name.setText(talabats.get(position).getPlace_name());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Home.class);
                intent.putExtra("fragment","edit_emp");
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

        TextView id, name, duration, job_title, manage, place_name, time;
        ImageButton edit, delete;

        public Vholder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);
            id = itemView.findViewById(R.id.id);
            duration = itemView.findViewById(R.id.duration);
            time = itemView.findViewById(R.id.time);
            job_title = itemView.findViewById(R.id.job_title);
            manage = itemView.findViewById(R.id.manage);
            place_name = itemView.findViewById(R.id.place_name);

        }

    }
}
