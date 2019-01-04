package robfernandes.xyz.newchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private TextView logInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
        logInText = findViewById(R.id.activity_register_log_in_text);
    }

    private void setClickListeners() {
        logInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish activity and go back to log in
                finish();
            }
        });
    }
}
