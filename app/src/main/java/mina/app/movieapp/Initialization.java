package mina.app.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Initialization extends AppCompatActivity {

    ArrayList<String> selectedGenres = new ArrayList<>();
    ArrayList<String> selectedStreamPF = new ArrayList<>();
    FirebaseUser user;
    FirebaseAuth mAuth;
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

    ImageView appleIV;
    ImageView disneyIV;
    ImageView hboIV;
    ImageView huluIV;
    ImageView netflixIV;
    ImageView peacockIV;
    ImageView primeIV;

    View.OnClickListener genreSelect = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String textonbutton = b.getText().toString();
            if(!selectedGenres.contains(textonbutton)){
                b.setBackgroundColor(getResources().getColor(R.color.purple));
                b.setTextColor(getResources().getColor(R.color.white));
                selectedGenres.add(textonbutton);
            }else{
                b.setBackgroundColor(getResources().getColor(R.color.grey));
                b.setTextColor(getResources().getColor(R.color.black));
                selectedGenres.remove(textonbutton);
            }
        }
    };

    View.OnClickListener letsWatch = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
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

        appleIV = findViewById(R.id.appleIV);
        disneyIV = findViewById(R.id.disneyIV);
        hboIV = findViewById(R.id.hboIV);
        huluIV = findViewById(R.id.huluIV);
        netflixIV = findViewById(R.id.netflixIV);
        primeIV = findViewById(R.id.primeIV);
        peacockIV = findViewById(R.id.peacockIV);

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
        letswatchBT.setOnClickListener(letsWatch);

        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail().toString();
    }

    public void onClick(View v) {
        ImageView iv = (ImageView) v;
        if(iv == appleIV){
            if(appleIV.getImageAlpha() == 255){
                selectedStreamPF.remove("apple");
                appleIV.setImageAlpha(127);
            }else{
                selectedStreamPF.add("apple");
                appleIV.setImageAlpha(255);
            }
        } else if (iv == disneyIV) {
            if(disneyIV.getImageAlpha() == 255){
                selectedStreamPF.remove("disney");
                disneyIV.setImageAlpha(127);
            }else{
                selectedStreamPF.add("disney");
                disneyIV.setImageAlpha(255);
            }
        } else if (iv == hboIV) {
            if(hboIV.getImageAlpha() == 255){
                selectedStreamPF.remove("hbo");
                hboIV.setImageAlpha(127);
            }else{
                selectedStreamPF.add("hbo");
                hboIV.setImageAlpha(255);
            }
        } else if (iv == huluIV) {
            if(huluIV.getImageAlpha() == 255){
                selectedStreamPF.remove("hulu");
                huluIV.setImageAlpha(127);
            }else{
                selectedStreamPF.add("hulu");
                huluIV.setImageAlpha(255);
            }
        } else if (iv == netflixIV) {
            if(netflixIV.getImageAlpha() == 255){
                selectedStreamPF.remove("netflix");
                netflixIV.setImageAlpha(127);
            }else{
                selectedStreamPF.add("netflix");
                netflixIV.setImageAlpha(255);
            }
        } else if (iv == peacockIV) {
            if(peacockIV.getImageAlpha() == 255){
                selectedStreamPF.remove("peacock");
                peacockIV.setImageAlpha(127);
            }else{
                selectedStreamPF.add("peacock");
                peacockIV.setImageAlpha(255);
            }
        } else if (iv == primeIV) {
            if(primeIV.getImageAlpha() == 255){
                selectedStreamPF.remove("prime");
                primeIV.setImageAlpha(127);
            }else{
                selectedStreamPF.add("prime");
                primeIV.setImageAlpha(255);
            }
        }
    }



}