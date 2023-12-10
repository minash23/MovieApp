package mina.app.movieapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    EditText vibe;
    Button dramaButton;
    Button goodButton;
    Button actionButton;

    ArrayList<String> selectedTypes = new ArrayList<>();

    View.OnClickListener typeSelect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String textonbutton = b.getText().toString();
            if(!selectedTypes.contains(textonbutton)){
                b.setBackgroundColor("9747FF".hashCode());
                selectedTypes.add(textonbutton);
            }else{
                b.setBackgroundColor("d3d3d3".hashCode());
                selectedTypes.remove(textonbutton);
            }
        }
    };

    public void onClick(View view){
        Intent intent = new Intent(getApplicationContext(), resultsActivity.class);
        intent.putExtra("vibe", vibe.getText().toString().trim());
        intent.putExtra("ArrayOfButtons", selectedTypes);

        if (!selectedTypes.isEmpty()) {
            intent.putExtra("genre", selectedTypes.get(0));
        }

        Log.d("vibe", vibe.getText().toString().trim());
        Log.d("Array", selectedTypes.toString());

        //hello
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibe = findViewById(R.id.vibeText);
        dramaButton = findViewById(R.id.dramaBT);
        goodButton = findViewById(R.id.feelgoodBT);
        actionButton = findViewById(R.id.actionBT);

        dramaButton.setOnClickListener(typeSelect);
        goodButton.setOnClickListener(typeSelect);
        actionButton.setOnClickListener(typeSelect);

    }
}