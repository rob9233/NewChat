package robfernandes.xyz.newchat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    private TextView logInTextView;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerBtn;
    private Button addImageBtn;
    private ImageView image;
    private FirebaseAuth mAuth;
    private Uri mImageUri;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final int PICK_IMAGE_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mImageUri = null;
        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
        logInTextView = findViewById(R.id.activity_register_log_in_text);
        usernameEditText = findViewById(R.id.activity_register_username);
        emailEditText = findViewById(R.id.activity_register_email);
        passwordEditText = findViewById(R.id.activity_register_password);
        registerBtn = findViewById(R.id.activity_register_register_btn);
        addImageBtn = findViewById(R.id.activity_register_add_photo_btn);
        image = findViewById(R.id.activity_register_image_image_view);
    }

    private void setClickListeners() {
        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish activity and go back to log in
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
                        || passwordString.isEmpty()
                        || mImageUri == null) {
                    displayToast("All fields must be answer, please try again");
                } else {
                    registerUser(emailString, passwordString);
                }
            }
        });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST_CODE) {
            mImageUri = data.getData();
            image.setImageURI(mImageUri);
            addImageBtn.setVisibility(View.INVISIBLE);
            image.setVisibility(View.VISIBLE);
        }
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            displayToast("Register succeed");
                            saveUserInfoInFirebase();
                        } else {
                            // If sign in fails, display a message to the user.
                            displayToast("Register failed, please try again");
                        }
                    }
                });
    }

    private void saveUserInfoInFirebase() {
        String filename = generateRandomFileName();

        final StorageReference storageReference = FirebaseStorage
                .getInstance()
                .getReference()
                .child("images/" + filename);

        storageReference.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference
                                .getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String profileImageUrl;
                                String username;
                                String uid;

                                profileImageUrl = uri.toString();
                                username = usernameEditText.getText().toString();
                                uid = FirebaseAuth.getInstance().getUid();

                                User user = new User(uid, username, profileImageUrl);

                                FirebaseFirestore.getInstance().collection("users")
                                        //to add with a random name the document
/*                                        .add(user)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        updateUI();
                                    }})*/
                                // to add with a selected name
                                        .document(uid)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                updateUI();
                                            }
                                        })
                                        //until here
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                            }
                        });
                    //
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void updateUI() {
            Intent intent = new Intent(this, MessageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
    }

    private void displayToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private String generateRandomFileName() {
        return UUID.randomUUID().toString();
    }
}
