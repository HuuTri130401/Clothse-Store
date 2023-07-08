package com.trith.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.trith.R;
import com.trith.adapters.CategoryAdapter;
import com.trith.adapters.ProductAdapter;
import com.trith.models.CategoryModel;
import com.trith.models.ProductModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment  {

    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView categoryRec, productRec;

    FirebaseFirestore db;
     ArrayList<CategoryModel> categoryModelList;
     CategoryAdapter categoryAdapter;

     ArrayList<ProductModel> productModelList;

     ProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();



        progressBar = root.findViewById(R.id.progressBar);
        scrollView = root.findViewById(R.id.scrollView);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        //Category
        categoryRec = root.findViewById(R.id.home_hor_rec);
        categoryRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(), this, categoryModelList);
        categoryRec.setAdapter(categoryAdapter);
        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel =  document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

        //Product
        productRec = root.findViewById(R.id.home_ver_rec);
        productRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        productModelList = new ArrayList<>();
        productAdapter = new ProductAdapter(getActivity(), productModelList);
        productRec.setAdapter(productAdapter);
        db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel productModel =  document.toObject(ProductModel.class);
                                productModelList.add(productModel);
                                productAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
        return root;
    }


}