package com.exam.resultprocess;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class TeacherSetupActivity extends AppCompatActivity {

    Spinner spinnerTeacherId, spinnerBatchCode, spinnerCourseName, spinnerSubjectCode;
    TextView teacherName, designation;
    Button submitBtn;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_setup);

        dbHelper = new DBHelper(this);

        spinnerTeacherId = (Spinner) findViewById(R.id.spinnerTeacherId);
        spinnerBatchCode = (Spinner) findViewById(R.id.spinnerBatchCode);
        spinnerCourseName = (Spinner) findViewById(R.id.spinnerCourseName);
        spinnerSubjectCode = (Spinner) findViewById(R.id.spinnerSubjectCode);

        teacherName = (TextView) findViewById(R.id.showTeacherName);
        designation = (TextView) findViewById(R.id.showDesignation);

        submitBtn = (Button) findViewById(R.id.submitTeacherSetup);

        addItemsOnSpinner();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                
                if(spinnerTeacherId.getSelectedItem() == "Select Teacher"){
                    Toast.makeText(TeacherSetupActivity.this, "Teacher ID is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(spinnerBatchCode.getSelectedItem() == "Select Batch"){
                    Toast.makeText(TeacherSetupActivity.this, "Batch is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(spinnerCourseName.getSelectedItem() == "Select Type"){
                    Toast.makeText(TeacherSetupActivity.this, "Course Name is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(spinnerSubjectCode.getSelectedItem() == "Select Subject"){
                    Toast.makeText(TeacherSetupActivity.this, "Subject is Required!!!", Toast.LENGTH_SHORT).show();
                }else {
                    TeacherSetup teacherSetup = new TeacherSetup();
                    teacherSetup.setTeacherId(String.valueOf(spinnerTeacherId.getSelectedItem()));
                    teacherSetup.setTeacherName(teacherName.getText().toString());
                    teacherSetup.setDesignation(designation.getText().toString());
                    teacherSetup.setBatchCode(String.valueOf(spinnerBatchCode.getSelectedItem()));
                    teacherSetup.setCourseName(String.valueOf(spinnerCourseName.getSelectedItem()));
                    String codeName = (String)spinnerSubjectCode.getSelectedItem();
                    String[] subject = codeName.split(" - ", 0);
                    teacherSetup.setSubjectCode(subject[0]);
                    teacherSetup.setSubjectName(subject[1]);

                    LocalDateTime dateObj = LocalDateTime.now();
                    DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = dateObj.format(formatObj);
                    teacherSetup.setCreatedOrUpdatedTime(formattedDate);

                    dbHelper.insertTeacherSetupData(teacherSetup);
                }
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

        List<String> listBatchCode = dbHelper.getBatchList();

        ArrayAdapter<String> dataAdapterBatchCode = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listBatchCode);
        dataAdapterBatchCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBatchCode.setAdapter(dataAdapterBatchCode);

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
                listSubjectCode.add("Select Subject");
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
                }

                ArrayAdapter<String> dataAdapterSubjectCode = new ArrayAdapter<String>(TeacherSetupActivity.this,
                        android.R.layout.simple_spinner_item, listSubjectCode);
                dataAdapterSubjectCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSubjectCode.setAdapter(dataAdapterSubjectCode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}