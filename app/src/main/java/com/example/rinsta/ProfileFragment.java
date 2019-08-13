package com.example.rinsta;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private Spinner search;

    private TextView numFollowers, numFollowing, username, bio;
    private String usernameString;
    private ImageView profPic;

    private Button signOutButton;
    int followersCount;
    int followingCount;
    private String userIdentifier;
    private ArrayList<String> following = new ArrayList<>();
    private ArrayList<String> followers = new ArrayList<>();
    private String[] followersArray;
    private String[] followingArray;

    //database
    private FirebaseDatabase fbDatabase;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private StorageReference storageRootRef;

    private ArrayList<String> allOtherUsers = new ArrayList<>();
    ArrayAdapter<String> spinnerArrayAdapter;


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
        signOutButton = view.findViewById(R.id.signOutButton);
        followersCount = 0;
        followingCount = 0;

        storageRootRef = FirebaseStorage.getInstance().getReference();
        fbDatabase = FirebaseDatabase.getInstance();
        myRef = fbDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        userIdentifier = new StringManipulation().removeSpecialChar(user.getEmail());

        myAdapter = new CustomPostsAdapter(getActivity(), allMyPosts);
        listView.setAdapter(myAdapter);

        myAdapter.clear();
        populateList();

        followingFollowersListeners();
        showFollowers();
        showFollowing();
        updateProfile();
        signOutButtonFunctionality();
        search = view.findViewById(R.id.search);
        allOtherUsers.add("Find friends");
        addToAllOtherUsers();

        spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, allOtherUsers);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search.setAdapter(spinnerArrayAdapter);
        // set search users drop down menu
        search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    String userProfileUsername = adapterView.getItemAtPosition(i).toString();
                    Log.d("search", "2: " + userProfileUsername);
                    Intent intent = new Intent(getContext(), UserProfileActivity.class);
                    intent.putExtra("userProfileUsername", userProfileUsername);
                    getContext().startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void signOutButtonFunctionality() {
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
            }
        });
    }

    // add users profile info
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

    private void setImage(final Post post) {
        // Log.d("bitmap", imageid);
        final StorageReference storageRef = storageRootRef.child("Images").child(post.getImageid());
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.d("bitmap", "success");
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                PostsAdapterItem postsAdapterItem = new PostsAdapterItem(post, bm);
                likeImage(postsAdapterItem);
                allMyPosts.add(postsAdapterItem);
                myAdapter.notifyDataSetChanged();
                Log.d("bitmap", bm.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("bitmap", "failure");
                // Handle any errors
            }
        });
    }

    // add my posts to my profile feed
    private void populateList() {
        myRef.child("post").orderByChild("uniqueIdentifier").equalTo(new StringManipulation()
                .removeSpecialChar(user.getEmail())).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post c = dataSnapshot.getValue(Post.class);
                setImage(c);
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


    // check if I liked an image
    private void likeImage(final PostsAdapterItem p) {
        final String imageId = new StringManipulation().removeJpgFromEnd(p.getPost().getImageid());
        myRef.child("likes").child(imageId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Toast.makeText(getContext(), new StringManipulation().formatIdentifier(dataSnapshot.getKey()) + " liked this image", Toast.LENGTH_SHORT).show();
                if (dataSnapshot.getKey().equals(new StringManipulation().removeSpecialChar(user.getEmail()))) {
                    p.setLiked(true);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myAdapter.notifyDataSetChanged();
            }

            // check if I've unliked an image
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

    // add my followers and following to list on profile page
    private void followingFollowersListeners() {
        myRef.child("following").child(userIdentifier).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("profile", dataSnapshot.getKey());
                followingCount++;
                String identifier = new StringManipulation().formatIdentifier(dataSnapshot.getKey());
                following.add(identifier);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                followingCount--;
                following.remove(new StringManipulation().formatIdentifier(dataSnapshot.getKey()));
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        myRef.child("followers").child(userIdentifier).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String identifier = new StringManipulation().formatIdentifier(dataSnapshot.getKey());
                followers.add(identifier);
                followersCount++;
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                followersCount--;
                followers.remove(new StringManipulation().formatIdentifier(dataSnapshot.getKey()));
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

    // dialog box when opening followers
    private void showFollowers() {
        //  followersArray = followers.toArray(new String[followers.size()]);
        numFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Followers")
                        .setItems(followers.toArray(new String[followers.size()]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String userProfileUsername = followers.get(i);
                                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                                intent.putExtra("userProfileUsername", userProfileUsername);
                                getContext().startActivity(intent);
                            }
                        }).show();
            }
        });
    }

    // dialog box when opening following
    private void showFollowing() {
        numFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Following")
                        .setItems(following.toArray(new String[following.size()]), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String userProfileUsername = following.get(i);
                                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                                intent.putExtra("userProfileUsername", userProfileUsername);
                                getContext().startActivity(intent);
                            }
                        }).show();
            }
        });
    }

    // list of users for drop down menu
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
                spinnerArrayAdapter.notifyDataSetChanged();

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

}
