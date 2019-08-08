package com.example.rinsta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import objects.Post;
import objects.User;
import utils.CustomPostsAdapter;
import utils.PostsAdapterItem;
import utils.StringManipulation;

public class UserProfileActivity extends AppCompatActivity {

    private FirebaseUser currUser;
    private DatabaseReference dbRef;

    private ListView listView;
    private ArrayList<PostsAdapterItem> allUsersPosts = new ArrayList<>();
    CustomPostsAdapter myAdapter;
    private TextView numFollowers, numFollowing, username, bio;
    private ImageView profPic;
    private Button followButton;

    private String userProfileName;

    public UserProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        currUser = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference();


        listView = findViewById(R.id.userProfileListView);
        numFollowers = findViewById(R.id.userProfileFollowersCount);
        numFollowing = findViewById(R.id.userProfileFollowingCount);
        username = findViewById(R.id.userProfileUsername);
        bio = findViewById(R.id.userProfileBio);
        profPic = findViewById(R.id.userProfileProfPic);
        followButton = findViewById(R.id.userProfileFollowButton);
        userProfileName = getIntent().getStringExtra("userProfileUsername");

        myAdapter = new CustomPostsAdapter(this, allUsersPosts);
        listView.setAdapter(myAdapter);

        populateList();
        setProfileInfo();
        setFollowButton();


    }

    private void setProfileInfo() {
        dbRef.child("user_settings").child(userProfileName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("profile", dataSnapshot.getValue().toString());
                User userInfo = dataSnapshot.getValue(User.class);
                numFollowing.setText(Integer.toString(userInfo.getNumFollowing()));
                numFollowers.setText(Integer.toString(userInfo.getNumFollowers()));
                username.setText(new StringManipulation().extractUsername(userInfo.getEmail()));
                bio.setText(userInfo.getDescription());
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populateList() {
        dbRef.child("post").orderByChild("uniqueIdentifier").equalTo(userProfileName)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Post c = dataSnapshot.getValue(Post.class);
                        PostsAdapterItem pai = new PostsAdapterItem(c);
                        allUsersPosts.add(pai);
                        // likeImage(pai);
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setFollowButton() {
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followButton.setText("Following");
                String currUserName = new StringManipulation().removeSpecialChar(currUser.getEmail());
                dbRef.child("following").child(currUserName).child(userProfileName).setValue("true");
                dbRef.child("followers").child(userProfileName).child(currUserName).setValue("true");
            }
        });
    }

}
