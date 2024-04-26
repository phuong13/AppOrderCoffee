package com.example.demo.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.Activity.BaseActivity;
import com.example.demo.Activity.User.MainActivity;
import com.example.demo.Model.Category;
import com.example.demo.Model.Price;
import com.example.demo.R;
import com.example.demo.databinding.ActivityAddFoodBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddFoodActivity extends BaseActivity {
    ActivityAddFoodBinding binding;
    EditText editTextTitle,editTextDesc,editTextTime,editTextPrice,editTextCateID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        initCategory();
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        ArrayList<Category> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Category.class));
                    }
                    ArrayAdapter<Category> adapter = new ArrayAdapter<>(AddFoodActivity.this, R.layout.sp_item_category, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinCategory.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setVariable() {
        binding.buttonBack.setOnClickListener(v -> finish());
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood();
            }
        });

    }

    private void addFood() {
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

        // Kiểm tra xem các EditText có rỗng không
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
        builder.setMessage("You want to add food?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Map<String, Object> foodMap = new HashMap<>();
                        foodMap.put("Title", title);
                        foodMap.put("Description", desc);
                        foodMap.put("TimeValue", time);
                        foodMap.put("Price", price);
                        foodMap.put("CategoryId", cateID);

                        // Thêm món ăn vào Firebase Realtime Database
                        DatabaseReference foodRef = database.getReference("Foods");
                        foodRef.push().setValue(foodMap)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(AddFoodActivity.this, "Add food successful", Toast.LENGTH_SHORT).show();
                                    // Xóa nội dung trong EditText sau khi thêm thành công
                                    binding.editTextTitle.setText("");
                                    binding.editTextDescription.setText("");
                                    binding.editTextTime.setText("");
                                    binding.editTextPrice.setText("");
                                    binding.editTextCateID.setText("");
                                    binding.editTextTitle.requestFocus();
                                })
                                .addOnFailureListener(e -> Toast.makeText(AddFoodActivity.this, "Failure", Toast.LENGTH_SHORT).show());
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
}