package com.exam.resultprocess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.resultprocess.model.Users;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button login;
    TextView register;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        username = (EditText) findViewById(R.id.username);
//        username.requestFocus();
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.buttonLogin);
        register = (TextView) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String pass = password.getText().toString();
                if(name.equals("")){
                    Toast.makeText(MainActivity.this, "Please Username is Required!!!", Toast.LENGTH_SHORT).show();
                }else if(pass.equals("")){
                    Toast.makeText(MainActivity.this, "Please Password is Required!!!", Toast.LENGTH_SHORT).show();
                }else {
                    Users checkUser = dbHelper.getByEmailOrID(name);
                    if(checkUser.getIdentity().equals("")){
                        Toast.makeText(MainActivity.this, "Email Or ID not registration, please first registration!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        if(checkUser.getEmail().equals(name) || checkUser.getIdentity().equals(name)){
                            String md5Hex = HashMD5.passwordHashing(pass);
                            if(checkUser.getPassword().toUpperCase().equals(md5Hex.toUpperCase())){
                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                intent.putExtra("user", checkUser);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Password not match!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }
    
}