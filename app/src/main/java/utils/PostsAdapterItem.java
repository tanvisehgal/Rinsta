package utils;

import android.util.Log;

import objects.Post;

public class PostsAdapterItem {
    private Post post;
    private boolean liked;

    public PostsAdapterItem(Post post) {
        this.post = post;
        this.liked = false;
    }

    public PostsAdapterItem(Post post, boolean liked) {
        this.post = post;
        this.liked = liked;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        Log.d("liked", "setLiked called: " + liked);
        this.liked = liked;
    }
}
