package utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.rinsta.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import objects.User;
import objects.UserBasic;

public class CustomUsersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UsersAdapterItem> allUsers;
    StorageReference storageReference;
    private DatabaseReference myRef;

    public CustomUsersAdapter(Context context, ArrayList<UsersAdapterItem> allUsers) {
        this.context = context;
        this.allUsers = allUsers;
    }

    @Override
    public int getCount() {
        return 0;
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
        final ViewHolder viewHolder;

        if (view == null) {
            view = View.inflate(context, R.layout.users_adapter_item, null);
            viewHolder = new ViewHolder();

            myRef = FirebaseDatabase.getInstance().getReference();
            storageReference = FirebaseStorage.getInstance().getReference();
            viewHolder.profpic = view.findViewById(R.id.userAdapterProfPic);
            viewHolder.username = view.findViewById(R.id.userAdapterUsername);

            view.setTag(viewHolder);

        }

        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        UserBasic currUser = allUsers.get(i).getUser();
        viewHolder.username.setText(new StringManipulation().extractUsername(currUser.getEmail()));

        StorageReference stRef = storageReference.child("Images").child(currUser.getProfilepic());
        final long ONE_MEGABYTE = 1024 * 1024;
        stRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                viewHolder.profpic.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return view;
    }

    private static class ViewHolder {
        ImageView profpic;
        TextView username;
    }
}
