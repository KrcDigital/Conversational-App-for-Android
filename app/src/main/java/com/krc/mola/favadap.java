package com.krc.mola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class favadap extends RecyclerView.Adapter<ImageViewHolderfav> {


    private Context context;
    private List<favjava> favlist;

    public favadap(Context context, List<favjava> favlist) {
        this.context = context;
        this.favlist = favlist;
    }

    @NonNull
    @Override
    public ImageViewHolderfav onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorec,parent,false);

        return  new ImageViewHolderfav(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolderfav holder, int position) {


        holder.o.setText(favlist.get(position).geto());
        Picasso.get().load(favlist.get(position).getId()).into(holder.id);



    }

    @Override
    public int getItemCount() {
        return favlist.size();
    }
}

class ImageViewHolderfav extends RecyclerView.ViewHolder {


    TextView o;
    ImageView id;


    public ImageViewHolderfav(@NonNull View itemView) {
        super(itemView);

        o = itemView.findViewById(R.id.textView26);
        id = itemView.findViewById(R.id.imageView5);


    }

}