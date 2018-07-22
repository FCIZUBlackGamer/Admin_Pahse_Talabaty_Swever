package com.talabaty.swever.admin.Mabi3at.SailedReports;

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

public class SailedReportsTalabatAdapter extends RecyclerView.Adapter<SailedReportsTalabatAdapter.Vholder> {

    Context context;
    List<Talabat> talabats;

    public SailedReportsTalabatAdapter(Context context, List<Talabat> talabats) {
        this.context = context;
        this.talabats = talabats;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sailed_report_talabat_row_item, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {


        holder.id.setText(talabats.get(position).getId());
        holder.name.setText(talabats.get(position).getName());
        holder.total.setText(talabats.get(position).getTotal());
        holder.estilam_date.setText(talabats.get(position).getEstlam_date());
        holder.estilam_time.setText(talabats.get(position).getEstlam_time());
        holder.tasleem_date.setText(talabats.get(position).getTasleem_date());
        holder.tasleem_time.setText(talabats.get(position).getTasleem_time());

    }

    @Override
    public int getItemCount() {
        return talabats.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView id, name, total, estilam_date, estilam_time, tasleem_date, tasleem_time;

        public Vholder(View itemView) {
            super(itemView);
            estilam_date = itemView.findViewById(R.id.estlam_date);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.client_name);
            total = itemView.findViewById(R.id.total);
            estilam_time = itemView.findViewById(R.id.estlam_time);
            tasleem_date = itemView.findViewById(R.id.tasleem_date);
            tasleem_time = itemView.findViewById(R.id.tasleem_time);
        }

    }

}
