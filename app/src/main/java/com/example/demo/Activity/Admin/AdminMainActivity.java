package com.example.demo.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.demo.Activity.BaseActivity;
import com.example.demo.Adapter.Admin.AdminFoodListAdapter;
import com.example.demo.Model.Food;
import com.example.demo.databinding.ActivityAdminMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminMainActivity extends BaseActivity {
    ActivityAdminMainBinding binding;
    private RecyclerView.Adapter adapterListFood;

    @Override
    protected void onResume() {
        super.onResume();
        initListFood();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListFood();
        setVariable();
    }

    private void setVariable() {
        binding.buttonBack.setOnClickListener(v -> finish());
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AddFoodActivity.class));
            }
        });
    }

    private void initListFood() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarListFood.setVisibility(View.VISIBLE);
        ArrayList<Food> list = new ArrayList<>();
        Query query = myRef.orderByChild("CategoryId");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Food food = issue.getValue(Food.class);
                        food.setKey(issue.getKey()); // Gán key cho mỗi Food
                        list.add(food);
                    }

                    if (list.size() > 0) {
                        binding.recyclerViewListFood.setLayoutManager(new GridLayoutManager(AdminMainActivity.this, 1));
                        adapterListFood = new AdminFoodListAdapter(list,myRef);
                        binding.recyclerViewListFood.setAdapter(adapterListFood);
                    }
                    binding.progressBarListFood.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}