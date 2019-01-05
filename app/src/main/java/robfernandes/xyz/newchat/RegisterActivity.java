package robfernandes.xyz.newchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private TextView logInTextView;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
        logInTextView = findViewById(R.id.activity_register_log_in_text);
        usernameEditText = findViewById(R.id.activity_register_username);
        emailEditText = findViewById(R.id.activity_register_email);
        passwordEditText = findViewById(R.id.activity_register_password);
        registerBtn = findViewById(R.id.activity_register_register_btn);
    }

    private void setClickListeners() {
        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish activity and go back to log in
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameString = usernameEditText.getText().toString();
                String emailString = emailEditText.getText().toString();
                String passwordString = passwordEditText.getText().toString();

                if (usernameString == null
                        || usernameString.isEmpty()
                        || emailString == null
                        || emailString.isEmpty()
                        || passwordString == null
                        || passwordString.isEmpty()) {
                    displayToast("All fields must be answer, please try again");
                } else {
                    registerUser(usernameString, emailString, passwordString);
                }
            }
        });
    }

    private void registerUser(String username, String email, String password) {

    }

    private void displayToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
