package com.exam.resultprocess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Users;

public class InputResultActivity extends AppCompatActivity {

    EditText studentId, attendence, assignment, presentation, midTerm, finalMarks;
    TextView fullName, email, mobile, gender, course, total;
    Spinner semester;
    Button pressBtn, submitBtn;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_result);

        dbHelper = new DBHelper(this);

        studentId = (EditText) findViewById(R.id.studentid);
        attendence = (EditText) findViewById(R.id.showAttendence);
        assignment = (EditText) findViewById(R.id.showAssignment);
        presentation = (EditText) findViewById(R.id.showPresentation);
        midTerm = (EditText) findViewById(R.id.showMidterm);
        finalMarks = (EditText) findViewById(R.id.showFinal);

        fullName = (TextView) findViewById(R.id.showFullName);
        email = (TextView) findViewById(R.id.showEmail);
        mobile = (TextView) findViewById(R.id.showMobile);
        gender = (TextView) findViewById(R.id.showGender);
        course = (TextView) findViewById(R.id.showCourse);
        total = (TextView) findViewById(R.id.showTotal);

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
                    }
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}