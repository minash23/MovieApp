package mina.app.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity {
    Button createButton;
    Button backButton;

    EditText emailInput;
    EditText passInput;
    EditText firstInput;
    EditText lastInput;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), logInActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener createListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(passInput.getText().toString().trim().length() > 0 && firstInput.getText().toString().trim().length() > 0  && lastInput.getText().toString().trim().length() > 0 && emailInput.getText().toString().trim().length() > 0){
                createAccount(emailInput.getText().toString().trim(), passInput.getText().toString().trim());
            }else{
                Toast.makeText(getApplicationContext(), "Please Fill in All Fields.", Toast.LENGTH_LONG).show();
            }
        }
    };

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User();
                            user.setEmail(email);
                            user.setFirstName(firstInput.getText().toString().trim());
                            user.setLastName(lastInput.getText().toString().trim());

                            // Get a reference to the root of the database
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                            // Reference to the "Users" branch and push a\ new unique key
                            DatabaseReference usersRef = rootRef.child("Users").push();

                            // Set the user data under the generated key
                            usersRef.setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // User data added successfully
                                            // You can add further actions here if needed
                                            Toast.makeText(getApplicationContext(), "User added to database", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Failed to add user data
                                            Toast.makeText(getApplicationContext(), "Failed to add user to database", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                            passInput.setText("");
                            firstInput.setText("");
                            lastInput.setText("");
                            emailInput.setText("");
                            Toast.makeText(getApplicationContext(), "User Has Been Created!", Toast.LENGTH_LONG).show();
                            Log.d("Hi", "createUserWithEmail:success");

                            //user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("hi", "createUserWithEmail:failure", task.getException());

                            // Handle different error cases
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "Email Address: " + email + " is already in use.", Toast.LENGTH_LONG).show();
                            } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(getApplicationContext(), "Your Password is not strong enough.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Account Creation failed.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        createButton = findViewById(R.id.create_button);
        backButton = findViewById(R.id.back_button);
        emailInput = findViewById(R.id.email_input);
        passInput = findViewById(R.id.password_input);
        firstInput = findViewById(R.id.first_name_input);
        lastInput = findViewById(R.id.last_name_input);

        createButton.setOnClickListener(createListener);
        backButton.setOnClickListener(backListener);

        mAuth = FirebaseAuth.getInstance();
    }
}