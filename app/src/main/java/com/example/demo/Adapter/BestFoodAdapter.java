package com.example.demo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.demo.Activity.User.DetailActivity;
import com.example.demo.Model.Food;
import com.example.demo.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BestFoodAdapter extends RecyclerView.Adapter<BestFoodAdapter.viewholder> {
    ArrayList<Food> items;
    Context context;
    public BestFoodAdapter(ArrayList<Food> items){
        this.items = items;
    }

    @NonNull
    @Override
    public BestFoodAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context =parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_best_deal,parent,false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull BestFoodAdapter.viewholder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedPrice = decimalFormat.format( items.get(position).getPrice());
        holder.priceTxt.setText(formattedPrice);
        holder.timeTxt.setText(items.get(position).getTimeValue()+"min");
        holder.starTxt.setText(""+items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.pic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, DetailActivity.class);
                intent.putExtra("object",items.get(position));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt,priceTxt,starTxt,timeTxt,btnCong;
        ImageView pic;
        public viewholder(@NonNull View itemView){
            super(itemView);
            titleTxt = itemView.findViewById(R.id.txt_Title);
            priceTxt = itemView.findViewById(R.id.txt_Price);
            starTxt = itemView.findViewById(R.id.txt_Star);
            timeTxt = itemView.findViewById(R.id.txt_Time);
            btnCong =itemView.findViewById(R.id.txt_Plus);
            pic = itemView.findViewById(R.id.img);


        }
    }
}
