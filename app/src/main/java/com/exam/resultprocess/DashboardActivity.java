package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Users;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

    CardView cardViewBatch, cardViewTeacher, cardViewShowResult, cardViewShowAllResult, cardViewInputResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setSupportActionBar(findViewById(R.id.toolbarId));
        cardViewBatch = (CardView) findViewById(R.id.cardViewBatch);
        cardViewTeacher = (CardView) findViewById(R.id.cardViewTeacher);
        cardViewShowResult = (CardView) findViewById(R.id.cardViewShowResult);
        cardViewShowAllResult = (CardView) findViewById(R.id.cardViewShowAllResult);
        cardViewInputResult = (CardView) findViewById(R.id.cardViewInputResult);
        cardViewBatch.setRadius(20F);
        cardViewTeacher.setRadius(30F);
        cardViewShowResult.setRadius(30F);
        cardViewShowAllResult.setRadius(30F);
        cardViewInputResult.setRadius(30F);

        session = new Session(this);

        imageViewProfile = (CircleImageView) findViewById(R.id.profile_image);
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        usernameToolbar = (TextView) findViewById(R.id.usernameToolbar);

        usernameToolbar.setText(session.get("username"));
        String imagePath = session.get("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);
        String type = session.get("type");
        String userid = session.get("userid");
        if((type.equals("Teacher")) && (userid.equals("1234"))){
            cardViewBatch.setVisibility(View.VISIBLE);
            cardViewTeacher.setVisibility(View.VISIBLE);
            cardViewShowResult.setVisibility(View.GONE);
            cardViewShowAllResult.setVisibility(View.VISIBLE);
            cardViewInputResult.setVisibility(View.GONE);
        }else if((type.equals("Teacher")) && (!userid.equals("1234"))){
            cardViewBatch.setVisibility(View.GONE);
            cardViewTeacher.setVisibility(View.GONE);
            cardViewShowResult.setVisibility(View.GONE);
            cardViewShowAllResult.setVisibility(View.VISIBLE);
            cardViewInputResult.setVisibility(View.VISIBLE);
        }else{
            cardViewBatch.setVisibility(View.GONE);
            cardViewTeacher.setVisibility(View.GONE);
            cardViewShowResult.setVisibility(View.VISIBLE);
            cardViewShowAllResult.setVisibility(View.GONE);
            cardViewInputResult.setVisibility(View.GONE);
        }

        if(getIntent().getExtras() != null){
            Users users = (Users) getIntent().getParcelableExtra("user");
            setToolbarData(users);
        }

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        cardViewShowAllResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MultipleCourseActivity.class);
                intent.putExtra("identity", userid);
                startActivity(intent);
            }
        });

        cardViewShowResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ShowResultActivity.class);
                intent.putExtra("identity", session.get("userid"));
                intent.putExtra("courseName", session.get("courseName"));
                startActivity(intent);
            }
        });

        cardViewInputResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, InputResultActivity.class);
                startActivity(intent);
            }
        });

        cardViewBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, BatchCreateActivity.class);
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

    public void setToolbarData(Users users){
        usernameToolbar.setText(users.getFullName());
        File filePath = Environment.getExternalStorageDirectory();
        File dir = new File(filePath.getAbsolutePath()+"/userImages/");
        File file = new File(dir, users.getIdentity()+users.getExtension());
        String imagePath = file.getPath();
        session.set("imagePath", imagePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);
    }

}