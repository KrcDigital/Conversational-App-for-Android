package com.krc.mola;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class gecadap extends RecyclerView.Adapter<ImageViewHoldergec> {


    private Context context;
    private List<gecjava> geclist;

    public gecadap(Context context, List<gecjava> geclist) {
        this.context = context;
        this.geclist = geclist;
    }

    @NonNull
    @Override
    public ImageViewHoldergec onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gecmisrec,parent,false);

        return  new ImageViewHoldergec(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHoldergec holder, int position) {


        holder.o.setText(geclist.get(position).geto());
        Picasso.get().load(geclist.get(position).getId()).into(holder.id);



    }

    @Override
    public int getItemCount() {
        return geclist.size();
    }
}

class ImageViewHoldergec extends RecyclerView.ViewHolder {


    TextView o;
    ImageView id;

    Button oku;

    public ImageViewHoldergec(@NonNull View itemView) {
        super(itemView);

        o = itemView.findViewById(R.id.textView26);
        id = itemView.findViewById(R.id.imageView5);
        oku = itemView.findViewById(R.id.button18);

        oku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }

}