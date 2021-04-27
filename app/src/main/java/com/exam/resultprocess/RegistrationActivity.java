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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistrationActivity extends AppCompatActivity {

    final String secretKey = "ssshhhhhhhhhhh!!!!";

    EditText fullName, email, identity, mobile, password, confirmPassword;
    TextView login;
    RadioGroup genderRadioGroup;
    RadioButton genderRadioButton;
    Spinner type, designationOrCourseName;
    CircleImageView imageView;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    private Bitmap imageToStore;

    Button submit;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullName = (EditText) findViewById(R.id.fullName);
        email = (EditText) findViewById(R.id.email);
        identity = (EditText) findViewById(R.id.identity);
        mobile = (EditText) findViewById(R.id.mobile);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        login = (TextView) findViewById(R.id.goLogin);

        genderRadioGroup = (RadioGroup) findViewById(R.id.genderGroup);

        submit = (Button) findViewById(R.id.registrationSubmit);

        dbHelper = new DBHelper(this);

        addItemsOnSpinner();
        addListenerOnButton();

        imageView = (CircleImageView) findViewById(R.id.upload_image);

        imageView = (CircleImageView) findViewById(R.id.upload_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                CropImage.startPickImageActivity(RegistrationActivity.this);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                int radioId = genderRadioGroup.getCheckedRadioButtonId();
                genderRadioButton = findViewById(radioId);

                if (fullName.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please Full Name is Required!!!", Toast.LENGTH_LONG).show();
                } else if (email.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please Email is Required!!!", Toast.LENGTH_LONG).show();
                } else if (identity.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please ID is Required!!!", Toast.LENGTH_LONG).show();
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please Password is Required!!!", Toast.LENGTH_LONG).show();
                } else if (confirmPassword.getText().toString().equals("")) {
                    Toast.makeText(RegistrationActivity.this, "Please Confirm Password is Required!!!", Toast.LENGTH_LONG).show();
                } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this, "Password not Match", Toast.LENGTH_LONG).show();
                } else {
                    boolean checkUser = dbHelper.getByEmailOrIdentity(email.getText().toString(), identity.getText().toString());
                    if (checkUser) {
                        Toast.makeText(RegistrationActivity.this, "Email Or ID Already Exist!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        Users users = new Users();
                        users.setFullName(fullName.getText().toString().trim());
                        users.setEmail(email.getText().toString().trim());
                        users.setGender(genderRadioButton.getText().toString());
                        users.setIdentity(identity.getText().toString().trim());
                        users.setType(String.valueOf(type.getSelectedItem()));
                        users.setDesignationOrCourse(String.valueOf(designationOrCourseName.getSelectedItem()));
//                        users.setPassword(password.getText().toString());
                        System.out.println(HashMD5.passwordHashing(password.getText().toString().trim()));
                        users.setPassword(HashMD5.passwordHashing(password.getText().toString().trim()));
                        users.setConfirmPassword(HashMD5.passwordHashing(confirmPassword.getText().toString().trim()));
//                        users.setConfirmPassword(confirmPassword.getText().toString());
                        String encrypt = EncryptAndDecrypt.encrypt(identity.getText().toString().trim(), secretKey);
                        System.out.println(encrypt + " *** ENCRYPT");
                        String decrypt = EncryptAndDecrypt.decrypt(encrypt, secretKey);
                        System.out.println(decrypt + " -- DECRYPT");
                        users.setImageName(identity.getText().toString().trim());
                        users.setExtension(".jpeg");
//                        users.setImage(imageToStore);
                        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                        Bitmap bitmap1 = drawable.getBitmap();
                        File filePath = Environment.getExternalStorageDirectory();
                        File dir = new File(filePath.getAbsolutePath() + "/userImages/");
                        dir.mkdir();
                        File file = new File(dir, users.getIdentity() + users.getExtension());
                        FileOutputStream outputStream = null;
                        try {
                            outputStream = new FileOutputStream(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        Toast.makeText(RegistrationActivity.this, "Image store successfully", Toast.LENGTH_SHORT).show();
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

                        LocalDateTime dateObj = LocalDateTime.now();
                        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                        String formattedDate = dateObj.format(formatObj);
                        users.setCreatedOrUpdatedTime(formattedDate);

                        dbHelper.insertUserData(users);
                        clearUser();
//                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                        startActivity(intent);
                    }
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        type = (Spinner) findViewById(R.id.type);
        List<String> listType = new ArrayList<>();
        listType.add("Select Type");
        listType.add("Teacher");
        listType.add("Student");

        ArrayAdapter<String> dataAdapterType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listType);
        dataAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(dataAdapterType);

    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                designationOrCourseName = (Spinner) findViewById(R.id.designationOrCourseName);

                List<String> listCourse = new ArrayList<>();

                if (String.valueOf(type.getSelectedItem()) == "Teacher") {
                    listCourse.add("Select Designation");
                    listCourse.add("Assistant Professor");
                    listCourse.add("Associate Professor");
                    listCourse.add("Director");
                    listCourse.add("Lecturer");
                    listCourse.add("Professor");
                } else if (String.valueOf(type.getSelectedItem()) == "Student") {
                    listCourse.add("Select Course Name");
                    listCourse.add("PGDIT");
                    listCourse.add("MIT");
                }

                ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(RegistrationActivity.this,
                        android.R.layout.simple_spinner_item, listCourse);
                dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                designationOrCourseName.setAdapter(dataAdapterCourse);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode + " - " + resultCode + " - " + data);
//        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){ // requestCode = 1
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) { // requestCode = 200
            Uri uri = CropImage.getPickImageResultUri(RegistrationActivity.this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(RegistrationActivity.this, uri)) {
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
                Toast.makeText(RegistrationActivity.this, "Image Upload Successfully!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCrop(Uri uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(RegistrationActivity.this);
    }

    private void clearUser() {
        fullName.setText("");
        email.setText("");
        genderRadioGroup.clearCheck();
        mobile.setText("");
        type.setAdapter(null);
        designationOrCourseName.setAdapter(null);
        password.setText("");
        confirmPassword.setText("");
        imageView.setImageBitmap(null);
    }
}