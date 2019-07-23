package com.example.rinsta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import objects.Post;
import objects.UserBasic;
import utils.CustomPostsAdapter;
import utils.PostsAdapterItem;

public class ProfileActivity extends FooterActivity {

    ListView listView;
    ArrayList<PostsAdapterItem> allMyPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        listView = findViewById(R.id.imagesListViewPS);
        populateList();
        CustomPostsAdapter myAdapter = new CustomPostsAdapter(this, allMyPosts);
        listView.setAdapter(myAdapter);
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
