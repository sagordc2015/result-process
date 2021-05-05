package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.BatchSetup;
import com.exam.resultprocess.model.Results;
import com.exam.resultprocess.model.TeacherSetup;
import com.exam.resultprocess.model.Users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InputResultActivity extends AppCompatActivity {

    final String secretKey = "ssshhhhhhhhhhh!!!!";
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

    EditText studentId, attendance, assignment, presentation, midTerm, finalMarks;
    TextView fullName, email, mobile, gender, course, showSubjectCode, showSemester;
    Button pressBtn, submitBtn;

    DBHelper dbHelper;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_result);
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
        showSubjectCode = (TextView) findViewById(R.id.showSubjectCode);
        showSemester = (TextView) findViewById(R.id.showSemester);

        pressBtn = (Button) findViewById(R.id.btnPress);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputResultActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        pressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentId.getText().toString().equals("")){
                    Toast.makeText(InputResultActivity.this, "Please enter Student ID!!!", Toast.LENGTH_SHORT).show();
                }else{
                    Users user = dbHelper.getByEmailOrID(studentId.getText().toString().trim());
                    if(user.getIdentity().equals("")){
                        Toast.makeText(InputResultActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                    }else{
                        fullName.setText(user.getFullName());
                        email.setText(user.getEmail());
                        mobile.setText(user.getMobile());
                        gender.setText(user.getGender());
                        course.setText(user.getDesignationOrCourse());
                        String teacherId = session.get("userid");
                        String batchCode = studentId.getText().toString().substring(0, studentId.length() - 2);
                        TeacherSetup teacherSetup = dbHelper.getBySubjectCode(teacherId, batchCode);
                        showSubjectCode.setText(teacherSetup.getSubjectCode());
                        showSemester.setText(teacherSetup.getSemester());
                    }
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                boolean check = dbHelper.checkByResult(studentId.getText().toString(), showSubjectCode.getText().toString());
                if(check){
                    Toast.makeText(InputResultActivity.this, "This data are already exists!!!", Toast.LENGTH_SHORT).show();
                }else{
                    if(studentId.getText().toString() == ""){
                        Toast.makeText(InputResultActivity.this, "Student ID is Required!!!", Toast.LENGTH_SHORT).show();
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
                        builder.setMessage("Welcome to Alert Dialog")
                                .setTitle("Alert Dialog");
                        builder.setMessage("Do you want to create a Result ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        Results result = new Results();
                                        result.setStudentId(studentId.getText().toString());
                                        result.setDesignationOrCourseName(course.getText().toString());
                                        result.setSemester(showSemester.getText().toString());
                                        result.setAttendance(EncryptAndDecrypt.encrypt(attendance.getText().toString().trim(), secretKey));
                                        result.setAssignment(EncryptAndDecrypt.encrypt(assignment.getText().toString().trim(), secretKey));
                                        result.setPresentation(EncryptAndDecrypt.encrypt(presentation.getText().toString().trim(), secretKey));
                                        result.setMidTerm(EncryptAndDecrypt.encrypt(midTerm.getText().toString().trim(), secretKey));
                                        result.setFinalMarks(EncryptAndDecrypt.encrypt(finalMarks.getText().toString().trim(), secretKey));
                                        int att = Integer.parseInt(attendance.getText().toString());
                                        int ass = Integer.parseInt(assignment.getText().toString());
                                        int pre = Integer.parseInt(presentation.getText().toString());
                                        int mid = Integer.parseInt(midTerm.getText().toString());
                                        int fnl = Integer.parseInt(finalMarks.getText().toString());
                                        int total = att + ass + pre + mid + fnl;
                                        result.setTotal(EncryptAndDecrypt.encrypt(String.valueOf(total), secretKey));

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

                                        result.setCgpa(EncryptAndDecrypt.encrypt(result.getCgpa(), secretKey));
                                        result.setSubjectCode(showSubjectCode.getText().toString());

                                        LocalDateTime dateObj = LocalDateTime.now();
                                        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                        String formattedDate = dateObj.format(formatObj);
                                        result.setCreatedOrUpdatedTime(formattedDate);

                                        dbHelper.insertResultData(result);

                                        Intent intent = new Intent(InputResultActivity.this, InputResultActivity.class);
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
                        alert.setTitle("Input Result");
                        alert.show();
                    }
                }
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
                Intent intentProfile = new Intent(InputResultActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(InputResultActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}