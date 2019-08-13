package utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;


//import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rinsta.R;

import com.example.rinsta.UserProfileActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import objects.Post;

public class CustomPostsAdapter extends BaseAdapter {
    Context context;
    ArrayList<PostsAdapterItem> allPosts;
    StorageReference storageReference;
    FirebaseUser currUser;

    private ArrayList<String> likesList = new ArrayList<>();
    private DatabaseReference dbRef;

    public CustomPostsAdapter(Context context, ArrayList<PostsAdapterItem> allPosts) {
        this.context = context;
        this.allPosts = allPosts;
    }

    public void clear() {
        allPosts.clear();
    }

    @Override
    public int getCount() {
        return allPosts.size();
    }

    @Override
    public Object getItem(int i) {
        return allPosts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        dbRef = FirebaseDatabase.getInstance().getReference();

        if (view == null) {
            view = View.inflate(context, R.layout.posts_adapter_item, null);

            storageReference = FirebaseStorage.getInstance().getReference();
            currUser = FirebaseAuth.getInstance().getCurrentUser();
            viewHolder = new ViewHolder();

            viewHolder.post = view.findViewById(R.id.post);
            viewHolder.username = view.findViewById(R.id.usernameAbovePost);
            viewHolder.likes = view.findViewById(R.id.numLikes);
            viewHolder.comments = view.findViewById(R.id.numComments);
            viewHolder.time = view.findViewById(R.id.timeStamp);
            viewHolder.caption = view.findViewById(R.id.caption);
            viewHolder.likeButton = view.findViewById(R.id.heartImageOff);
            viewHolder.username2 = view.findViewById(R.id.usernameBelowPost);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
   //     final int index = allPosts.size() - 1 - i;
        final Post currPost = allPosts.get(i).getPost();
        Log.d("email", currPost.getEmail() + " " + new StringManipulation()
                .extractUsername(currPost.getEmail()));
        viewHolder.username.setText(new StringManipulation().extractUsername(currPost.getEmail()));
        viewHolder.username2.setText(new StringManipulation().extractUsername(currPost.getEmail()));
        viewHolder.likes.setText(currPost.getNumLikes() + " likes");
        // viewHolder.comments.setText(Integer.toString(currPost.getNumComments()));
        viewHolder.time.setText(new StringManipulation().formatTime(currPost.getTimeStamp()));
        viewHolder.caption.setText(currPost.getCaption());
        viewHolder.likes.setContentDescription(viewHolder.likes.getText().toString());
        if (allPosts.get(i).isLiked()) {
            viewHolder.likeButton.setImageResource(R.drawable.heart_on);
        } else {
            viewHolder.likeButton.setImageResource(R.drawable.heart_off);
        }

//        final StorageReference myRef = storageReference.child("Images").child(currPost.getImageid());
//
//        final long ONE_MEGABYTE = 1024 * 1024;
//        myRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
//            @Override
//            public void onSuccess(byte[] bytes) {
//                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                viewHolder.post.setImageBitmap(bm);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });

       viewHolder.post.setImageBitmap(allPosts.get(i).getBitmap());


        viewHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imageId = new StringManipulation().removeJpgFromEnd(currPost.getImageid());
                String username = new StringManipulation().removeSpecialChar(currUser.getEmail());
                Log.d("posts", i + " i value " + imageId);
                if (allPosts.get(i).isLiked()) {
                    dbRef.child("likes").child(imageId).removeValue();
                    int newLikes = currPost.getNumLikes() - 1;
                    currPost.setNumLikes(newLikes);
                    dbRef.child("post").child(new StringManipulation().removeJpgFromEnd(currPost
                            .getImageid())).child("numLikes").setValue(newLikes);
                } else {
                    dbRef.child("likes").child(imageId).child(username).setValue("true");
                    int newLikes = currPost.getNumLikes() + 1;
                    currPost.setNumLikes(newLikes);
                    dbRef.child("post").child(new StringManipulation().removeJpgFromEnd(currPost
                            .getImageid())).child("numLikes").setValue(newLikes);
                }


            }
        });



        dbRef.child("likes").child(new StringManipulation().removeJpgFromEnd(currPost.getImageid()))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        String nameliked = new StringManipulation().formatIdentifier(dataSnapshot.getKey());
                        likesList.add(nameliked);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        String nameUnliked = new StringManipulation().formatIdentifier(dataSnapshot.getKey());
                        likesList.remove(nameUnliked);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        viewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] likesArray = likesList.toArray(new String[likesList.size()]);
                new AlertDialog.Builder(context).setTitle("Likes").setItems(likesArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        String userProfileUsername = likesArray[index];
                        //UserProfileActivity userProfileActivity = new UserProfileActivity(userProfileUsername);
                        Intent intent = new Intent(context, UserProfileActivity.class);
                        intent.putExtra("userProfileUsername", userProfileUsername);
                        context.startActivity(intent);
                    }
                }).show();
            }
        });

        return view;
    }


    private static class ViewHolder {
        ImageView post;
        TextView username;
        TextView time;
        TextView likes;
        TextView comments;
        TextView caption;
        ImageView likeButton;
        ImageView likeButtonOn;
        TextView username2;
    }
}
