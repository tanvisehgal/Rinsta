package com.example.rinsta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import objects.Post;
import objects.UserBasic;
import utils.CustomPostsAdapter;
import utils.PostsAdapterItem;

public class HomeScreenActivity extends FooterActivity {

    ListView listView;
    ArrayList<PostsAdapterItem> allPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setFooter();

        listView = findViewById(R.id.imagesListView);
        populateList();
        CustomPostsAdapter myAdapter = new CustomPostsAdapter(this, allPosts);
        listView.setAdapter(myAdapter);
    }

    private void populateList() {
        allPosts.add(new PostsAdapterItem(new Post(R.drawable.dogpic4, 100, 3,
                430, new UserBasic(R.drawable.profpic, "tanvi"), "ocean")));
        allPosts.add(new PostsAdapterItem(new Post(R.drawable.dogpic1, 85, 1,
                420, new UserBasic(R.drawable.profpic, "sehgal"), "dog")));
        allPosts.add(new PostsAdapterItem(new Post(R.drawable.dogpic2, 2, 3,
                430, new UserBasic(R.drawable.profpic, "ts"), "dogzzz")));
        allPosts.add(new PostsAdapterItem(new Post(R.drawable.dogpic3, 2, 1,
                420, new UserBasic(R.drawable.profpic, "tsehgal"), "d")));
    }
}
