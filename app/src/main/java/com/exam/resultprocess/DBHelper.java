package com.exam.resultprocess;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.exam.resultprocess.model.*;

import java.io.ByteArrayOutputStream;

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
            "extension TEXT)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(users);
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
    }

    public void insertUserData(Users users){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
//            Bitmap imageToStoreBitmap = users.getImage();
//            byteArrayOutputStream = new ByteArrayOutputStream();
//            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            imageBytes = byteArrayOutputStream.toByteArray();

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

            long result = db.insert("Users", null, values);
            if(result != -1){
                Toast.makeText(context, "Data insert successfully", Toast.LENGTH_LONG).show();
                db.close();
            }else{
                Toast.makeText(context, "Fail data insert", Toast.LENGTH_LONG).show();
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
            values.put("mobile", users.getMobile());
            values.put("imageName", users.getImageName());
            values.put("password", users.getPassword());
            values.put("confirmPassword", users.getConfirmPassword());

            long result = db.update("Users", values, "email = ?", new String[]{users.getEmail()});
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

}
