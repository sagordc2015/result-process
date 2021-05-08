package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.BatchSetup;
import com.exam.resultprocess.model.Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BatchCreateActivity extends AppCompatActivity {
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

    Spinner course;
    EditText batchCode, batchYear;
    TextView batchYearLevel;
    Button batchSubmit;
    Button batchClear;

    DBHelper dbHelper;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_create);
        setSupportActionBar(findViewById(R.id.toolbarId));

        dbHelper = new DBHelper(this);
        session = new Session(this);
        builder = new AlertDialog.Builder(this);

        imageViewProfile = (CircleImageView) findViewById(R.id.profile_image);
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        usernameToolbar = (TextView) findViewById(R.id.usernameToolbar);

        usernameToolbar.setText(session.get("username"));
        String imagePath = session.get("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);

        course = (Spinner) findViewById(R.id.courseType);
        batchCode = (EditText) findViewById(R.id.batchNumber);
        batchYear = (EditText) findViewById(R.id.batchYear);
        batchYearLevel = (TextView) findViewById(R.id.batchYearLevel);
        batchSubmit = (Button) findViewById(R.id.batchSubmit);
        batchClear = (Button) findViewById(R.id.batchClear);
        batchClear.setBackgroundColor(Color.RED);
        addItemsOnSpinner();

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BatchCreateActivity.this, DashboardActivity.class);

                startActivity(intent);
            }
        });

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                batchCode.setText("");
                if (String.valueOf(course.getSelectedItem()) == "PGDIT") {
                    batchCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                } else if (String.valueOf(course.getSelectedItem()) == "MIT") {
                    batchCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        batchSubmit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDateTime dateObj = LocalDateTime.now();
                DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String formattedDate = dateObj.format(formatObj);
                if(course.getSelectedItem().equals("Select Course Name")){
                    Toast.makeText(BatchCreateActivity.this, "Course name is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(batchCode.getText().toString().equals("")){
                    Toast.makeText(BatchCreateActivity.this, "Batch code is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(course.getSelectedItem()) == "PGDIT" && batchCode.length() < 2){
                    Toast.makeText(BatchCreateActivity.this, "Batch code only 2 digit!!!", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(course.getSelectedItem()) == "MIT" && batchCode.length() < 3){
                    Toast.makeText(BatchCreateActivity.this, "Batch code only 3 digit!!!", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check = dbHelper.checkByBatchCode(batchCode.getText().toString());
                    if(check){
                        Toast.makeText(BatchCreateActivity.this, "Batch code is already Exists!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        builder.setMessage("Welcome to Alert Dialog")
                                .setTitle("Alert Dialog");
                        builder.setMessage("Do you want to create a Batch ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();

                                        BatchSetup batchSetup = new BatchSetup();
                                        batchSetup.setCourseName(String.valueOf(course.getSelectedItem()));
                                        batchSetup.setBatchCode(batchCode.getText().toString());
                                        batchSetup.setBatchName(String.valueOf(course.getSelectedItem()) + " " + batchCode.getText().toString());
                                        batchSetup.setBatchYear(batchYear.getText().toString());
                                        batchSetup.setCreatedOrUpdatedTime(formattedDate);

                                        dbHelper.insertBatchData(batchSetup);

                                        Intent intent = new Intent(BatchCreateActivity.this, BatchCreateActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = builder.create();
                        //Setting the title manually
                        alert.setTitle("Batch Create");
                        alert.show();

                    }
                }
            }
        });

        batchClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.setSelection(0);
                batchCode.setText("");
                batchYear.setText("");
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        List<String> listCourse = new ArrayList<>();
        listCourse.add("Select Course Name");
        listCourse.add("PGDIT");
        listCourse.add("MIT");

        ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(BatchCreateActivity.this,
                android.R.layout.simple_spinner_item, listCourse);
        dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(dataAdapterCourse);

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
                Intent intentProfile = new Intent(BatchCreateActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(BatchCreateActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}