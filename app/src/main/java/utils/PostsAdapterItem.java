package utils;

import objects.Post;

public class PostsAdapterItem {
    private Post post;

    public PostsAdapterItem(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
