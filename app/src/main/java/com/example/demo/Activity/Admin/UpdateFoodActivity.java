package com.example.demo.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.demo.Activity.BaseActivity;
import com.example.demo.Activity.User.DetailActivity;
import com.example.demo.Model.Food;
import com.example.demo.R;
import com.example.demo.databinding.ActivityUpdateFoodBinding;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class UpdateFoodActivity extends BaseActivity {
    ActivityUpdateFoodBinding binding;
    private Food object;
    EditText editTextTitle,editTextDesc,editTextTime,editTextPrice,editTextCateID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();

    }

    private void setVariable() {
        binding.buttonBack.setOnClickListener(v -> finish());
        Glide.with(UpdateFoodActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);
        DecimalFormat decimalFormat = new DecimalFormat("####.#");
        String formattedPrice = decimalFormat.format(object.getPrice());
        binding.editTextTitle.setText(object.getTitle());
        binding.editTextDescription.setText(object.getDescription());
        binding.editTextTime.setText(object.getTimeValue()+"");
        binding.editTextPrice.setText(formattedPrice);
        binding.editTextCateID.setText(object.getCategoryID()+"");

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFood();
            }
        });
    }

    private void updateFood() {
        editTextTitle = findViewById(R.id.editText_Title);
        editTextDesc = findViewById(R.id.editText_Description);
        editTextTime = findViewById(R.id.editText_Time);
        editTextPrice = findViewById(R.id.editText_Price);
        editTextCateID = findViewById(R.id.editText_CateID);

        String title = editTextTitle.getText().toString().trim();
        String desc = editTextDesc.getText().toString().trim();
        String timeStr = editTextTime.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String cateIDStr = editTextCateID.getText().toString().trim();

        if (title.isEmpty() || desc.isEmpty() || timeStr.isEmpty() || priceStr.isEmpty() || cateIDStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // Chuyển đổi chuỗi thành số nguyên và số thực
        int time, cateID;
        double price;
        try {
            time = Integer.parseInt(timeStr);
            price = Double.parseDouble(priceStr);
            cateID = Integer.parseInt(cateIDStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input format", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You want to update food?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Map<String, Object> foodMap = new HashMap<>();
                        foodMap.put("Title", title);
                        foodMap.put("Description", desc);
                        foodMap.put("TimeValue", time);
                        foodMap.put("Price", price);
                        foodMap.put("CategoryId", cateID);

                        DatabaseReference foodRef = database.getReference("Foods");
                        foodRef.child(object.getKey()).updateChildren(foodMap)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(UpdateFoodActivity.this, "Update food successful", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> Toast.makeText(UpdateFoodActivity.this, "Failure", Toast.LENGTH_SHORT).show());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void getIntentExtra() {
        object= (Food) getIntent().getSerializableExtra("object1");
    }
}