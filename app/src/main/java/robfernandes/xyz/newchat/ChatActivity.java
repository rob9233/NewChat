package robfernandes.xyz.newchat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter mChatAdapter;
    private RecyclerView mRecyclerView;
    private User userReceiver;
    private Button sendMessageBtn;
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendMessageBtn = findViewById(R.id.activity_chat_send_message_btn);
        messageEditText = findViewById(R.id.activity_chat_enter_message_edit_text);
       getIntentExtras();
        setRecyclerView();
       getSupportActionBar().setTitle(userReceiver.getUsername());

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = messageEditText.getText().toString();
                if (!text.isEmpty()) {
                    Message message = new Message();
                    message.setMessage(text);
                    message.setMessageReceiver(userReceiver.getUid());
                    message.setMessageSender(FirebaseAuth.getInstance().getUid());
                    message.setTime(System.currentTimeMillis());

                    sendMessage(message);
                } else {
                    Toast.makeText(ChatActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void sendMessage(Message message) {
        String receiverUID = message.getMessageReceiver();
        String fromUID = message.getMessageSender();


        FirebaseFirestore.getInstance().collection("conversations")
                .document(fromUID)
                .collection(receiverUID)
                .add(message)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(ChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatActivity.this, "Error message NOT sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIntentExtras() {
        userReceiver = getIntent().getExtras().getParcelable("user");
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
