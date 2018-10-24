package com.talabaty.swever.admin.Offers.Restaurant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.talabaty.swever.admin.Offers.OperOfferModel;
import com.talabaty.swever.admin.R;

import java.util.List;

public class OfferRestaurantAdapter extends RecyclerView.Adapter<OfferRestaurantAdapter.Vholder> {

    Context context;
    List<OperOfferModel> agents;


    public OfferRestaurantAdapter(Context context, List<OperOfferModel> agents) {
        this.context = context;
        this.agents = agents;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_resturant_row_item,parent,false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.name.setText(agents.get(position).getName());
        holder.price.setText(agents.get(position).getPrice()+"");
        holder.num.setText(agents.get(position).getAmount()+"");
        holder.size.setText(agents.get(position).getSize_name()+"");

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Delete From List And Review the list
                try {
                    if (agents.size()> 0) {
                        agents.remove(position);
                        notifyItemRemoved(position);
                    }else {
                        Toast.makeText(context,"Can't Remove",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception s){
//                    Toast.makeText(context,"Can't Remove",Toast.LENGTH_SHORT).show();

                    for (int x=0; x<agents.size(); x++){
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

    public class Vholder extends RecyclerView.ViewHolder{
        ImageButton close;
        TextView name, price, num, size;
        public Vholder(View itemView) {
            super(itemView);
            close = itemView.findViewById(R.id.close);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            num = itemView.findViewById(R.id.num);
            size = itemView.findViewById(R.id.size);
        }
    }

}
