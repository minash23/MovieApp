package mina.app.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class logInActivity extends AppCompatActivity {
    Boolean initializedValue = false;
    Button logInButton;
    Button SignUpButton;
    EditText userInput;
    EditText passInput;
    FirebaseUser user;

    View.OnClickListener logListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signInAccount(userInput.getText().toString().trim(), passInput.getText().toString().trim());
        }
    };

    View.OnClickListener signListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), signUpActivity.class);
            startActivity(intent);
        }
    };

    public interface InitializedValueCallback {
        void onInitializedValue(boolean initializedValue);
    }

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logInButton = findViewById(R.id.create_button);
        passInput = findViewById(R.id.password_input);
        userInput = findViewById(R.id.username_input);
        SignUpButton = findViewById(R.id.sign_up_button);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        logInButton.setOnClickListener(logListener);
        SignUpButton.setOnClickListener(signListener);
    }

    public void signInAccount(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String email = mAuth.getCurrentUser().getEmail().toString();
                            getInitializedValue(email, new InitializedValueCallback() {
                                @Override
                                public void onInitializedValue(boolean initializedValue) {
                                    Log.d("Final Initialized Value", String.valueOf(initializedValue));

                                    if (initializedValue) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(getApplicationContext(), Initialization.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        } else {
                            Log.w("Hi", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void getInitializedValue(String userEmail, InitializedValueCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("/Users");

        usersRef.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String userKey = dataSnapshot.getChildren().iterator().next().getKey();
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("/Users/" + userKey);

                            userRef.child("initialized").addListenerForSingleValueEvent(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                boolean initializedValue = dataSnapshot.getValue(Boolean.class);
                                                Log.d("Initialized Here?", String.valueOf(initializedValue));
                                                callback.onInitializedValue(initializedValue);
                                            } else {
                                                Log.d("Initialized", "Value not found");
                                                callback.onInitializedValue(false);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Log.e("Firebase", "Error retrieving initialized value: " + databaseError.getMessage());
                                            callback.onInitializedValue(false);
                                        }
                                    });
                        } else {
                            Log.d("User", "User not found");
                            callback.onInitializedValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("Firebase", "Error querying users: " + databaseError.getMessage());
                        callback.onInitializedValue(false);
                    }
                });
    }
}
