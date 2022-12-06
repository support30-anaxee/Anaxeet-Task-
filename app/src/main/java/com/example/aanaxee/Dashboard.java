package com.example.aanaxee;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private Button signOut;
    TextInputLayout fullName,email,phoneNo,passowrd;
    TextView fullNameLabel,userNameLabel;
    String user_username,user_name,user_email,user_phoneNo,user_password;

    //RelativeLayout business,payment1,bima,amazon,wp,portal,info,select,online,download;

    @SuppressLint({"UseSupportActionBar", "MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        business = findViewById(R.id.business);
//        payment1 = findViewById(R.id.payment1);
//        bima = findViewById(R.id.bima);
//        amazon = findViewById(R.id.amazon);
//        wp = findViewById(R.id.wp);
//        portal = findViewById(R.id.portal);
//        info = findViewById(R.id.info);
//        select = findViewById(R.id.select);
//        online = findViewById(R.id.online);
//        download = findViewById(R.id.download);
        drawerLayout = findViewById(R.id.drawer_Layout1);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(Dashboard.this,Dashboard.class);
                startActivity(intent);
                break;
            case R.id.nav_my_Profile:
                Intent intent1 = new Intent(Dashboard.this,UserProfile.class);
                showAllData();
                startActivity(intent1);
                break;
            case R.id.nav_add_village:
                Intent intent2 = new Intent(Dashboard.this,List_Of_State.class);
                startActivity(intent2);
                break;
            case R.id.nav_logout:
                Intent intent3 = new Intent(Dashboard.this,LoginActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_notification:
                //Intent intent4 = new Intent(Dashboard.this,------???.class);
                //startActivity(intent4);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
}