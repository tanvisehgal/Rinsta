package com.example.rinsta;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import objects.Post;
import objects.User;
import utils.CustomPostsAdapter;
import utils.PostsAdapterItem;
import utils.StringManipulation;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    private ListView listView;
    private ArrayList<PostsAdapterItem> allMyPosts = new ArrayList<>();
    CustomPostsAdapter myAdapter;

    private TextView numFollowers, numFollowing, username, bio;
    private String usernameString;
    private ImageView profPic;

    //database
    private FirebaseDatabase fbDatabase;
    private DatabaseReference myRef;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        listView = view.findViewById(R.id.profileFragmentListView);
        numFollowers = view.findViewById(R.id.profileFragmentFollowersCount);
        numFollowing = view.findViewById(R.id.profileFragmentFollowingCount);
        username = view.findViewById(R.id.profileFragmentUsername);
        bio = view.findViewById(R.id.profileFragmentBio);
        profPic = view.findViewById(R.id.profileFragmentProfPic);

        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        myAdapter = new CustomPostsAdapter(getActivity(), allMyPosts);
        listView.setAdapter(myAdapter);

        myAdapter.clear();
        populateList();

        updateProfile();


        return view;
    }

    private void updateProfile() {
        myRef.child("user_settings").child(new StringManipulation().removeSpecialChar(user.getEmail())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userInfo = dataSnapshot.getValue(User.class);
                numFollowing.setText(Integer.toString(userInfo.getNumFollowing()));
                numFollowers.setText(Integer.toString(userInfo.getNumFollowers()));
                username.setText(new StringManipulation().extractUsername(userInfo.getEmail()));
                bio.setText(userInfo.getDescription());
            //    profPic.setImageResource(userInfo.getProfilepic());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void populateList() {
        myRef.child("post").orderByChild("uniqueIdentifier").equalTo(new StringManipulation()
                .removeSpecialChar(user.getEmail())).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post c = dataSnapshot.getValue(Post.class);
                allMyPosts.add(new PostsAdapterItem(c));
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


}
