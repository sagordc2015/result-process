package com.exam.resultprocess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.exam.resultprocess.model.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "ResultProcess.db";
    private static final int DATABASE_VERSION = 1;

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageBytes;

    private String users = "CREATE TABLE Users(" +
            "fullName TEXT, " +
            "email TEXT primary key, " +
            "gender TEXT, " +
            "identity TEXT, " +
            "mobile TEXT, " +
            "type TEXT, " +
            "designationOrCourseName TEXT, " +
            "password TEXT, " +
            "confirmPassword TEXT, " +
            "imageName TEXT, " +
            "extension TEXT, " +
            "createdOrUpdatedTime TEXT)";

    private String results = "CREATE TABLE Results(" +
            "fullName TEXT, " +
            "email TEXT, " +
            "gender TEXT, " +
            "identity TEXT, " +
            "mobile TEXT, " +
            "designationOrCourseName TEXT, " +
            "semester TEXT, " +
            "attendance TEXT, " +
            "assignment TEXT, " +
            "presentation TEXT, " +
            "midTerm TEXT, " +
            "finalMarks TEXT, " +
            "total TEXT, " +
            "cgpa TEXT, " +
            "grade TEXT, " +
            "remarks TEXT, " +
            "createdOrUpdatedTime TEXT)";

    private String batchs = "CREATE TABLE Batchs(" +
            "courseName TEXT, " +
            "batchCode TEXT, " +
            "batchName TEXT, " +
            "batchYear TEXT, " +
            "createdOrUpdatedTime TEXT)";

    private String teacherSetups = "CREATE TABLE TeacherSetups(" +
            "teacherId TEXT, " +
            "teacherName TEXT, " +
            "designation TEXT, " +
            "batchCode TEXT, " +
            "courseName TEXT, " +
            "subjectCode TEXT, " +
            "subjectName TEXT, " +
            "createdOrUpdatedTime TEXT)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(users);
            db.execSQL(results);
            db.execSQL(batchs);
            db.execSQL(teacherSetups);
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Results");
        db.execSQL("DROP TABLE IF EXISTS Batchs");
        db.execSQL("DROP TABLE IF EXISTS TeacherSetups");
    }

    public void insertUserData(Users users){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("fullName", users.getFullName());
            values.put("email", users.getEmail());
            values.put("gender", users.getGender());
            values.put("identity", users.getIdentity());
            values.put("type", users.getType());
            values.put("designationOrCourseName", users.getDesignationOrCourse());
            values.put("mobile", users.getMobile());
            values.put("password", users.getPassword());
            values.put("confirmPassword", users.getConfirmPassword());
            values.put("imageName", users.getImageName());
            values.put("extension", users.getExtension());
            values.put("createdOrUpdatedTime", users.getCreatedOrUpdatedTime());

            long result = db.insert("Users", null, values);
            if(result != -1){
                Toast.makeText(context, "Data insert successfully", Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(context, "Fail data inserted", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertResultData(Results results){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("fullName", results.getFullName());
            values.put("email", results.getEmail());
            values.put("gender", results.getGender());
            values.put("identity", results.getStudentId());
            values.put("mobile", results.getMobile());
            values.put("designationOrCourseName", results.getDesignationOrCourseName());
            values.put("semester", results.getSemester());
            values.put("attendance", results.getAttendance());
            values.put("assignment", results.getAssignment());
            values.put("presentation", results.getPresentation());
            values.put("midTerm", results.getMidTerm());
            values.put("finalMarks", results.getFinalMarks());
            values.put("total", results.getTotal());
            values.put("cgpa", results.getCgpa());
            values.put("grade", results.getGrade());
            values.put("remarks", results.getRemarks());
            values.put("createdOrUpdatedTime", results.getCreatedOrUpdatedTime());

            long result = db.insert("Results", null, values);
            if(result != -1){
                Toast.makeText(context, "Data insert successfully", Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(context, "Fail data inserted", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertBatchData(BatchSetup batchSetup){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("courseName", batchSetup.getCourseName());
            values.put("batchCode", batchSetup.getBatchCode());
            values.put("batchName", batchSetup.getBatchName());
            values.put("batchYear", batchSetup.getBatchYear());
            values.put("createdOrUpdatedTime", batchSetup.getCreatedOrUpdatedTime());

            long result = db.insert("Batchs", null, values);
            if(result != -1){
                Toast.makeText(context, "Data insert successfully", Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(context, "Fail data inserted", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void insertTeacherSetupData(TeacherSetup teacherSetup){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("teacherId", teacherSetup.getTeacherId());
            values.put("teacherName", teacherSetup.getTeacherName());
            values.put("designation", teacherSetup.getDesignation());
            values.put("batchCode", teacherSetup.getBatchCode());
            values.put("courseName", teacherSetup.getCourseName());
            values.put("subjectCode", teacherSetup.getSubjectCode());
            values.put("subjectName", teacherSetup.getSubjectName());
            values.put("createdOrUpdatedTime", teacherSetup.getCreatedOrUpdatedTime());

            long result = db.insert("TeacherSetups", null, values);
            if(result != -1){
                Toast.makeText(context, "Data insert successfully", Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(context, "Fail data inserted", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void updateUserData(Users users){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("fullName", users.getFullName());
            values.put("email", users.getEmail());
            values.put("gender", users.getGender());
            values.put("mobile", users.getMobile());
            values.put("designationOrCourse", users.getDesignationOrCourse());
            values.put("imageName", users.getImageName());
            values.put("password", users.getPassword());
            values.put("confirmPassword", users.getConfirmPassword());

            long result = db.update("Users", values, "identity = ?", new String[]{users.getIdentity()});
            if(result != -1){
                Toast.makeText(context, "Data Update successfully", Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(context, "Fail data update", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public Users getByEmailOrID(String value){
        Users users = new Users();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Users WHERE email = ? OR identity = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{value, value});
            if(cursor.moveToFirst()){
                users.setFullName(cursor.getString(cursor.getColumnIndex("fullName")));
                users.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                users.setGender(cursor.getString(cursor.getColumnIndex("gender")));
                users.setMobile(cursor.getString(cursor.getColumnIndex("mobile")));
                users.setIdentity(cursor.getString(cursor.getColumnIndex("identity")));
                users.setType(cursor.getString(cursor.getColumnIndex("type")));
                users.setDesignationOrCourse(cursor.getString(cursor.getColumnIndex("designationOrCourseName")));
                users.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                users.setConfirmPassword(cursor.getString(cursor.getColumnIndex("confirmPassword")));
                users.setImageName(cursor.getString(cursor.getColumnIndex("imageName")));
                users.setExtension(cursor.getString(cursor.getColumnIndex("extension")));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    public boolean getByEmailOrIdentity(String email, String identity){
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Users WHERE email = ? OR identity = ?";
            cursor = db.rawQuery(sql, new String[]{email, identity});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    // USERS TABLE

    public List<String> getTeacherList(){
        List<String> lists = new ArrayList<>();
        lists.add("Select Teacher");
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Users WHERE type = ? AND identity != ?";
            cursor = db.rawQuery(sql, new String[]{"Teacher", "0000"});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                lists.add(cursor.getString(cursor.getColumnIndex("identity")) + " - " + cursor.getString(cursor.getColumnIndex("fullName")));
            }
            System.out.println("SIZE : " + lists.size());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lists;
    }

    // BATCHS TABLE

    public List<String> getBatchList(){
        List<String> lists = new ArrayList<>();
        lists.add("Select Batch");
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Batchs";
            cursor = db.rawQuery(sql, new String[]{});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                lists.add(cursor.getString(cursor.getColumnIndex("batchCode")));
            }
            System.out.println("SIZE : " + lists.size());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lists;
    }

}
