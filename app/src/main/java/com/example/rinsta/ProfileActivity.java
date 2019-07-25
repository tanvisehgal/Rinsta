package com.example.rinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import objects.Post;
import objects.User;
import objects.UserBasic;
import utils.CustomPostsAdapter;
import utils.PostsAdapterItem;
import utils.StringManipulation;

public class ProfileActivity extends FooterActivity {

    private ListView listView;
    private ArrayList<PostsAdapterItem> allMyPosts = new ArrayList<>();

    private TextView numFollowers, numFollowing, username, bio;
    private ImageView profPic;

    //database
    private FirebaseDatabase fbDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setFooter();
        initializeWidgets();
        updateProfile();


        populateList();
        CustomPostsAdapter myAdapter = new CustomPostsAdapter(this, allMyPosts);
        listView.setAdapter(myAdapter);
    }

    private void initializeWidgets() {
        listView = findViewById(R.id.imagesListViewPS);
        numFollowers = findViewById(R.id.followersText);
        numFollowing = findViewById(R.id.followingText);
        username = findViewById(R.id.usernamePS);
        bio = findViewById(R.id.userDescriptionPS);
        profPic = findViewById(R.id.profPicPS);
    }
    private void updateProfile() {
        fbDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String usernameFromUser = new StringManipulation().extractUsername(user.getEmail());
        myRef = fbDatabase.getReference().child("user_settings").child(usernameFromUser);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userInfo = dataSnapshot.getValue(User.class);
                String followersText = "Followers: " + userInfo.getNumFollowers();
                String followingText = "Following: " + userInfo.getNumFollowing();
                numFollowers.setText(followersText);
                numFollowing.setText(followingText);
                username.setText(userInfo.getUsername());
                bio.setText(userInfo.getDescription());
                profPic.setImageResource(userInfo.getProfilePic());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateList() {
        allMyPosts.add(new PostsAdapterItem(new Post(R.drawable.dogpic4, 100, 3,
                430, new UserBasic(R.drawable.profpic, "tanvisiz"), "ocean")));
        allMyPosts.add(new PostsAdapterItem(new Post(R.drawable.dogpic1, 85, 1,
                420, new UserBasic(R.drawable.profpic, "tanvisiz"), "dog")));
        allMyPosts.add(new PostsAdapterItem(new Post(R.drawable.dogpic2, 2, 3,
                430, new UserBasic(R.drawable.profpic, "tanvisiz"), "dogzzz")));
        allMyPosts.add(new PostsAdapterItem(new Post(R.drawable.dogpic3, 2, 1,
                420, new UserBasic(R.drawable.profpic, "tanvisiz"), "d")));

    }
}
