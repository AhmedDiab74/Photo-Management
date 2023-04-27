package com.example.photomanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class galaryAdapter extends RecyclerView.Adapter<galaryAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<String> images_list;

    public galaryAdapter(Context context, ArrayList<String> images_list) {
        this.context = context;
        this.images_list = images_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.photogalary,null,true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    File file_imag = new File(images_list.get(position));
    if (file_imag.exists()){
        Glide.with(context).load(file_imag).into(holder.img);
    }
    }

    @Override
    public int getItemCount() {
        return images_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       public static ImageView img ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.photo);

        }
    }
}
