package robfernandes.xyz.newchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

public class ContactsActivity extends AppCompatActivity {
    private static final String TAG = "ContactsActivity";

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
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot document:
                                 documents) {
                                User user = document.toObject(User.class);
                                Log.d(TAG, "onEvent: " + user.getUsername());
                            }
                        } else  {
                            Log.e(TAG, "onEvent: " + e.getMessage() );
                        }
                    }
                });
    }
}
