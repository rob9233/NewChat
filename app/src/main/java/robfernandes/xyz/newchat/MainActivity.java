package robfernandes.xyz.newchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView signUpText;
    private Button logInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    private void initializeViews() {
        email = findViewById(R.id.activity_main_email);
        password = findViewById(R.id.activity_main_password);
        signUpText = findViewById(R.id.activity_main_sign_up_text);
        logInBtn = findViewById(R.id.activity_main_log_in_btn);
    }
}
