package utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rinsta.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomUsersAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UsersAdapterItem> allPosts;
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    public CustomUsersAdapter(Context context, ArrayList<UsersAdapterItem> allPosts) {
        this.context = context;
        this.allPosts = allPosts;
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
        ViewHolder viewHolder;

        if (view == null) {
            view = View.inflate(context, R.layout.users_adapter_item, null);
            viewHolder = new ViewHolder();

            viewHolder.profpic = view.findViewById(R.id.userAdapterProfPic);
            viewHolder.username = view.findViewById(R.id.userAdapterUsername);

            view.setTag(viewHolder);


        }


        return view;
    }

    private static class ViewHolder {
        ImageView profpic;
        TextView username;
    }
}
