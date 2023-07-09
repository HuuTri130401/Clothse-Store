package com.trith.ui.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.trith.R;
import com.trith.activities.OrderActivity;
import com.trith.adapters.CartAdapter;
import com.trith.models.OrderDetailModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<OrderDetailModel> orderDetailModelList;
    TextView totalOrderPrice;
    Button buyNow;
    FirebaseFirestore db;
    FirebaseAuth auth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.recycleView_cart);
        buyNow = root.findViewById(R.id.buy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        totalOrderPrice = root.findViewById(R.id.totalOrderPrice);

        orderDetailModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), orderDetailModelList);

        recyclerView.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                                String documentId = documentSnapshot.getId();
                                OrderDetailModel orderDetailModel = documentSnapshot.toObject(OrderDetailModel.class);

                                orderDetailModel.setDcocumentId(documentId);
                                orderDetailModelList.add(orderDetailModel);
                                cartAdapter.notifyDataSetChanged();
                            }

                            calculateTotalAmount(orderDetailModelList);
                        }
                    }
                });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("itemList", (Serializable) orderDetailModelList);
                startActivity(intent);
            }
        });

        return root;
    }

    private void calculateTotalAmount(List<OrderDetailModel> orderDetailModelList) {
        double totalAmount = 0.0;
        for (OrderDetailModel orderDetailModel : orderDetailModelList) {
            totalAmount += orderDetailModel.getTotal();
        }
        totalOrderPrice.setText("Total amount" + totalAmount);

    }


}