package com.example.aanaxee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {
    TextInputLayout fullName,email,phoneNo,passowrd;
    TextView fullNameLabel,userNameLabel;
    ImageView back;
    DatabaseReference reference;
    String user_username,user_name,user_email,user_phoneNo,user_password;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        back = findViewById(R.id.user_back);
        fullName = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_profile);
        passowrd = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.full_name_filled);
        userNameLabel = findViewById(R.id.user_name_filled);

        reference = FirebaseDatabase.getInstance().getReference("users");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this,Dashboard.class);
                startActivity(intent);
            }
        });

        showAllData();
    }

    private void showAllData() {
        Intent intent = getIntent() ;
        user_username = intent.getStringExtra("username");
        user_name = intent   .getStringExtra("name");
        user_email = intent.getStringExtra("email");
        user_phoneNo = intent.getStringExtra("phoneNo");
        user_password = intent.getStringExtra("password");

        fullNameLabel.setText(user_name);
        userNameLabel.setText(user_username);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        passowrd.getEditText().setText(user_password);

    }
//
//    private void update(View view){
//        if (isNameChange() || isPasswordChange()){
//            Toast.makeText(this, "Data has been update", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private boolean isPasswordChange(){
//
//    }
//
//    private boolean isNameChange() {
//        if (user_username.equals(fullName.getEditText().getText().toString()));
//    }
//

}