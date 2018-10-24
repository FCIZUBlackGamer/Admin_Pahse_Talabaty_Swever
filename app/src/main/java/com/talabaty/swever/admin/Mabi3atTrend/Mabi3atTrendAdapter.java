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
    List<BestSell> sellList;
    int temp_first, temp_last;

    public Mabi3atTrendAdapter(Context context, List<BestSell> sellList, int temp_first, int temp_last) {
        this.context = context;
        this.sellList = sellList;
        this.temp_last = temp_last;
        this.temp_first = temp_first;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_report_mabi3at_row_item, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {


        holder.id.setText(sellList.get(position).getProductId()+"");
        holder.name.setText(sellList.get(position).getProductName()+"");
        holder.total.setText(sellList.get(position).getTotal()+"");
        holder.amount.setText(sellList.get(position).getAmountseller()+"");


    }

    @Override
    public int getItemCount() {
        return sellList.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView id, name, total, amount;

        public Vholder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            total = itemView.findViewById(R.id.total);
            amount = itemView.findViewById(R.id.amount);
        }

    }

}
