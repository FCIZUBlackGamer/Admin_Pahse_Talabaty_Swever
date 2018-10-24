package com.talabaty.swever.admin.Montagat.AddReturanteMontage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.talabaty.swever.admin.R;

import java.util.List;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.Vholder> {

    Context context;
    List<Size> sizeList;

    public SizeAdapter(Context context, List<Size> sizeList) {
        this.context = context;
        this.sizeList = sizeList;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_size_returante,parent,false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {

        holder.size_button.setText(sizeList.get(position).getPrice()+"\n\n"+ sizeList.get(position).getSize());
        holder.delete_size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete Color From List And Redisplay the List
                try {
                    if (sizeList.size()> 0) {
                        sizeList.remove(position);
                        notifyItemRemoved(position);
                    }else {
                        Toast.makeText(context,"Can't Remove",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception s){
//                    Toast.makeText(context,"Can't Remove",Toast.LENGTH_SHORT).show();

                    for (int x = 0; x< sizeList.size(); x++){
                        sizeList.remove(x);
                        notifyItemRemoved(x);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public class Vholder extends RecyclerView.ViewHolder{
        Button delete_size;
        TextView size_button;
        public Vholder(View itemView) {
            super(itemView);
            delete_size = itemView.findViewById(R.id.delete_size);
            size_button = itemView.findViewById(R.id.size_button);
        }
    }
}
