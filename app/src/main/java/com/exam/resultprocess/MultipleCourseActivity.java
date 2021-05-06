package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.BatchSetup;
import com.exam.resultprocess.model.Results;
import com.exam.resultprocess.model.TeacherSetup;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MultipleCourseActivity extends AppCompatActivity {
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

    CardView cardViewPGDITResult;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_course);
        setSupportActionBar(findViewById(R.id.toolbarId));

        dbHelper = new DBHelper(this);
        session = new Session(this);

        imageViewProfile = (CircleImageView) findViewById(R.id.profile_image);
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        usernameToolbar = (TextView) findViewById(R.id.usernameToolbar);

        cardViewPGDITResult = (CardView) findViewById(R.id.cardViewPGDITResult);

        usernameToolbar.setText(session.get("username"));
        String imagePath = session.get("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);

        String userid = getIntent().getExtras().getString("identity");

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MultipleCourseActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        if(userid.equals("123456")){
            List<BatchSetup> batchSetups = dbHelper.getBatchsList();

            BatchListviewAdapter adapter = new BatchListviewAdapter(this, batchSetups);

            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.listViewBatch);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView batchName = (TextView) view.findViewById(R.id.listViewBatchName);
                    String[] name = batchName.getText().toString().split(" ");
                    Intent intent = new Intent(MultipleCourseActivity.this, CourseListviewActivity.class);
                    intent.putExtra("courseName", batchName.getText().toString());
                    startActivity(intent);
                }
            });
        }else{
            List<TeacherSetup> teacherSetups = dbHelper.getTeacherSetupList(userid);

            TeacherListviewAdapter adapter = new TeacherListviewAdapter(this, teacherSetups);

            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.listViewBatch);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView batchName = (TextView) view.findViewById(R.id.listViewBatchName);
                    Intent intent = new Intent(MultipleCourseActivity.this, SubjectWiseResultActivity.class);
                    String name = batchName.getText().toString();
                    String[] arr = name.split(" : ");
                    String code = arr[0];
                    String subject = arr[1];
                    String[] a = code.split(" - ");
                    String batchCode = a[1];
                    intent.putExtra("subjectCode", subject);
                    intent.putExtra("batchCode", batchCode);
                    startActivity(intent);
                }
            });
        }

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
                Intent intentProfile = new Intent(MultipleCourseActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(MultipleCourseActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}