package com.talabaty.swever.admin.Montagat.AddMontag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.talabaty.swever.admin.R;

import java.util.List;

public class OldImageAdapter extends RecyclerView.Adapter<OldImageAdapter.Vholder> {

    Context context;
    List<ImageSource> imageSources;

    public OldImageAdapter(Context context, List<ImageSource> imageSources) {
        this.context = context;
        this.imageSources = imageSources;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_image, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {


        Picasso.with(context)
                .load("http://selltlbaty.rivile.com"+imageSources.get(position).getPhoto())
                .into(holder.image_button);

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete Color From List And Redisplay the List
                try {
                    if (imageSources.size() > 0) {
                        imageSources.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Can't Remove", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception s) {
//                    Toast.makeText(context,"Can't Remove",Toast.LENGTH_SHORT).show();

                    for (int x = 0; x < imageSources.size(); x++) {
                        imageSources.remove(x);
                        notifyItemRemoved(x);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageSources.size();
    }

    public class Vholder extends RecyclerView.ViewHolder {
        ImageView image_button;
        ImageView delete_image;

        public Vholder(View itemView) {
            super(itemView);
            image_button = itemView.findViewById(R.id.image_button);
            delete_image = itemView.findViewById(R.id.delete_image);
        }
    }
}
