package com.example.rinsta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class FooterActivity extends AppCompatActivity {

    ImageButton homeButton;
    ImageButton searchButton;
    ImageButton cameraButton;
    ImageButton notificationButton;
    ImageButton profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footer);

    }

    public void setFooter() {
        homeButton = findViewById(R.id.homeButton);
        searchButton = findViewById(R.id.searchButton);
        cameraButton = findViewById(R.id.cameraButton);
        notificationButton = findViewById(R.id.notificationButton);
        profileButton = findViewById(R.id.profileButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FooterActivity.this, HomeScreenActivity.class);
                startActivity(i);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FooterActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FooterActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("profilebutton", "prof button clicked");
                Intent i = new Intent(FooterActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

}
