package utils;

import android.graphics.Bitmap;
import android.util.Log;

import objects.Post;

public class PostsAdapterItem {
    private Post post;
    private boolean liked;
    private Bitmap bitmap;

    public PostsAdapterItem(Post post) {
        this.post = post;
        this.liked = false;
    }

    public PostsAdapterItem(Post post, boolean liked) {
        this.post = post;
        this.liked = liked;
    }

    public PostsAdapterItem(Post post, Bitmap bitmap) {
        this.post = post;
        this.bitmap = bitmap;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
