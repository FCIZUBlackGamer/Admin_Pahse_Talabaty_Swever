package com.talabaty.swever.admin.Montagat.Market;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.Vholder> {

    Context context;
    List<Bitmap> imageSources;
    List<Uri> uris;

    public ImageAdapter(Context context, List<Bitmap> imageSources, List<Uri> uris) {
        this.context = context;
        this.imageSources = imageSources;
        this.uris = uris;
    }

    @NonNull
    @Override
    public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_image, parent, false);
        return new Vholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vholder holder, final int position) {


//        holder.image_button.setImageBitmap(imageSources.get(position));
        Picasso.with(context).load(uris.get(position)).into(holder.image_button);

        if (holder.image_button.getDrawable() == null){
            holder.image_button.setImageBitmap(imageSources.get(position));
        }

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete Color From List And Redisplay the List
                try {
                    if (imageSources.size() > 0) {
                        imageSources.remove(position);
                        uris.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Can't Remove", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception s) {
//                    Toast.makeText(context,"Can't Remove",Toast.LENGTH_SHORT).show();

                    for (int x = 0; x < imageSources.size(); x++) {
                        imageSources.remove(x);
                        uris.remove(x);
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
