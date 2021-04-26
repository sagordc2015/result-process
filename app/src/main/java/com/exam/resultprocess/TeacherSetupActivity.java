package com.exam.resultprocess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TeacherSetupActivity extends AppCompatActivity {

    Spinner spinnerTeacherId, spinnerBatchCode, spinnerCourseName, spinnerSubjectCode;
    TextView teacherName, designation, subjectName;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_setup);

        spinnerTeacherId = (Spinner) findViewById(R.id.spinnerTeacherId);
        spinnerBatchCode = (Spinner) findViewById(R.id.spinnerBatchCode);
        spinnerCourseName = (Spinner) findViewById(R.id.spinnerCourseName);
        spinnerSubjectCode = (Spinner) findViewById(R.id.spinnerSubjectCode);

        teacherName = (TextView) findViewById(R.id.showTeacherName);
        designation = (TextView) findViewById(R.id.showDesignation);
        subjectName = (TextView) findViewById(R.id.showSubjectName);

        submitBtn = (Button) findViewById(R.id.submitTeacherSetup);

        addItemsOnSpinner();

    }
    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        List<String> listTeacherId = new ArrayList<>();
        listTeacherId.add("Select Type");
        listTeacherId.add("01");
        listTeacherId.add("02");

        ArrayAdapter<String> dataAdapterTeacherId = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listTeacherId);
        dataAdapterTeacherId.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeacherId.setAdapter(dataAdapterTeacherId);

        List<String> listBatchCode = new ArrayList<>();
        listBatchCode.add("Select Type");
        listBatchCode.add("03");
        listBatchCode.add("04");

        ArrayAdapter<String> dataAdapterBatchCode = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listBatchCode);
        dataAdapterBatchCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBatchCode.setAdapter(dataAdapterBatchCode);

        List<String> listCourseName = new ArrayList<>();
        listCourseName.add("Select Type");
        listCourseName.add("05");
        listCourseName.add("06");

        ArrayAdapter<String> dataAdapterCourseName = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listCourseName);
        dataAdapterCourseName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourseName.setAdapter(dataAdapterCourseName);

        List<String> listSubjectCode = new ArrayList<>();
        listSubjectCode.add("Select Type");
        listSubjectCode.add("07");
        listSubjectCode.add("08");

        ArrayAdapter<String> dataAdapterSubjectCode = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listSubjectCode);
        dataAdapterSubjectCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubjectCode

                .setAdapter(dataAdapterSubjectCode);

    }
}