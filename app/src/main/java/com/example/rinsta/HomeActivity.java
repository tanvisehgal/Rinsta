package com.example.rinsta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import utils.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    static final String CHANNEL_ID = "one";

    Long timeStamp;
    int count;
    private Button postButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);



        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        Intent intent = new Intent(this, ImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        postButton = findViewById(R.id.postButton);
        postButton.bringToFront();

        postImage();

       timeStamp =  getIntent().getLongExtra("pictureTimestamp", 0);
       if (timeStamp != 0) {
           postButton.setText("POST(5)");
           count = 5;
           Toast.makeText(this, "Time to take a picture!", Toast.LENGTH_SHORT).show();
           Timer timer = new Timer();


           timer.schedule(new TimerTask() {
               @Override
               public void run() {
                   if (count <= 0) {
                       postButton.setText("POST");
                       cancel();
                   }
                   else {
                       count--;
                       postButton.setText("POST(" + count + "s)");
                   }
               }
           }, 0, 1000);
       }
    }

    // temporary - post images
    private void postImage() {
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long currTime = (new Date()).getTime();
                if (currTime - timeStamp <= 5000) {
                    Intent i = new Intent(getApplicationContext(), ImageActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Can't take a picture!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
