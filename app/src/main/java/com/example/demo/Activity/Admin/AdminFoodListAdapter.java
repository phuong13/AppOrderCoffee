package com.example.demo.Activity.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.demo.Adapter.FoodListAdapter;
import com.example.demo.Model.Food;
import com.example.demo.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdminFoodListAdapter extends RecyclerView.Adapter<AdminFoodListAdapter.viewholder> {

    ArrayList<Food> items;
    Context context;

    public AdminFoodListAdapter(ArrayList<Food> items) {
        this.items =items;
    }



    @NonNull
    @Override
    public AdminFoodListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_admin_listfood, parent, false);
        return new AdminFoodListAdapter.viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminFoodListAdapter.viewholder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedPrice = decimalFormat.format( items.get(position).getPrice());
        holder.titleTxt.setText(items.get(position).getTitle());
        holder.priceTxt.setText(formattedPrice);

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.pic);

        
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt;
        Button editBtn,delBtn;
        ImageView pic;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.txt_Title);
            priceTxt = itemView.findViewById(R.id.txt_Price);
            editBtn=itemView.findViewById(R.id.btn_Edit);
            delBtn=itemView.findViewById(R.id.btn_Delete);

            pic = itemView.findViewById(R.id.img);
        }
    }
}
