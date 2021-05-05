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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.BatchSetup;
import com.exam.resultprocess.model.TeacherSetup;
import com.exam.resultprocess.model.Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherSetupActivity extends AppCompatActivity {
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

    Spinner spinnerTeacherId, spinnerBatchCode, spinnerCourseName, spinnerSubjectCode, spinnerSemester;
    TextView teacherName, designation;
    Button submitBtn, clearBtn;

    DBHelper dbHelper;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_setup);
        setSupportActionBar(findViewById(R.id.toolbarId));

        dbHelper = new DBHelper(this);
        session = new Session(this);
        builder = new AlertDialog.Builder(this);

        imageViewProfile = (CircleImageView) findViewById(R.id.profile_image);
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        usernameToolbar = (TextView) findViewById(R.id.usernameToolbar);

        spinnerTeacherId = (Spinner) findViewById(R.id.spinnerTeacherId);
        spinnerBatchCode = (Spinner) findViewById(R.id.spinnerBatchCode);
        spinnerCourseName = (Spinner) findViewById(R.id.spinnerCourseName);
        spinnerSubjectCode = (Spinner) findViewById(R.id.spinnerSubjectCode);
        spinnerSemester = (Spinner) findViewById(R.id.spinnerSemester);

        teacherName = (TextView) findViewById(R.id.showTeacherName);
        designation = (TextView) findViewById(R.id.showDesignation);

        submitBtn = (Button) findViewById(R.id.submitTeacherSetup);
        clearBtn = (Button) findViewById(R.id.clearTeacherSetup);
        clearBtn.setBackgroundColor(Color.RED);

        usernameToolbar.setText(session.get("username"));
        String imagePath = session.get("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);

        addItemsOnSpinner();

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherSetupActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String codeName = (String)spinnerSubjectCode.getSelectedItem();
                String[] subject = codeName.split(" - ", 0);
                boolean check = dbHelper.checkByTeacher(String.valueOf(spinnerBatchCode.getSelectedItem()), subject[0]);
                if(check){
                    Toast.makeText(TeacherSetupActivity.this, "This subject are already exists!!!", Toast.LENGTH_SHORT).show();
                }else{
                    if(spinnerTeacherId.getSelectedItem() == "Select Teacher"){
                        Toast.makeText(TeacherSetupActivity.this, "Teacher ID is Required!!!", Toast.LENGTH_SHORT).show();
                    }else if(spinnerBatchCode.getSelectedItem() == "Select Batch"){
                        Toast.makeText(TeacherSetupActivity.this, "Batch is Required!!!", Toast.LENGTH_SHORT).show();
                    }else if(spinnerCourseName.getSelectedItem() == "Select Type"){
                        Toast.makeText(TeacherSetupActivity.this, "Course Name is Required!!!", Toast.LENGTH_SHORT).show();
                    }else if(spinnerSubjectCode.getSelectedItem() == "Select Subject"){
                        Toast.makeText(TeacherSetupActivity.this, "Subject is Required!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        builder.setMessage("Welcome to Alert Dialog")
                                .setTitle("Alert Dialog");
                        builder.setMessage("Do you want to create a Teacher setup ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        TeacherSetup teacherSetup = new TeacherSetup();
                                        String teacher = String.valueOf(spinnerTeacherId.getSelectedItem());
                                        String[] teacherArr = teacher.split(" - ", 0);
                                        teacherSetup.setTeacherId(teacherArr[0]);
                                        teacherSetup.setTeacherName(teacherName.getText().toString());
                                        teacherSetup.setDesignation(designation.getText().toString());
                                        teacherSetup.setBatchCode(String.valueOf(spinnerBatchCode.getSelectedItem()));
                                        teacherSetup.setCourseName(String.valueOf(spinnerCourseName.getSelectedItem()));
                                        teacherSetup.setSubjectCode(subject[0]);
                                        teacherSetup.setSubjectName(subject[1]);
                                        teacherSetup.setSemester(String.valueOf(spinnerSemester.getSelectedItem()));

                                        LocalDateTime dateObj = LocalDateTime.now();
                                        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                        String formattedDate = dateObj.format(formatObj);
                                        teacherSetup.setCreatedOrUpdatedTime(formattedDate);

                                        dbHelper.insertTeacherSetupData(teacherSetup);

                                        Intent intent = new Intent(TeacherSetupActivity.this, TeacherSetupActivity.class);
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
                        alert.setTitle("Teacher Setup");
                        alert.show();
                    }
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerTeacherId.setSelection(0);
                teacherName.setText("");
                designation.setText("");
                spinnerCourseName.setSelection(0);
                spinnerBatchCode.setSelection(0);
                spinnerSubjectCode.setSelection(0);
                spinnerSemester.setSelection(0);
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        List<String> listTeacherId = dbHelper.getTeacherList();

        ArrayAdapter<String> dataAdapterTeacherId = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listTeacherId);
        dataAdapterTeacherId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacherId.setAdapter(dataAdapterTeacherId);

        List<String> listCourseName = new ArrayList<>();
        listCourseName.add("Select Type");
        listCourseName.add("PGDIT");
        listCourseName.add("MIT");

        ArrayAdapter<String> dataAdapterCourseName = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listCourseName);
        dataAdapterCourseName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourseName.setAdapter(dataAdapterCourseName);

        addListenerOnButton();

    }

    // get the selected dropdown list value
    public void addListenerOnButton() {
        spinnerTeacherId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerTeacher = (String)spinnerTeacherId.getSelectedItem();
                String[] teacherArr = spinnerTeacher.split(" - ", 0);
                String teacherId = teacherArr[0];
                Users teacher = dbHelper.getByEmailOrID(teacherId);
                teacherName.setText(teacher.getFullName());
                designation.setText(teacher.getDesignationOrCourse());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCourseName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> listSubjectCode = new ArrayList<>();
                List<String> listSemester = new ArrayList<>();
                listSubjectCode.add("Select Subject");
                listSemester.add("Select Semester");
                if (String.valueOf(spinnerCourseName.getSelectedItem()) == "PGDIT") {
                    listSubjectCode.add("PGD 101 - Computer Fundamentals and Office Automation");
                    listSubjectCode.add("PGD 104 - Structured Programming");
                    listSubjectCode.add("PGD 105 - Introduction to Software Engineering");
                    listSubjectCode.add("PGD 106 - Operating System Concepts & UNIX OS");
                    listSubjectCode.add("PGD 107 - Internet Programming");
                    listSubjectCode.add("PGD 201 - Data Structure & Algorithm");
                    listSubjectCode.add("PGD 202 - Object Oriented Programming");
                    listSubjectCode.add("PGD 203 - Data Communication and Computer Networks");
                    listSubjectCode.add("PGD 204 - DBMS & XML");
                    listSubjectCode.add("PGD 206 - Micro-controller & Embedded System");
                    listSubjectCode.add("PGD 207 - Mobile Application");
                    listSubjectCode.add("PGD 208 - Net Technology");
                    listSubjectCode.add("PGD 210 - Project for PGDIT");

                    listSemester.add("1st Trimester");
                    listSemester.add("2nd Trimester");
                    listSemester.add("3rd Trimester");

                }else if(String.valueOf(spinnerCourseName.getSelectedItem()) == "MIT"){
                    listSubjectCode.add("MITM301 - Project Management and Business Info System");
                    listSubjectCode.add("MITM302 - Computer Programming");
                    listSubjectCode.add("MITM303 - Client Server Technology and System Programming");
                    listSubjectCode.add("MITM304 - Database Architecture and Administration");
                    listSubjectCode.add("MITM305 - Internet Computing");
                    listSubjectCode.add("MITM306 - Advanced Computer Networks & Internetworking");
                    listSubjectCode.add("MITE401 - Data Mining and Warehousing");
                    listSubjectCode.add("MITE402 - E-Commerce Technologies in E-Business");
                    listSubjectCode.add("MITE403 - Computer, Data, Network Security/E-Security");
                    listSubjectCode.add("MITE404 - Parallel and Distributed Processing");
                    listSubjectCode.add("MITE405 - Computer Graphics and Multimedia");
                    listSubjectCode.add("MITE406 - Simulation and Modeling");
                    listSubjectCode.add("MITE407 - Mobile and Cellular Communications");
                    listSubjectCode.add("MITE408 - Decision Support System and Intelligent Systems");
                    listSubjectCode.add("MITE409 - E-Governance");
                    listSubjectCode.add("MITE410 - Business System");
                    listSubjectCode.add("MITE411 - Bioinformatics");
                    listSubjectCode.add("MITE412 - Advanced Object Oriented Programming");
                    listSubjectCode.add("MITE413 - Strategic Management");
                    listSubjectCode.add("MITE414 - Software Testing");
                    listSubjectCode.add("MITP421 - Project for MIT");
                    listSubjectCode.add("MITI422 - Industrial Attachment");

                    listSemester.add("1st Semester");
                    listSemester.add("2nd Semester");
                    listSemester.add("3rd Semester");
                    listSemester.add("4th Semester");

                }

                List<String> listBatchCode = dbHelper.getBatchList(String.valueOf(spinnerCourseName.getSelectedItem()));

                ArrayAdapter<String> dataAdapterBatchCode = new ArrayAdapter<String>(TeacherSetupActivity.this,
                        android.R.layout.simple_spinner_item, listBatchCode);
                dataAdapterBatchCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerBatchCode.setAdapter(dataAdapterBatchCode);

                ArrayAdapter<String> dataAdapterSubjectCode = new ArrayAdapter<String>(TeacherSetupActivity.this,
                        android.R.layout.simple_spinner_item, listSubjectCode);
                dataAdapterSubjectCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubjectCode.setAdapter(dataAdapterSubjectCode);

                ArrayAdapter<String> dataAdapterSemester = new ArrayAdapter<String>(TeacherSetupActivity.this,
                        android.R.layout.simple_spinner_item, listSemester);
                dataAdapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSemester.setAdapter(dataAdapterSemester);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                Intent intentProfile = new Intent(TeacherSetupActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(TeacherSetupActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}