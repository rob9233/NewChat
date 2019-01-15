package robfernandes.xyz.newchat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roberto Fernandes on 12/01/2019.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Message> mMessageList = new ArrayList<>();
    private String loggedInUserUID;

    public void addMessage(Message message) {
        mMessageList.add(message);
    }

    public ChatAdapter(String loggedInUserUID) {
        this.loggedInUserUID = loggedInUserUID;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layout;

        if (i == 0) {
            layout = R.layout.message_sending_layout;
        } else {
            layout = R.layout.message_receiving_layout;
        }

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (isSending(mMessageList.get(position).getMessageSender())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        if (mMessageList!=null) {
            return mMessageList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private boolean isSending (String senderUUID) {
        return senderUUID.equals(loggedInUserUID);
    }
}
