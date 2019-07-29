package utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rinsta.R;

import java.util.ArrayList;

import objects.Post;

public class CustomPostsAdapter extends BaseAdapter {
    Context context;
    ArrayList<PostsAdapterItem> allPosts;

    public CustomPostsAdapter(Context context, ArrayList<PostsAdapterItem> allPosts) {
        this.context = context;
        this.allPosts = allPosts;
    }

    @Override
    public int getCount() {
        return allPosts.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null) {
            view = View.inflate(context, R.layout.posts_adapter_item, null);

            viewHolder = new ViewHolder();

            viewHolder.post = view.findViewById(R.id.post);
            viewHolder.username = view.findViewById(R.id.usernameAbovePost);
            viewHolder.likes = view.findViewById(R.id.numLikes);
            viewHolder.comments = view.findViewById(R.id.numComments);
            viewHolder.time = view.findViewById(R.id.timeStamp);
            viewHolder.caption = view.findViewById(R.id.caption);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }
        Post currPost = allPosts.get(i).getPost();
        viewHolder.post.setImageResource(currPost.getImageid());
        viewHolder.username.setText(new StringManipulation().extractUsername(currPost.getEmail()));
        viewHolder.likes.setText(Integer.toString(currPost.getNumLikes()));
        viewHolder.comments.setText(Integer.toString(currPost.getNumComments()));
        viewHolder.time.setText(Integer.toString(currPost.getTimeStamp()));
        viewHolder.caption.setText(currPost.getCaption());

        return view;
    }

    private static class ViewHolder {
        ImageView post;
        TextView username;
        TextView time;
        TextView likes;
        TextView comments;
        TextView caption;
    }
}
