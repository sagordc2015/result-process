package com.exam.resultprocess;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Users;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    final String secretKey = "ssshhhhhhhhhhh!!!!";

    EditText editFullName, editEmail, editMobile, editPassword, editConfirmPassword;
    TextView editGender, editBatchCode, editIdentity, editType;
    Spinner editDesignationOrCourseName;
    CircleImageView imageView;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private Bitmap imageToStore;

    Button updateBtn;

    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editFullName = (EditText) findViewById(R.id.editFullName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editIdentity = (TextView) findViewById(R.id.editIdentity);
        editType = (TextView) findViewById(R.id.editType);
        editGender = (TextView) findViewById(R.id.editGender);
        editBatchCode = (TextView) findViewById(R.id.editBatchCode);
        editMobile = (EditText) findViewById(R.id.editMobile);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);

        updateBtn = (Button) findViewById(R.id.profileUpdateBtn);

        dbHelper = new DBHelper(this);

        addItemsOnSpinner();

        imageView = (CircleImageView) findViewById(R.id.edit_upload_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                CropImage.startPickImageActivity(EditProfileActivity.this);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (editFullName.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Please Full Name is Required!!!", Toast.LENGTH_LONG).show();
                } else if (editEmail.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Please Email is Required!!!", Toast.LENGTH_LONG).show();
                } else if (editPassword.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Please Password is Required!!!", Toast.LENGTH_LONG).show();
                } else if (editConfirmPassword.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Please Confirm Password is Required!!!", Toast.LENGTH_LONG).show();
                } else if (!editPassword.getText().toString().equals(editConfirmPassword.getText().toString())) {
                    Toast.makeText(EditProfileActivity.this, "Password not Match", Toast.LENGTH_LONG).show();
                } else {
                    /*boolean checkUser = dbHelper.getByEmailOrIdentity(editEmail.getText().toString(), editIdentity.getText().toString());
                    if (checkUser) {
                        Toast.makeText(EditProfileActivity.this, "Email Or ID Already Exist!!!", Toast.LENGTH_SHORT).show();
                    } else {*/
                        Users users = new Users();
                        users.setFullName(editFullName.getText().toString().trim());
                        users.setEmail(editEmail.getText().toString().trim());
                        users.setGender(editGender.getText().toString());
                        users.setBatchCode(editBatchCode.getText().toString().trim());
                        users.setIdentity(editIdentity.getText().toString().trim());
                        users.setType(editType.getText().toString());
                        users.setDesignationOrCourse(String.valueOf(editDesignationOrCourseName.getSelectedItem()));
//                        users.setPassword(password.getText().toString());
                        System.out.println(HashMD5.passwordHashing(editPassword.getText().toString().trim()));
                        users.setPassword(HashMD5.passwordHashing(editPassword.getText().toString().trim()));
                        users.setConfirmPassword(HashMD5.passwordHashing(editConfirmPassword.getText().toString().trim()));
//                        users.setConfirmPassword(confirmPassword.getText().toString());
                        String encrypt = EncryptAndDecrypt.encrypt(editIdentity.getText().toString().trim(), secretKey);
                        System.out.println(encrypt + " *** ENCRYPT");
                        String decrypt = EncryptAndDecrypt.decrypt(encrypt, secretKey);
                        System.out.println(decrypt + " -- DECRYPT");
                        users.setImageName(editIdentity.getText().toString().trim());
                        users.setExtension(".jpeg");
//                        users.setImage(imageToStore);
                        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                        Bitmap bitmap1 = drawable.getBitmap();
                        File filePath = Environment.getExternalStorageDirectory();
                        File dir = new File(filePath.getAbsolutePath() + "/userImages/");

//                        File f = new File(dir, users.getIdentity()+users.getExtension());
                        File f = new File(filePath.getAbsolutePath()+"/userImages/"+users.getIdentity()+users.getExtension());
                        System.out.println(f.getAbsoluteFile());
                        System.out.println(f.getName());
                        System.out.println(f.getAbsoluteFile());
                        String s = f.getAbsolutePath();
                        File file1 = new File(s);

                        if(file1.delete()){
                            Toast.makeText(EditProfileActivity.this, "File was deleted...", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(EditProfileActivity.this, "File not deleted...", Toast.LENGTH_SHORT).show();
                        }

                        dir.mkdir();
                        File file = new File(dir, users.getIdentity() + users.getExtension());
                        FileOutputStream outputStream = null;
                        try {
                            outputStream = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        Toast.makeText(EditProfileActivity.this, "Image store successfully", Toast.LENGTH_SHORT).show();
                        try {
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dbHelper.updateUserData(users);
                    //}
                }
            }
        });

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        editDesignationOrCourseName = (Spinner) findViewById(R.id.editDesignationOrCourseName);

        List<String> listCourse = new ArrayList<>();

        if (editType.getText().equals("Teacher")) {
            listCourse.add("Select Designation");
            listCourse.add("Assistant Professor");
            listCourse.add("Associate Professor");
            listCourse.add("Director");
            listCourse.add("Lecturer");
            listCourse.add("Professor");
        } else if (editType.getText().equals("Student")) {
            listCourse.add("Select Course Name");
            listCourse.add("PGDIT");
            listCourse.add("MIT");
        }

        ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(EditProfileActivity.this,
                android.R.layout.simple_spinner_item, listCourse);
        dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDesignationOrCourseName.setAdapter(dataAdapterCourse);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " - " + resultCode + " - " + data);
//        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){ // requestCode = 1
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) { // requestCode = 200
            Uri uri = CropImage.getPickImageResultUri(EditProfileActivity.this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(EditProfileActivity.this, uri)) {
                imageUri = uri;
            } else {
                startCrop(uri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    imageView.setImageBitmap(imageToStore);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(EditProfileActivity.this, "Image Upload Successfully!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCrop(Uri uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(EditProfileActivity.this);
    }

}