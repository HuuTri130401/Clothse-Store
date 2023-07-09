package com.trith.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.trith.R;
import com.trith.models.OrderDetailModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        List<OrderDetailModel> list = (ArrayList<OrderDetailModel>) getIntent().getSerializableExtra("itemList");
        if(list != null && list.size() > 0) {
            for (OrderDetailModel model : list) {
                final HashMap<String, Object> cartMap = new HashMap<>();

                cartMap.put("productName", model.getProductName());
                cartMap.put("currentDate", model.getCurrentDate());
                cartMap.put("currentTime", model.getCurrentTime());
                cartMap.put("quantity", model.getQuantity());
                cartMap.put("total", model.getTotal());

                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Order").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(OrderActivity.this, "Your Order Has Been Placed", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        }

    }
}