package com.trith;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.trith.adapters.CartAdapter;
import com.trith.models.OrderDetailModel;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<OrderDetailModel> orderDetailModelList;
    TextView totalOrderPrice;
    Button buyNow;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.recycleView_order);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        orderDetailModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), orderDetailModelList);

        recyclerView.setAdapter(cartAdapter);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Order").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                        }
                    }
                });
        return root;
    }


}