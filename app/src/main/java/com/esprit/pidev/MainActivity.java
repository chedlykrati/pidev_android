package com.esprit.pidev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    TextView createNewAccount ;
    Button btnGoogle;


    EditText username , password ;
    Button btnLogin;

    DBHelper myDB;
    //FirebaseAuth mAuth;
    //FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNewAccount = findViewById(R.id.createNewAccount);
        btnGoogle = findViewById(R.id.btnGoogle);


        username = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        myDB = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){

                    Toast.makeText(MainActivity.this, "Please enter Credentials", Toast.LENGTH_SHORT).show();

                }else {

                    Boolean result = myDB.checkUserNamePassword(user,pass);
                    if(result == true){

                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);

                    }else{

                        Toast.makeText(MainActivity.this, "Invalid Creadentials ! ", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });



        /*
        createNewAccount.setOnClickListener(view -> {

        });
        */

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
/*
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GoogleSignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FacebookAuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
*/




    }
}