package robfernandes.xyz.newchat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Roberto Fernandes on 08/01/2019.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<User> mUserList;
    private Context mContext;

    public ContactsAdapter(List<User> userList, Context context) {
        mUserList = userList;
        mContext = context;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_user, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder viewHolder, int i) {
        User user = mUserList.get(i);
        viewHolder.title.setText(user.getUsername());
        Picasso.get().load(user.getUrl()).into(viewHolder.UserImage);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView UserImage;
        private int position;
        private User user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.item_user_title);
            UserImage = itemView.findViewById(R.id.item_user_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    if (getAdapterPosition()>=0) {
                        position = getAdapterPosition();
                    } else {
                        position = 0;
                    }

                    user = mUserList.get(position);
                    intent.putExtra("user", user);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
