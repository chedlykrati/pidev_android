package com.esprit.pidev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyHaveAccount;
    EditText username , password , repassword ;
    Button btnRegister;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);

        username = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        repassword = findViewById(R.id.inputConformPassword);
        btnRegister = findViewById(R.id.btnRegister);

        myDB = new DBHelper(this);


        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("") || pass.equals("") || repass.equals("")){

                    Toast.makeText(RegisterActivity.this, "Fill All The Fields", Toast.LENGTH_SHORT).show();

                }  else{

                    if(pass.equals(repass)){

                        Boolean userCheckResult = myDB.checkUserName(user);
                        if(userCheckResult == false){

                            Boolean regResult = myDB.insertData(user,pass);
                            if (regResult == true){

                                Toast.makeText(RegisterActivity.this, "Registration Successful .", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);

                            }else {

                                Toast.makeText(RegisterActivity.this, "Registration Failed .", Toast.LENGTH_SHORT).show();

                            }

                        }else{

                            Toast.makeText(RegisterActivity.this, "User Already Exists. \n Please Sign In ", Toast.LENGTH_SHORT).show();
                        }

                    }else{

                        Toast.makeText(RegisterActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                    }

                }




                }
        });


    }
}