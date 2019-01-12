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
    private List<Boolean> isSendingList = new ArrayList<>();
    private List<String> messageList = new ArrayList<>();

    public void addMessage(boolean isSending, String message) {
        isSendingList.add(isSending);
        messageList.add(message);
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
        if (isSendingList.get(position)) {
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
        if (isSendingList!=null) {
            return isSendingList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
