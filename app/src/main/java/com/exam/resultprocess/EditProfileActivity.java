package com.exam.resultprocess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.BatchSetup;
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
    private Session session;

    CircleImageView imageViewProfile;
    ImageView homeIcon;
    TextView usernameToolbar;

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
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setSupportActionBar(findViewById(R.id.toolbarId));

        session = new Session(this);

        imageViewProfile = (CircleImageView) findViewById(R.id.profile_image);
        homeIcon = (ImageView) findViewById(R.id.homeIcon);
        usernameToolbar = (TextView) findViewById(R.id.usernameToolbar);

        editFullName = (EditText) findViewById(R.id.editFullName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editIdentity = (TextView) findViewById(R.id.editIdentity);
        editType = (TextView) findViewById(R.id.editType);
        editGender = (TextView) findViewById(R.id.editGender);
        editBatchCode = (TextView) findViewById(R.id.editBatchCode);
        editMobile = (EditText) findViewById(R.id.editMobile);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        editDesignationOrCourseName = (Spinner) findViewById(R.id.editDesignationOrCourseName);

        updateBtn = (Button) findViewById(R.id.profileUpdateBtn);

        dbHelper = new DBHelper(this);
        builder = new AlertDialog.Builder(this);

        String userid = session.get("userid");
        Users users = dbHelper.getByEmailOrID(userid);

        imageView = (CircleImageView) findViewById(R.id.edit_upload_image);

        usernameToolbar.setText(session.get("username"));
        String imagePath = session.get("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);

        setToolbarData(users);

        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                CropImage.startPickImageActivity(EditProfileActivity.this);
            }
        });

        addItemsOnSpinner(users.getType());
        setDataInColumn(users);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (editFullName.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Please Full Name is Required!!!", Toast.LENGTH_LONG).show();
                } else if (editEmail.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Please Email is Required!!!", Toast.LENGTH_LONG).show();
                } else if ((String.valueOf(editType.getText().toString()) == "Student") || (String.valueOf(editDesignationOrCourseName.getSelectedItem()) == "Select Course Name")) {
                    Toast.makeText(EditProfileActivity.this, "Course Name is Required!!!", Toast.LENGTH_LONG).show();
                } else if (editPassword.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Please Password is Required!!!", Toast.LENGTH_LONG).show();
                } else if (editConfirmPassword.getText().toString().equals("")) {
                    Toast.makeText(EditProfileActivity.this, "Please Confirm Password is Required!!!", Toast.LENGTH_LONG).show();
                } else if (!editPassword.getText().toString().equals(editConfirmPassword.getText().toString())) {
                    Toast.makeText(EditProfileActivity.this, "Password not Match", Toast.LENGTH_LONG).show();
                } else {
                    builder.setMessage("Welcome to Alert Dialog")
                            .setTitle("Alert Dialog");
                    builder.setMessage("Do you want to profile updated ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();

                                    Users users = new Users();
                                    users.setFullName(editFullName.getText().toString().trim());
                                    users.setEmail(editEmail.getText().toString().trim());
                                    users.setGender(editGender.getText().toString().trim());
                                    users.setMobile(editMobile.getText().toString().trim());
                                    users.setBatchCode(editBatchCode.getText().toString().trim());
                                    users.setIdentity(editIdentity.getText().toString().trim());
                                    users.setType(editType.getText().toString());
                                    users.setDesignationOrCourse(String.valueOf(editDesignationOrCourseName.getSelectedItem()));
                                    users.setPassword(HashMD5.passwordHashing(editPassword.getText().toString().trim()));
                                    users.setConfirmPassword(HashMD5.passwordHashing(editConfirmPassword.getText().toString().trim()));
                                    users.setImageName(editIdentity.getText().toString().trim());
                                    users.setExtension(".jpeg");
                                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                                    Bitmap bitmap1 = drawable.getBitmap();
                                    File filePath = Environment.getExternalStorageDirectory();
                                    File dir = new File(filePath.getAbsolutePath() + "/userImages/");

                                    File f = new File(filePath.getAbsolutePath()+"/userImages/"+users.getIdentity()+users.getExtension());
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
                                    session.set("username", users.getFullName());
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
                    alert.setTitle("Profile Update");
                    alert.show();
                }
            }
        });

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner(String type) {
        List<String> listCourse = new ArrayList<>();
        if (type.equals("Teacher")) {
            listCourse.add("Select Designation");
            listCourse.add("Assistant Professor");
            listCourse.add("Associate Professor");
            listCourse.add("Director");
            listCourse.add("Lecturer");
            listCourse.add("Professor");
        } else if (type.equals("Student")) {
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

    private void setDataInColumn(Users users){
        editFullName.setText(users.getFullName());
        editEmail.setText(users.getEmail());
        editGender.setText(users.getGender());
        editMobile.setText(users.getMobile());
        editType.setText(users.getType());
        editBatchCode.setText(users.getBatchCode());
        editIdentity.setText(users.getIdentity());
//        editDesignationOrCourseName.setSelection(editDesignationOrCourseName.getItemAtPosition());
        editDesignationOrCourseName.setSelection(
                (
                        (ArrayAdapter) editDesignationOrCourseName.getAdapter()
                ).getPosition(
                        users.getDesignationOrCourse()
                )
        );

        File filePath = Environment.getExternalStorageDirectory();
        File dir = new File(filePath.getAbsolutePath()+"/userImages/");
        File file = new File(dir, users.getIdentity()+users.getExtension());
        String imagePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);

    }

    public void setToolbarData(Users users){
        usernameToolbar.setText(users.getFullName());
        File filePath = Environment.getExternalStorageDirectory();
        File dir = new File(filePath.getAbsolutePath()+"/userImages/");
        File file = new File(dir, users.getIdentity()+users.getExtension());
        String imagePath = file.getPath();
        session.set("imagePath", imagePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageViewProfile.setImageBitmap(bitmap);
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
                Intent intentProfile = new Intent(EditProfileActivity.this, EditProfileActivity.class);
                startActivity(intentProfile);
                return true;
            case R.id.logoutItem:
                Intent intentLogout = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intentLogout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}