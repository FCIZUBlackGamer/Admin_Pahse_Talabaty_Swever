package com.talabaty.swever.admin.Options.Details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.talabaty.swever.admin.DetailsModel;
import com.talabaty.swever.admin.R;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.Vholder> {
    Context context;
    List<DetailsModel> detailsModels;


    public DetailsAdapter(Context context, List<DetailsModel> detailsModels) {
        this.context = context;
        this.detailsModels = detailsModels;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_details_recycle, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, int position) {

        holder.id.setText(detailsModels.get(position).getId());
        holder.name.setText(detailsModels.get(position).getName());
        holder.amount.setText(detailsModels.get(position).getAmount());
        holder.type.setText(detailsModels.get(position).getType());

    }

    @Override
    public int getItemCount() {
        return detailsModels.size();
    }

    public class Vholder extends RecyclerView.ViewHolder{
        TextView id, amount, type, name;
        public Vholder(View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.amount);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
        }
    }


}
