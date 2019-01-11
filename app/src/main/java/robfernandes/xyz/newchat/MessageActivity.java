package robfernandes.xyz.newchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MessageActivity extends AppCompatActivity {

    private TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        testTextView = findViewById(R.id.activity_message_test);
        testTextView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_menu_contacts_item:
            goToContacts();
            break;
            case R.id.user_menu_log_out_item:
            logOut();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToContacts() {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);

    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(MessageActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
