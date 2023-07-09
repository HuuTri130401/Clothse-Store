package com.trith.ui.cart;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
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

                            // Show notification if cart has products
                            if (!orderDetailModelList.isEmpty()) {
                                showCartNotification(getContext(), orderDetailModelList.size());
                            }

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
    private void showCartNotification(Context context, int productCount) {
        // Check for permission
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.VIBRATE}, 1);
            return;
        }

        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "cart_notification_channel";
            CharSequence channelName = "Cart Notification";
            String channelDescription = "Notification for Cart";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create notification
        String notificationTitle = "Cart Notification";
        String notificationText = "You have " + productCount + " product(s) in your cart";
        int notificationId = 1;

        Intent intent = new Intent(context, CartFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "cart_notification_channel")
                .setSmallIcon(R.drawable.notification_cart)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

}