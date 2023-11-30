package mina.app.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;

public class Initialization extends AppCompatActivity {

    String[] selectedGenres = new String[11];
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
            if(b.getBackgroundTintList().hashCode() == "d3d3d3".hashCode()){
                b.setBackgroundColor("9747FF".hashCode());
                addToGenreArray(textonbutton);
            }else{
                b.setBackgroundColor("d3d3d3".hashCode());
                int index = findInGenreArray(textonbutton);
                selectedGenres[index] = null;
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

    public int findInGenreArray(String genre){
        for(int i = 0; i < selectedGenres.length; i++){
            if(genre.equals(selectedGenres[i])){
                return i;
            }
        }
        return -1;
    }

    public void addToGenreArray(String genre){
        for(int i = 0; i < selectedGenres.length;i++){
            if(selectedGenres[i] == null){
                selectedGenres[i] = genre;
                return;
            }
        }
    }

}