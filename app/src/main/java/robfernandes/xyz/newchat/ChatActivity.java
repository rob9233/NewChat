package robfernandes.xyz.newchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter mChatAdapter;
    private RecyclerView mRecyclerView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getIntentExtras();
        setRecyclerView();
        getSupportActionBar().setTitle(user.getUsername());
    }

    private void getIntentExtras() {
        user = getIntent().getExtras().getParcelable("user");
    }

    private void setRecyclerView() {
           mChatAdapter = new ChatAdapter();
        mChatAdapter.addMessage(true, "estou a enviar");
        mChatAdapter.addMessage(true, "estou a enviar outra");
        mChatAdapter.addMessage(false, "estou a enviar receber");
            mRecyclerView = findViewById(R.id.activity_chat_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mChatAdapter);
    }
}
