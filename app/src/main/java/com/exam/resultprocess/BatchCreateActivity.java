package com.exam.resultprocess;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.BatchSetup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BatchCreateActivity extends AppCompatActivity {

    Spinner course;
    EditText batchCode, batchName, batchYear;
    TextView batchYearLevel;
    Button batchSubmit;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_create);

        dbHelper = new DBHelper(this);

        course = (Spinner) findViewById(R.id.courseType);
        batchCode = (EditText) findViewById(R.id.batchNumber);
        batchName = (EditText) findViewById(R.id.batchName);
        batchYear = (EditText) findViewById(R.id.batchYear);
        batchYearLevel = (TextView) findViewById(R.id.batchYearLevel);
        batchSubmit = (Button) findViewById(R.id.batchSubmit);
        addItemsOnSpinner();

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(course.getSelectedItem().equals("MIT")){
                    batchYear.setVisibility(View.INVISIBLE);
                    batchYearLevel.setVisibility(View.INVISIBLE);
                }else{
                    batchYear.setVisibility(View.VISIBLE);
                    batchYearLevel.setVisibility(View.VISIBLE);
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
                }else{
                    BatchSetup batchSetup = new BatchSetup();
                    batchSetup.setCourseName(String.valueOf(course.getSelectedItem()));
                    batchSetup.setBatchCode(batchCode.getText().toString());
                    batchSetup.setBatchName(batchName.getText().toString());
                    batchSetup.setBatchYear(batchYear.getText().toString());
                    batchSetup.setCreatedOrUpdatedTime(formattedDate);

                    dbHelper.insertBatchData(batchSetup);
                }
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
}