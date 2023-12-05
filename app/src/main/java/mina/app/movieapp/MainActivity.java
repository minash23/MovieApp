package mina.app.movieapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        //storeUserInFirebase(data);
    }

    //Account checker below
    private DatabaseReference databaseReference;

    private void storeUserInFirebase(User user) {
        String userKey = user.getEmail();

        databaseReference.orderByChild("email").equalTo(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getApplicationContext(), userKey + " already stored in database", Toast.LENGTH_SHORT).show();
                } else {
                    String key = databaseReference.push().getKey();
                    databaseReference.child(key).setValue(user);
                    Toast.makeText(getApplicationContext(), userKey + " added!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error checking duplicate", databaseError.toException());
            }
        });
    }
}