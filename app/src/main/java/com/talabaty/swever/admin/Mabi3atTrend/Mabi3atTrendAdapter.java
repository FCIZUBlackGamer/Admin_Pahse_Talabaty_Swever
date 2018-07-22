package com.talabaty.swever.admin.Mabi3atTrend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.talabaty.swever.admin.Mabi3at.DoneTalabat.Talabat;
import com.talabaty.swever.admin.R;

import java.util.List;

public class Mabi3atTrendAdapter extends RecyclerView.Adapter<Mabi3atTrendAdapter.Vholder> {

    Context context;
    List<Talabat> talabats;

    public Mabi3atTrendAdapter(Context context, List<Talabat> talabats) {
        this.context = context;
        this.talabats = talabats;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_report_mabi3at_row_item, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {


        holder.id.setText(talabats.get(position).getId());
        holder.name.setText(talabats.get(position).getName());
        holder.total.setText(talabats.get(position).getTotal());
        holder.price.setText(talabats.get(position).getPrice());
        holder.amount.setText(talabats.get(position).getAmount());


    }

    @Override
    public int getItemCount() {
        return talabats.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView id, name, total, price, amount;

        public Vholder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            total = itemView.findViewById(R.id.total);
            price = itemView.findViewById(R.id.price);
            amount = itemView.findViewById(R.id.amount);
        }

    }

}
