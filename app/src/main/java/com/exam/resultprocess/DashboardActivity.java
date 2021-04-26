package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    String result;
    Button inputResult;

    CardView cardViewBatch, cardViewTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setSupportActionBar(findViewById(R.id.toolbarId));
        cardViewBatch = (CardView) findViewById(R.id.cardViewBatch);
        cardViewTeacher = (CardView) findViewById(R.id.cardViewTeacher);
        cardViewBatch.setRadius(20F);
        cardViewTeacher.setRadius(30F);

        inputResult = (Button) findViewById(R.id.inputResult);

        inputResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, InputResultActivity.class);
                startActivity(intent);
            }
        });

        cardViewTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, TeacherSetupActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profileItem:
                Toast.makeText(this, "Edit Profile Click", Toast.LENGTH_SHORT).show();
                Intent intentProfile = new Intent(DashboardActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}