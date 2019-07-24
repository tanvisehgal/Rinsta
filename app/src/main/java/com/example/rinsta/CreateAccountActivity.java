package com.example.rinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import objects.User;

public class CreateAccountActivity extends AppCompatActivity {

    private Button signUp;
    private TextView logIn;

    private EditText email, password;
    private String emailData, passwordData;

    private FirebaseAuth authenticationRef;
    private FirebaseDatabase fbDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        signUp = findViewById(R.id.signUpButton);
        logIn = findViewById(R.id.logIn);
        email = findViewById(R.id.emailCA);
        password = findViewById(R.id.passwordCA);

        authenticationRef = FirebaseAuth.getInstance();

        buttonClickInit();


    }

    private void buttonClickInit() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailData = email.getText().toString();
                passwordData = password.getText().toString();

                authenticationRef.createUserWithEmailAndPassword(emailData, passwordData)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                    setNewUserSettings();
                                    Intent i = new Intent(CreateAccountActivity.this,
                                            HomeScreenActivity.class);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
            }
        });
    }

    private void setNewUserSettings() {
        fbDatabase = FirebaseDatabase.getInstance();
        myRef =  fbDatabase.getReference();
        String username = extractUsername(emailData);
        User newUser = new User(R.drawable.blankprofile, username, 0, 0, "");
        Log.d("add", "new user added to db");
        myRef.child("user_settings").child(username).push().setValue(newUser);
    }


    private String extractUsername(String email) {
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                break;
            }
            username.append(email.charAt(i));
        }
        return username.toString();
    }

    private void updateUI(FirebaseUser user) {

    }
}
