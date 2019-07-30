package com.example.rinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button logInButton;
    private TextView signUpButton;


    private FirebaseAuth authenticationRef;
    private String emailData, passwordData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logInButton = findViewById(R.id.logInButton);
        signUpButton = findViewById(R.id.signUp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        authenticationRef = FirebaseAuth.getInstance();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailData = email.getText().toString();
                passwordData = password.getText().toString();

                authenticationRef.signInWithEmailAndPassword(emailData, passwordData)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                    Toast.makeText(MainActivity.this, "login", Toast.LENGTH_LONG).show();
                                    startActivity(i);
                                } else {
                                    Toast.makeText(MainActivity.this, task.getException()
                                            .getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });


            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(i);
            }
        });
    }
}
