package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Results;
import com.exam.resultprocess.model.TeacherSetup;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubjectWiseResultActivity extends AppCompatActivity {
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_wise_result);
        setSupportActionBar(findViewById(R.id.toolbarId));

        dbHelper = new DBHelper(this);
        session = new Session(this);

        imageViewProfile = (CircleImageView) findViewById(R.id.profile_image);
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        usernameToolbar = (TextView) findViewById(R.id.usernameToolbar);

        TextView subCode = (TextView) findViewById(R.id.subjectCode);
        TextView subName = (TextView) findViewById(R.id.subjectName);
        TextView semester = (TextView) findViewById(R.id.subjectSemester);

        usernameToolbar.setText(session.get("username"));
        String imagePath = session.get("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);

        String userid = session.get("userid");

        String subjectCode = getIntent().getExtras().getString("subjectCode");
        String batchCode = getIntent().getExtras().getString("batchCode");
        TeacherSetup teacherSetup = dbHelper.getBySubjectCode(userid, batchCode);
        if(subjectCode.contains("PGD")){
            batchCode = "PGDIT " + batchCode;
        }else if(subjectCode.contains("MIT")){
            batchCode = "MIT " + batchCode;
        }

        List<Results> results = dbHelper.getSubjectAndBatchWiseResult(subjectCode, batchCode);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectWiseResultActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        subCode.setText(teacherSetup.getSubjectCode());
        subName.setText(teacherSetup.getSubjectName());
        semester.setText(teacherSetup.getSemester());

        ResultsAdapter adapter = new ResultsAdapter(this, results);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listViewSubjectWise);
        listView.setAdapter(adapter);

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
                Intent intentProfile = new Intent(SubjectWiseResultActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(SubjectWiseResultActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}