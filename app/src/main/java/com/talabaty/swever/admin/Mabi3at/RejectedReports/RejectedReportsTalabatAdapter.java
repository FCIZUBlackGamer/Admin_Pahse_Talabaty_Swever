package com.talabaty.swever.admin.Mabi3at.RejectedReports;

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

public class RejectedReportsTalabatAdapter extends RecyclerView.Adapter<RejectedReportsTalabatAdapter.Vholder> {

    Context context;
    List<Talabat> talabats;

    public RejectedReportsTalabatAdapter(Context context, List<Talabat> talabats) {
        this.context = context;
        this.talabats = talabats;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rejected_report_talabat_row_item, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {


        holder.id.setText(talabats.get(position).getId());
        holder.name.setText(talabats.get(position).getName());
        holder.reason.setText(talabats.get(position).getReason());
        holder.total.setText(talabats.get(position).getTotal());
        holder.date.setText(talabats.get(position).getEstlam_date());
        holder.time.setText(talabats.get(position).getEstlam_time());

    }

    @Override
    public int getItemCount() {
        return talabats.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        TextView id, name, reason, total, date, time;

        public Vholder(View itemView) {
            super(itemView);
            reason = itemView.findViewById(R.id.reason);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.client_name);
            total = itemView.findViewById(R.id.total);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }

    }

}
