package robfernandes.xyz.newchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ContactsActivity extends AppCompatActivity {
    private static final String TAG = "ContactsActivity";
    private ContactsAdapter mContactsAdapter;
    private  List<User> mUserList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        getUsers();
    }

    private void getUsers() {
        FirebaseFirestore.getInstance().collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e == null) {
                            mUserList = new ArrayList<>();
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot document:
                                 documents) {
                                User user = document.toObject(User.class);
                                String logedInUserUID =FirebaseAuth.getInstance().getCurrentUser().getUid();
                                if (!user.getUid().equals(logedInUserUID)) {
                                    mUserList.add(user);
                                }
                            }
                            setRecyclerView();
                        } else  {
                            Log.e(TAG, "onEvent: " + e.getMessage() );
                        }
                    }
                });
    }

    private void setRecyclerView() {
        if (mUserList != null) {
            mContactsAdapter = new ContactsAdapter(mUserList, this);
            mRecyclerView = findViewById(R.id.activity_contacts_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));
            mRecyclerView.setAdapter(mContactsAdapter);
        }
    }
}
