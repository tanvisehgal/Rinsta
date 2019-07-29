package utils;

import objects.Post;

public class PostsAdapterItem implements Comparable<PostsAdapterItem> {
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

    @Override
    public int compareTo(PostsAdapterItem postsAdapterItem) {
        return this.post.getTimeStamp() - postsAdapterItem.getPost().getTimeStamp();
    }
}
