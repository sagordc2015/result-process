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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Results;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class CourseListviewActivity extends AppCompatActivity {
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

    DBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_listview);
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

        String courseName =(String) getIntent().getExtras().get("courseName");

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseListviewActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        List<Results> results = dbHelper.getCourseWiseResults(courseName);

        List<Results> unique = results.stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(Results::getStudentId))),ArrayList::new));

        CourseListviewAdapter adapter = new CourseListviewAdapter(this, unique);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listViewCourse);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView fullName = (TextView) view.findViewById(R.id.listview_fullName);
                TextView identity = (TextView) view.findViewById(R.id.listview_identity);
                Intent intent = new Intent(CourseListviewActivity.this, ShowResultActivity.class);
                intent.putExtra("identity", identity.getText().toString());
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
                Intent intentProfile = new Intent(CourseListviewActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(CourseListviewActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}