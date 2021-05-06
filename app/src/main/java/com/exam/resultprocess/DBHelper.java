package com.exam.resultprocess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.exam.resultprocess.model.*;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "ResultProcess.db";
    private static final int DATABASE_VERSION = 1;

    private String users = "CREATE TABLE Users(" +
            "fullName TEXT, " +
            "email TEXT primary key, " +
            "gender TEXT, " +
            "identity TEXT, " +
            "mobile TEXT, " +
            "type TEXT, " +
            "batchCode TEXT, " +
            "designationOrCourseName TEXT, " +
            "password TEXT, " +
            "confirmPassword TEXT, " +
            "imageName TEXT, " +
            "extension TEXT, " +
            "createdOrUpdatedTime TEXT)";

    private String results = "CREATE TABLE Results(" +
            "identity TEXT, " +
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
            "subjectCode TEXT, " +
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
            "semester TEXT, " +
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
            values.put("batchCode", users.getBatchCode());
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
            values.put("identity", results.getStudentId());
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
            values.put("subjectCode", results.getSubjectCode());
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
            values.put("semester", teacherSetup.getSemester());
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
            values.put("mobile", users.getMobile());
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
                users.setBatchCode(cursor.getString(cursor.getColumnIndex("batchCode")));
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

    public boolean getByEmail(String email){
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Users WHERE email = ?";
            cursor = db.rawQuery(sql, new String[]{email});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public List<String> getTeacherList(){
        List<String> lists = new ArrayList<>();
        lists.add("Select Teacher");
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Users WHERE type = ? AND identity != ? ORDER BY identity";
            cursor = db.rawQuery(sql, new String[]{"Teacher", "123456"});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                lists.add(cursor.getString(cursor.getColumnIndex("identity")) + " - " + cursor.getString(cursor.getColumnIndex("fullName")));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lists;
    }

    // BATCHS TABLE

    public List<String> getBatchList(String courseName){
        List<String> lists = new ArrayList<>();
        lists.add("Select Batch");
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Batchs WHERE courseName = ? ORDER BY batchCode";
            cursor = db.rawQuery(sql, new String[]{courseName});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                lists.add(cursor.getString(cursor.getColumnIndex("batchCode")));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lists;
    }

    public List<BatchSetup> getBatchsList(){
        List<BatchSetup> lists = new ArrayList<>();
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Batchs ORDER BY batchCode";
            cursor = db.rawQuery(sql, new String[]{});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                BatchSetup batchSetup = new BatchSetup();
                batchSetup.setBatchCode(cursor.getString(cursor.getColumnIndex("batchCode")));
                batchSetup.setBatchName(cursor.getString(cursor.getColumnIndex("batchName")));
                batchSetup.setBatchYear(cursor.getString(cursor.getColumnIndex("batchYear")));
                batchSetup.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
                lists.add(batchSetup);
            }
            System.out.println(lists.size() + " ******");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lists;
    }

    public List<TeacherSetup> getTeacherSetupList(String teacherId){
        List<TeacherSetup> lists = new ArrayList<>();
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM TeacherSetups WHERE teacherId = ? ORDER BY teacherId";
            cursor = db.rawQuery(sql, new String[]{teacherId});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                TeacherSetup teacherSetup = new TeacherSetup();
                teacherSetup.setTeacherId(cursor.getString(cursor.getColumnIndex("teacherId")));
                teacherSetup.setTeacherName(cursor.getString(cursor.getColumnIndex("teacherName")));
                teacherSetup.setBatchCode(cursor.getString(cursor.getColumnIndex("batchCode")));
                teacherSetup.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
                teacherSetup.setSubjectCode(cursor.getString(cursor.getColumnIndex("subjectCode")));
                teacherSetup.setSubjectName(cursor.getString(cursor.getColumnIndex("subjectName")));
                teacherSetup.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
                lists.add(teacherSetup);
            }
            System.out.println(lists.size() + " ---------- ");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return lists;
    }

    public boolean checkByBatchCode(String batchCode){
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Batchs WHERE batchCode = ?";
            cursor = db.rawQuery(sql, new String[]{batchCode});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean checkByTeacher(String batchCode, String subjectCode){
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM TeacherSetups WHERE batchCode = ? AND subjectCode = ?";
            cursor = db.rawQuery(sql, new String[]{batchCode, subjectCode});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean checkByResult(String identity, String subjectCode){
        Cursor cursor = null;
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Results WHERE identity = ? AND subjectCode = ?";
            cursor = db.rawQuery(sql, new String[]{identity, subjectCode});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public TeacherSetup getBySubjectCode(String teacherId, String batchCode) {
        TeacherSetup teacherSetup = new TeacherSetup();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM TeacherSetups WHERE teacherId = ? AND batchCode = ?";
            Cursor cursor = db.rawQuery(sql, new String[]{teacherId, batchCode});
            if(cursor.moveToFirst()){
                teacherSetup.setSubjectCode(cursor.getString(cursor.getColumnIndex("subjectCode")));
                teacherSetup.setSubjectName(cursor.getString(cursor.getColumnIndex("subjectName")));
                teacherSetup.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return teacherSetup;
    }

    public List<Results> getByIdentity(String identity) {
        List<Results> resultList = new ArrayList<>();
        Users users = this.getByEmailOrID(identity);
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Results WHERE identity = ? ORDER BY identity";
            Cursor cursor = db.rawQuery(sql, new String[]{identity});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Results results = new Results();

                results.setFullName(users.getFullName());
                results.setEmail(users.getEmail());
                results.setGender(users.getGender());
                results.setStudentId(users.getIdentity());
                results.setMobile(users.getMobile());
                results.setDesignationOrCourseName(users.getDesignationOrCourse());
//                results.setStudentId(cursor.getString(cursor.getColumnIndex("identity")));
                results.setDesignationOrCourseName(cursor.getString(cursor.getColumnIndex("designationOrCourseName")));
                results.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
                results.setAttendance(cursor.getString(cursor.getColumnIndex("attendance")));
                results.setAssignment(cursor.getString(cursor.getColumnIndex("assignment")));
                results.setPresentation(cursor.getString(cursor.getColumnIndex("presentation")));
                results.setMidTerm(cursor.getString(cursor.getColumnIndex("midTerm")));
                results.setFinalMarks(cursor.getString(cursor.getColumnIndex("finalMarks")));
                results.setTotal(cursor.getString(cursor.getColumnIndex("total")));
                results.setCgpa(cursor.getString(cursor.getColumnIndex("cgpa")));
                results.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                results.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                results.setSubjectCode(cursor.getString(cursor.getColumnIndex("subjectCode")));
                resultList.add(results);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public List<Results> getSubjectAndBatchWiseResult(String subjectCode, String batchCode) {
        List<Results> resultList = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Results WHERE subjectCode = ? AND designationOrCourseName = ? ORDER BY identity";
            Cursor cursor = db.rawQuery(sql, new String[]{subjectCode, batchCode});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Results results = new Results();
                Users users = this.getByEmailOrID(cursor.getString(cursor.getColumnIndex("identity")));
                results.setFullName(users.getFullName());
                results.setDesignationOrCourseName(cursor.getString(cursor.getColumnIndex("designationOrCourseName")));
                results.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
                results.setAttendance(cursor.getString(cursor.getColumnIndex("attendance")));
                results.setAssignment(cursor.getString(cursor.getColumnIndex("assignment")));
                results.setPresentation(cursor.getString(cursor.getColumnIndex("presentation")));
                results.setMidTerm(cursor.getString(cursor.getColumnIndex("midTerm")));
                results.setFinalMarks(cursor.getString(cursor.getColumnIndex("finalMarks")));
                results.setTotal(cursor.getString(cursor.getColumnIndex("total")));
                results.setCgpa(cursor.getString(cursor.getColumnIndex("cgpa")));
                results.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                results.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
//                results.setSubjectCode(cursor.getString(cursor.getColumnIndex("subjectCode")));
                results.setSubjectCode(users.getFullName());
                resultList.add(results);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public List<Results> getCourseWiseResults(String designationOrCourseName) {
        List<Results> resultList = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM Results WHERE designationOrCourseName = ? ORDER BY identity";
            Cursor cursor = db.rawQuery(sql, new String[]{designationOrCourseName});
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                Results results = new Results();
                Users users = this.getByEmailOrID(cursor.getString(cursor.getColumnIndex("identity")));
                results.setFullName(users.getFullName());
                results.setStudentId(users.getIdentity());
//                results.setStudentId(cursor.getString(cursor.getColumnIndex("identity")));
                results.setDesignationOrCourseName(cursor.getString(cursor.getColumnIndex("designationOrCourseName")));
                results.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
                results.setAttendance(cursor.getString(cursor.getColumnIndex("attendance")));
                results.setAssignment(cursor.getString(cursor.getColumnIndex("assignment")));
                results.setPresentation(cursor.getString(cursor.getColumnIndex("presentation")));
                results.setMidTerm(cursor.getString(cursor.getColumnIndex("midTerm")));
                results.setFinalMarks(cursor.getString(cursor.getColumnIndex("finalMarks")));
                results.setTotal(cursor.getString(cursor.getColumnIndex("total")));
                results.setCgpa(cursor.getString(cursor.getColumnIndex("cgpa")));
                results.setGrade(cursor.getString(cursor.getColumnIndex("grade")));
                results.setRemarks(cursor.getString(cursor.getColumnIndex("remarks")));
                results.setSubjectCode(cursor.getString(cursor.getColumnIndex("subjectCode")));
                resultList.add(results);
            }
            System.out.println(resultList.size() + " //////");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return resultList;
    }

}
