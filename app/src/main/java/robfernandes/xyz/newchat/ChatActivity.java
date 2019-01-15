package robfernandes.xyz.newchat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter mChatAdapter;
    private RecyclerView mRecyclerView;
    private User userReceiver;
    private Button sendMessageBtn;
    private EditText messageEditText;
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendMessageBtn = findViewById(R.id.activity_chat_send_message_btn);
        messageEditText = findViewById(R.id.activity_chat_enter_message_edit_text);

        getIntentExtras();
        getLoggedInUser();
       getSupportActionBar().setTitle(userReceiver.getUsername());

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = messageEditText.getText().toString();
                if (!text.isEmpty()) {
                    Message message = new Message();
                    message.setMessage(text);
                    message.setMessageReceiver(userReceiver.getUid());
                    message.setMessageSender(loggedInUser.getUid());
                    message.setTime(System.currentTimeMillis());

                    sendMessage(message);
                } else {
                    Toast.makeText(ChatActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getLoggedInUser() {
        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        loggedInUser = documentSnapshot.toObject(User.class);
                        setRecyclerView();
                        fetchMessages();
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

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatActivity.this, "Error message NOT sent", Toast.LENGTH_SHORT).show();
            }
        });
        //add to the other user
        FirebaseFirestore.getInstance().collection("conversations")
                .document(receiverUID)
                .collection(fromUID)
                .add(message)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        messageEditText.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, "Error message NOT sent", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchMessages() {
        if (loggedInUser != null) {

            String fromId = loggedInUser.getUid();
            String toId = userReceiver.getUid();

            FirebaseFirestore.getInstance().collection("/conversations")
                    .document(fromId)
                    .collection(toId)
                    .orderBy("time", Query.Direction.ASCENDING)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                            if (documentChanges != null) {
                                for (DocumentChange doc: documentChanges) {
                                    if (doc.getType() == DocumentChange.Type.ADDED) {
                                        Message message = doc.getDocument().toObject(Message.class);
                                        mChatAdapter.addMessage(message);
                                    }
                                }
                                mChatAdapter.notifyDataSetChanged();
                            }
                        }
                    });

        }
    }

    private void getIntentExtras() {
        userReceiver = getIntent().getExtras().getParcelable("user");
    }

    private void setRecyclerView() {
           mChatAdapter = new ChatAdapter(loggedInUser.getUid(), userReceiver);
            mRecyclerView = findViewById(R.id.activity_chat_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mChatAdapter);
    }
}
