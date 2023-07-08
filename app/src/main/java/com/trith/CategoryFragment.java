package com.trith;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trith.adapters.NavCategoryAdapter;
import com.trith.adapters.ProductAdapter;
import com.trith.models.ProductModel;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    FirebaseFirestore db;
    RecyclerView productRec;
    ArrayList<ProductModel> productModelList;
    NavCategoryAdapter productAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_category, container, false);

        Bundle bundle = getArguments();
        String type = bundle.getString("1");
        db = FirebaseFirestore.getInstance();
        productRec = root.findViewById(R.id.cat_rec);
        productRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        productModelList = new ArrayList<>();
        productAdapter = new NavCategoryAdapter(getActivity(), productModelList);
        productRec.setAdapter(productAdapter);

        if(type != null && type.equalsIgnoreCase("Shirt")) {
            db.collection("Product").whereEqualTo("type", "Shirt")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ProductModel productModel =  document.toObject(ProductModel.class);
                                    productModelList.add(productModel);
                                    productAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("Hoodie")) {
            db.collection("Product").whereEqualTo("type", "Hoodie")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ProductModel productModel =  document.toObject(ProductModel.class);
                                    productModelList.add(productModel);
                                    productAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("Tee")) {
            db.collection("Product").whereEqualTo("type", "Tee")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ProductModel productModel =  document.toObject(ProductModel.class);
                                    productModelList.add(productModel);
                                    productAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }

        if(type != null && type.equalsIgnoreCase("Jacket")) {
            db.collection("Product").whereEqualTo("type", "Jacket")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ProductModel productModel =  document.toObject(ProductModel.class);
                                    productModelList.add(productModel);
                                    productAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }


        if(type != null && type.equalsIgnoreCase("Sale")) {
            db.collection("Product").whereEqualTo("type", "Sale")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    ProductModel productModel =  document.toObject(ProductModel.class);
                                    productModelList.add(productModel);
                                    productAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }


        return root;
    }
}