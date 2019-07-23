package com.example.rinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {

    private Button signUp;
    private TextView logIn;

    private EditText username, email, password;
    private String usernameData, emailData, passwordData;

    private FirebaseAuth authenticationRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        signUp = findViewById(R.id.signUpButton);
        logIn = findViewById(R.id.logIn);
        username = findViewById(R.id.usernameCA);
        email = findViewById(R.id.emailCA);
        password = findViewById(R.id.passwordCA);

        authenticationRef = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailData = email.getText().toString();
                passwordData = email.getText().toString();

                authenticationRef.createUserWithEmailAndPassword(emailData, passwordData)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Intent i = new Intent(CreateAccountActivity.this,
                                        HomeScreenActivity.class);
                                startActivity(i);
                            }
                        });
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
