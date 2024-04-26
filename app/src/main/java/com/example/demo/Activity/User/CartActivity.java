package com.example.demo.Activity.User;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.demo.Activity.BaseActivity;
import com.example.demo.Adapter.User.CartAdapter;
import com.example.demo.Helper.ManagmentCart;
import com.example.demo.databinding.ActivityCartBinding;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    private double tax;
    double percentTax = 0.05;
    String counponCode = "xincamon";//10%

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#F75564"));

        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);

        setVariable();
        calculateCart();
        initList();
    }

    private void initList() {
        if (managmentCart.getListCart().isEmpty()) {
            binding.txtEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.txtEmpty.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cartView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        binding.cartView.setAdapter(adapter);
    }

    private void calculateCart() {
// Tính toán tax
        double tax = managmentCart.getTotalFee() * percentTax;
        DecimalFormat taxFormat = new DecimalFormat("#,###.##");
        taxFormat.setRoundingMode(RoundingMode.DOWN);
        String formattedTax =   taxFormat.format(tax);

// Tính toán total
        double counpon=0;
        double total= managmentCart.getTotalFee() + tax ;
        if (binding.editTextCounpon.getText().toString().equals(counponCode)) {
            counpon=total*0.1;
            total =total*0.9;
        }
        DecimalFormat totalFormat = new DecimalFormat("#,###.##");
        totalFormat.setRoundingMode(RoundingMode.DOWN);
        String formattedTotal = totalFormat.format(total);

        DecimalFormat totalCounpon = new DecimalFormat("#,###.##");
        totalCounpon.setRoundingMode(RoundingMode.DOWN);
        String formattedCounpon = "- " +totalCounpon.format(counpon);

// Lấy giá trị itemTotal
        double itemTotal = managmentCart.getTotalFee();
        DecimalFormat itemTotalFormat = new DecimalFormat("#,###.##");
        itemTotalFormat.setRoundingMode(RoundingMode.DOWN);
        String formattedItemTotal = itemTotalFormat.format(itemTotal);




        // Gán giá trị vào TextViews
        binding.txtSubTotal.setText(formattedItemTotal);
        binding.txtTax.setText(formattedTax);
        binding.txtTotal.setText(formattedTotal);
        binding.txtCounpon.setText(formattedCounpon);

    }

    private void setVariable() {
        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnCounpon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCart();
            }
        });
    }
}