package mina.app.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Initialization extends AppCompatActivity {

    ArrayList<String> selectedGenres = new ArrayList<>();
    Button actionBT;
    Button feelgoodBT;
    Button dramaBT;
    Button awardwinnerBT;
    Button comedyBT;
    Button arthouseBT;
    Button thrillerBT;
    Button horrorBT;
    Button kidsmovieBT;
    Button documentaryBT;
    Button truestoryBT;
    Button letswatchBT;

    View.OnClickListener genreSelect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String textonbutton = b.getText().toString();
            if(!selectedGenres.contains(textonbutton)){
                b.setBackgroundColor("9747FF".hashCode());
                selectedGenres.add(textonbutton);
            }else{
                b.setBackgroundColor("d3d3d3".hashCode());
                selectedGenres.remove(textonbutton);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialization);

        actionBT = findViewById(R.id.actionBT);
        feelgoodBT = findViewById(R.id.feelgoodBT);
        dramaBT = findViewById(R.id.dramaBT);
        awardwinnerBT = findViewById(R.id.awardwinnersBT);
        comedyBT = findViewById(R.id.comedyBT);
        arthouseBT = findViewById(R.id.arthouseBT);
        thrillerBT = findViewById(R.id.thrillerBT);
        horrorBT = findViewById(R.id.horrorBT);
        kidsmovieBT = findViewById(R.id.kidsmovieBT);
        documentaryBT = findViewById(R.id.documentaryBT);
        truestoryBT = findViewById(R.id.truestoryBT);
        letswatchBT = findViewById(R.id.letswatchBT);

        actionBT.setOnClickListener(genreSelect);
        feelgoodBT.setOnClickListener(genreSelect);
        dramaBT.setOnClickListener(genreSelect);
        awardwinnerBT.setOnClickListener(genreSelect);
        comedyBT.setOnClickListener(genreSelect);
        arthouseBT.setOnClickListener(genreSelect);
        thrillerBT.setOnClickListener(genreSelect);
        horrorBT.setOnClickListener(genreSelect);
        kidsmovieBT.setOnClickListener(genreSelect);
        documentaryBT.setOnClickListener(genreSelect);
        truestoryBT.setOnClickListener(genreSelect);
    }
}