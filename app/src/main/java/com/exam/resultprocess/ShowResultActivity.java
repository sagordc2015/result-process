package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Results;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowResultActivity extends AppCompatActivity {
    final String secretKey = "ssshhhhhhhhhhh!!!!";
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

        AtomicReference<Double> total = new AtomicReference<>(0.0);
        AtomicInteger count = new AtomicInteger();
        AtomicReference<Boolean> check = new AtomicReference<>(false);
        results.stream().forEach(arr ->{
            if(arr.getSemester().equals("1st Trimester") || arr.getSemester().equals("1st Semester")){
                result1st.add(arr);
                count.getAndIncrement();
                if(arr.getGrade().equals("F")){
                    check.set(true);
                }
            }else if(arr.getSemester().equals("2nd Trimester") || arr.getSemester().equals("2nd Semester")){
                result2nd.add(arr);
                count.getAndIncrement();
                if(arr.getGrade().equals("F")){
                    check.set(true);
                }
            }else if(arr.getSemester().equals("3rd Trimester") || arr.getSemester().equals("3rd Semester")){
                result3rd.add(arr);
                count.getAndIncrement();
                if(arr.getGrade().equals("F")){
                    check.set(true);
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                total.set(total.get() + Double.parseDouble(EncryptAndDecrypt.decrypt(arr.getCgpa(), secretKey)));
            }
        });

        double avarage = 0.0;
        if(total.get() > 0.0 && count.get() > 0.0){
            avarage = total.get() / count.get();
        }else {

        }


        TextView fullName = (TextView) findViewById(R.id.resultFullName);
        TextView email = (TextView) findViewById(R.id.resultEmail);
        TextView gender = (TextView) findViewById(R.id.resultGender);
        TextView studentId = (TextView) findViewById(R.id.resultIdentity);
        TextView mobile = (TextView) findViewById(R.id.resultMobile);
        TextView course = (TextView) findViewById(R.id.resultCourse);
        TextView resultCGPA = (TextView) findViewById(R.id.resultCGPA);
        TextView resultGrade = (TextView) findViewById(R.id.resultGrade);
        TextView resultRemarks = (TextView) findViewById(R.id.resultRemarks);
        LinearLayout resultBackgroundColor = (LinearLayout) findViewById(R.id.resultBackgroundColor);

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

            DecimalFormat dec = new DecimalFormat("#0.00");
            String point = dec.format(avarage);
            avarage = Double.parseDouble(point);

            if(avarage >= 4.00){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("A+");
                resultRemarks.setText("Excellent");
            }else if(avarage < 4.00 && avarage >= 3.75){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("A");
                resultRemarks.setText("Better");
            }else if(avarage < 3.75 && avarage >= 3.50){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("A-");
                resultRemarks.setText("Good");
            }else if(avarage < 3.50 && avarage >= 3.25){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("B+");
                resultRemarks.setText("Above Average");
            }else if(avarage < 3.25 && avarage >= 3.00){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("B");
                resultRemarks.setText("Average");
            }else if(avarage < 3.00 && avarage >= 2.75){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("B-");
                resultRemarks.setText("Below Average");
            }else if(avarage < 2.75 && avarage >= 2.50){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("C+");
                resultRemarks.setText("Satisfactory");
            }else if(avarage < 2.50 && avarage >= 2.25){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("C");
                resultRemarks.setText("Not Satisfactory");
            }else if(avarage < 2.25 && avarage >= 2.00){
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("D");
                resultRemarks.setText("Pass");
            }else if(avarage < 2.00){
                resultBackgroundColor.setBackgroundColor(Color.RED);
                resultCGPA.setText(String.valueOf(avarage));
                resultGrade.setText("F");
                resultRemarks.setText("Fail");
            }

            if(check.get()){
                resultBackgroundColor.setBackgroundColor(Color.RED);
                resultCGPA.setText("0.0");
                resultGrade.setText("F");
                resultRemarks.setText("Fail");
            }

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