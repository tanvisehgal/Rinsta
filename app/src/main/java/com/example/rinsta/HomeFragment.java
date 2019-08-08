package com.example.rinsta;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import objects.Post;
import objects.User;
import utils.CustomPostsAdapter;
import utils.PostsAdapterItem;
import utils.StringManipulation;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    public HomeFragment() {
        // Required empty public constructor
    }

    private ArrayList<PostsAdapterItem> allPosts = new ArrayList<>();
    private CustomPostsAdapter myAdapter;
    private ListView myListView;
    private String userIdentifier;
    private Spinner search;
    private ArrayList<String> allOtherUsers = new ArrayList<>();
    private ImageView likebutton;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button postButton;

    private ArrayList<String> following = new ArrayList<>();

    //database
    private FirebaseDatabase fbDatabase;
    private DatabaseReference myRef;
    private FirebaseUser user;

    //image database
    private StorageReference storageRootRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        myListView = view.findViewById(R.id.homeFragmentImagesListView);
        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userIdentifier = new StringManipulation().removeSpecialChar(user.getEmail());
        postButton = view.findViewById(R.id.postButton);
        storageRootRef = FirebaseStorage.getInstance().getReference();
        likebutton = view.findViewById(R.id.heartImageOff);
        search = view.findViewById(R.id.search);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, allOtherUsers);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search.setAdapter(spinnerArrayAdapter);
        search.setOnItemSelectedListener(this);

        myAdapter = new CustomPostsAdapter(getActivity(), allPosts);
        myListView.setAdapter(myAdapter);
        addToFollowingList();
        addMyPosts();
        addToAllOtherUsers();
        setSearch();

        Log.d("following", "size: " + following.size());
        postImage();

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void likeImage(final PostsAdapterItem p) {
        final String imageId = new StringManipulation().removeJpgFromEnd(p.getPost().getImageid());
        myRef.child("likes").child(imageId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Toast.makeText(getContext(), dataSnapshot.getKey() + " liked this image", Toast.LENGTH_SHORT).show();
                Log.d("likes", "key: " + dataSnapshot.getKey());
                Log.d("likes", "username: " + new StringManipulation().removeSpecialChar(user.getEmail()));
                if (dataSnapshot.getKey().equals(new StringManipulation().removeSpecialChar(user.getEmail()))) {
                    p.setLiked(true);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getKey().equals(new StringManipulation().removeSpecialChar(user.getEmail()))) {
                    p.setLiked(false);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void addToFollowingList() {
        Query myFollowing = myRef.child("following").child(userIdentifier);
        myFollowing.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String followingUser = dataSnapshot.getKey();
                Log.d("following", followingUser);
                following.add(followingUser);
                Log.d("following", "size: " + following.size());
                addPosts(followingUser);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    private void addMyPosts() {
        myRef.child("post").orderByChild("uniqueIdentifier").equalTo(userIdentifier).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);
                Log.d("following", "my post caption: " + post.getCaption());
                PostsAdapterItem postsAdapterItem = new PostsAdapterItem(post);
                allPosts.add(postsAdapterItem);
                likeImage(postsAdapterItem);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("followers", "on added called " + dataSnapshot.getValue());
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

    private void addPosts(String userid) {
        Query postsByUser = myRef.child("post").orderByChild("uniqueIdentifier").equalTo(userid);
        postsByUser.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);
                PostsAdapterItem postsAdapterItem = new PostsAdapterItem(post);
                allPosts.add(postsAdapterItem);
                likeImage(postsAdapterItem);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);

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

    private void addToAllOtherUsers() {
        myRef.child("user_settings").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User userInfo = dataSnapshot.getValue(User.class);
                Log.d("profile", userInfo.toString());
                String username = new StringManipulation().extractUsername(userInfo.getEmail());
                if (!userInfo.getEmail().equals(user.getEmail())) {
                    allOtherUsers.add(username);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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

    // temporary - post images
    private void postImage() {
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ImageActivity.class);
                startActivity(i);
            }
        });
    }

    private void setSearch() {
        search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String userProfileUsername = adapterView.getItemAtPosition(i).toString();
                Log.d("search", userProfileUsername);
                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                intent.putExtra("userProfileUsername", userProfileUsername);
                getContext().startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String userProfileUsername = adapterView.getItemAtPosition(i).toString();
        Log.d("search", "2: " + userProfileUsername);
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("userProfileUsername", userProfileUsername);
        getContext().startActivity(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
