package com.trith.ui.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trith.R;
import com.trith.models.UserModel;

public class ProfileFragment extends Fragment {
    private TextView tvFullName, tvEmail, tvPassword, tvAddress, tvPhone;
    private Button btnUpdate;
    private DatabaseReference userRef;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Lấy đối tượng FirebaseUser hiện tại
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());

        // Hiển thị thông tin người dùng trên giao diện
        tvFullName = root.findViewById(R.id.profileFullName);
        tvEmail = root.findViewById(R.id.profileEmail);
        tvPassword = root.findViewById(R.id.profilePassword);
        tvAddress = root.findViewById(R.id.profileAddress);
        tvPhone = root.findViewById(R.id.profilePhone);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    tvFullName.setText(userModel.getFullName());
                    tvEmail.setText(userModel.getEmail());
                    tvPassword.setText(userModel.getPassword());
                    tvAddress.setText(userModel.getAddress());
                    tvPhone.setText(userModel.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });

        // Xử lý sự kiện khi người dùng nhấn nút cập nhật
        btnUpdate = root.findViewById(R.id.btnUpdateProfile);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ giao diện người dùng
                String fullName = tvFullName.getText().toString();
                String email = tvEmail.getText().toString();
                String password = tvPassword.getText().toString();
                String address = tvAddress.getText().toString();
                String phone = tvPhone.getText().toString();

                // Cập nhật thông tin người dùng trên Realtime Database
                userRef.child("fullName").setValue(fullName);
                //userRef.child("email").setValue(email);
                userRef.child("password").setValue(password);
                userRef.child("address").setValue(address);
                userRef.child("phone").setValue(phone);

                Toast.makeText(getActivity(), "Update Profile Success!", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

}