package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Results;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowResultActivity extends AppCompatActivity {
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

    DBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        setSupportActionBar(findViewById(R.id.toolbarId));

        dbHelper = new DBHelper(this);
        session = new Session(this);

        imageViewProfile = (CircleImageView) findViewById(R.id.profile_image);
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        usernameToolbar = (TextView) findViewById(R.id.usernameToolbar);

        usernameToolbar.setText(session.get("username"));
        String imagePath = session.get("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);

        String identity = getIntent().getExtras().getString("identity");

        List<Results> results = dbHelper.getByIdentity(identity);
        List<Results> result1st = new ArrayList<>();
        List<Results> result2nd = new ArrayList<>();
        List<Results> result3rd = new ArrayList<>();

        results.stream().forEach(arr ->{
            System.out.println(arr.getTotal());
            if(arr.getSemester().equals("1st Trimester")){
                result1st.add(arr);
            }else if(arr.getSemester().equals("2nd Trimester")){
                result2nd.add(arr);
            }else if(arr.getSemester().equals("3rd Trimester")){
                result3rd.add(arr);
            }
        });

        TextView fullName = (TextView) findViewById(R.id.resultFullName);
        TextView email = (TextView) findViewById(R.id.resultEmail);
        TextView gender = (TextView) findViewById(R.id.resultGender);
        TextView studentId = (TextView) findViewById(R.id.resultIdentity);
        TextView mobile = (TextView) findViewById(R.id.resultMobile);
        TextView course = (TextView) findViewById(R.id.resultCourse);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowResultActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        if(results.size() > 0){
            fullName.setText(results.get(0).getFullName());
            email.setText(results.get(0).getEmail());
            gender.setText(results.get(0).getGender());
            studentId.setText(results.get(0).getStudentId());
            mobile.setText(results.get(0).getMobile());
            course.setText(results.get(0).getDesignationOrCourseName());
        }


        ResultsAdapter adapter1st = new ResultsAdapter(this, result1st);
        ResultsAdapter adapter2nd = new ResultsAdapter(this, result2nd);
        ResultsAdapter adapter3rd = new ResultsAdapter(this, result3rd);

        // Attach the adapter to a ListView
        ListView listView1st = (ListView) findViewById(R.id.listView1st);
        ListView listView2nd = (ListView) findViewById(R.id.listView2nd);
        ListView listView3rd = (ListView) findViewById(R.id.listView3rd);
        listView1st.setAdapter(adapter1st);
        listView2nd.setAdapter(adapter2nd);
        listView3rd.setAdapter(adapter3rd);

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
                Intent intentProfile = new Intent(ShowResultActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(ShowResultActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}