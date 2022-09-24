package com.krc.mola;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<ImageViewHolder> {


    private Context context;
    private List<chatjava> chatlist;

    public Adapter(Context context, List<chatjava> chatlist) {
        this.context = context;
        this.chatlist = chatlist;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msjcel,parent,false);

        return  new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {


        holder.o.setText(chatlist.get(position).geto());
        holder.ben.setText(chatlist.get(position).getben());



    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }
}

class ImageViewHolder extends RecyclerView.ViewHolder{


    TextView o;
    TextView ben;


    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);

        o = itemView.findViewById(R.id.textView25);
        ben = itemView.findViewById(R.id.textView3);




    }

}