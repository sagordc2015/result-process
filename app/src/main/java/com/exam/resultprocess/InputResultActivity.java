package com.exam.resultprocess;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Results;
import com.exam.resultprocess.model.Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InputResultActivity extends AppCompatActivity {

    EditText studentId, attendance, assignment, presentation, midTerm, finalMarks;
    TextView fullName, email, mobile, gender, course;
    Spinner semester;
    Button pressBtn, submitBtn;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_result);

        dbHelper = new DBHelper(this);

        studentId = (EditText) findViewById(R.id.studentid);
        attendance = (EditText) findViewById(R.id.showAttendance);
        assignment = (EditText) findViewById(R.id.showAssignment);
        presentation = (EditText) findViewById(R.id.showPresentation);
        midTerm = (EditText) findViewById(R.id.showMidterm);
        finalMarks = (EditText) findViewById(R.id.showFinal);

        fullName = (TextView) findViewById(R.id.showFullName);
        email = (TextView) findViewById(R.id.showEmail);
        mobile = (TextView) findViewById(R.id.showMobile);
        gender = (TextView) findViewById(R.id.showGender);
        course = (TextView) findViewById(R.id.showCourse);

        semester = (Spinner) findViewById(R.id.showSemester);

        pressBtn = (Button) findViewById(R.id.btnPress);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        pressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentId.getText().toString().equals("")){
                    Toast.makeText(InputResultActivity.this, "Please enter Student ID!!!", Toast.LENGTH_SHORT).show();
                }else{
                    Users user = dbHelper.getByEmailOrID(studentId.getText().toString().trim());
                    if(user.equals(null)){
                        Toast.makeText(InputResultActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                    }else{
                        fullName.setText(user.getFullName());
                        email.setText(user.getEmail());
                        mobile.setText(user.getMobile());
                        gender.setText(user.getGender());
                        course.setText(user.getDesignationOrCourse());
                        addItemsOnSpinner();
                    }
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(studentId.getText().toString() == ""){
                    Toast.makeText(InputResultActivity.this, "Student ID is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(String.valueOf(semester.getSelectedItem()) == "Select Semester"){
                    Toast.makeText(InputResultActivity.this, "Semester is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(attendance.getText().toString() == ""){
                    Toast.makeText(InputResultActivity.this, "Attendance is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(attendance.getText().toString()) > 5 || Integer.parseInt(attendance.getText().toString()) < 0){
                    Toast.makeText(InputResultActivity.this, "Attendance marks should be 0 to 5", Toast.LENGTH_SHORT).show();
                }else if(assignment.getText().toString() == ""){
                    Toast.makeText(InputResultActivity.this, "Assignment is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(assignment.getText().toString()) > 10 || Integer.parseInt(assignment.getText().toString()) < 0){
                    Toast.makeText(InputResultActivity.this, "Assignment marks should be 0 to 10", Toast.LENGTH_SHORT).show();
                }else if(presentation.getText().toString() == ""){
                    Toast.makeText(InputResultActivity.this, "Presentation is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(presentation.getText().toString()) > 5 || Integer.parseInt(presentation.getText().toString()) < 0){
                    Toast.makeText(InputResultActivity.this, "Presentation marks should be 0 to 5", Toast.LENGTH_SHORT).show();
                }else if(midTerm.getText().toString() == ""){
                    Toast.makeText(InputResultActivity.this, "Mid-Term is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(midTerm.getText().toString()) > 20 || Integer.parseInt(midTerm.getText().toString()) < 0){
                    Toast.makeText(InputResultActivity.this, "Mid-Term marks should be 0 to 20", Toast.LENGTH_SHORT).show();
                }else if(finalMarks.getText().toString() == ""){
                    Toast.makeText(InputResultActivity.this, "Final exam marks is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(finalMarks.getText().toString()) > 60 || Integer.parseInt(finalMarks.getText().toString()) < 0){
                    Toast.makeText(InputResultActivity.this, "Final exam marks should be 0 to 60", Toast.LENGTH_SHORT).show();
                }else{
                    Results result = new Results();
                    result.setStudentId(studentId.getText().toString());
                    result.setFullName(fullName.getText().toString());
                    result.setEmail(email.getText().toString());
                    result.setGender(gender.getText().toString());
                    result.setMobile(mobile.getText().toString());
                    result.setDesignationOrCourseName(course.getText().toString());
                    result.setSemester(String.valueOf(semester.getSelectedItem()));
                    result.setAttendance(attendance.getText().toString());
                    result.setAssignment(assignment.getText().toString());
                    result.setPresentation(presentation.getText().toString());
                    result.setMidTerm(midTerm.getText().toString());
                    result.setFinalMarks(finalMarks.getText().toString());
                    int att = Integer.parseInt(attendance.getText().toString());
                    int ass = Integer.parseInt(assignment.getText().toString());
                    int pre = Integer.parseInt(presentation.getText().toString());
                    int mid = Integer.parseInt(midTerm.getText().toString());
                    int fnl = Integer.parseInt(finalMarks.getText().toString());
                    int total = att + ass + pre + mid + fnl;
                    result.setTotal(String.valueOf(total));

                    if(total >= 80){
                        result.setCgpa("4.00");
                        result.setGrade("A+");
                        result.setRemarks("Excellent");
                    }else if(total < 80 && total >= 75){
                        result.setCgpa("3.75");
                        result.setGrade("A");
                        result.setRemarks("Better");
                    }else if(total < 75 && total >= 70){
                        result.setCgpa("3.50");
                        result.setGrade("A-");
                        result.setRemarks("Good");
                    }else if(total < 70 && total >= 65){
                        result.setCgpa("3.25");
                        result.setGrade("B+");
                        result.setRemarks("Above Average");
                    }else if(total < 65 && total >= 60){
                        result.setCgpa("3.00");
                        result.setGrade("B");
                        result.setRemarks("Average");
                    }else if(total < 60 && total >= 55){
                        result.setCgpa("2.75");
                        result.setGrade("B-");
                        result.setRemarks("Below Average");
                    }else if(total < 55 && total >= 50){
                        result.setCgpa("2.50");
                        result.setGrade("C+");
                        result.setRemarks("Satisfactory");
                    }else if(total < 50 && total >= 45){
                        result.setCgpa("2.25");
                        result.setGrade("C");
                        result.setRemarks("Not Satisfactory");
                    }else if(total < 45 && total >= 40){
                        result.setCgpa("2.00");
                        result.setGrade("D");
                        result.setRemarks("Pass");
                    }else if(total < 40){
                        result.setCgpa("0.00");
                        result.setGrade("F");
                        result.setRemarks("Fail");
                    }

                    LocalDateTime dateObj = LocalDateTime.now();
                    DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = dateObj.format(formatObj);
                    result.setCreatedOrUpdatedTime(formattedDate);

                    dbHelper.insertResultData(result);
                }
            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        List<String> listSemester = new ArrayList<>();
        listSemester.add("Select Semester");
        if(course.getText().toString().equals("PGDIT")){
            listSemester.add("1st Trimester");
            listSemester.add("2nd Trimester");
            listSemester.add("3rd Trimester");
        }else if(course.getText().toString().equals("MIT")){
            listSemester.add("1st Semester");
            listSemester.add("2nd Semester");
            listSemester.add("3rd Semester");
            listSemester.add("4th Semester");
        }

        ArrayAdapter<String> dataAdapterSemester = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listSemester);
        dataAdapterSemester.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semester.setAdapter(dataAdapterSemester);

    }
}