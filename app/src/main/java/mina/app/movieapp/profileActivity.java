package mina.app.movieapp;
// Import statements
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.common.ANRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.AndroidNetworking;
import com.squareup.picasso.Picasso;

public class profileActivity extends AppCompatActivity {
    ImageView one;
    ImageView two;
    ImageView three;
    ImageView four;
    ImageView five;

    ArrayList<String> selectedGenres = new ArrayList<>();

    int oneID;
    int twoID;
    int threeID;
    int fourID;
    int fiveID;

    public static int getRandomNumber() {
        // Creating a Random object
        Random random = new Random();

        // Generating a random number between 1 and 100,000
        int randomNumber = random.nextInt(100000) + 1;

        return randomNumber;
    }

    private void makeRequest3(String id, ImageView imageView) {
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/{id}?api_key=dbc8826d07a32ea3f193cf0541626b5e")
                .addPathParameter("id", id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String posterPath = response.getString("poster_path");
                            if (posterPath != null && !posterPath.isEmpty()) {
                                String uri = "https://image.tmdb.org/t/p/w500" + posterPath;
                                // Update the UI only after the network request is complete
                                updateUI(uri, imageView);
                                if(imageView == one){
                                    oneID = Integer.parseInt(id);
                                }
                                if(imageView == two){
                                    twoID = Integer.parseInt(id);
                                }
                                if(imageView == one){
                                    threeID = Integer.parseInt(id);
                                }
                                if(imageView == one){
                                    fourID = Integer.parseInt(id);
                                }
                                if(imageView == one){
                                    fiveID = Integer.parseInt(id);
                                }
                            } else {
                                // Handle the case where posterPath is null or empty
                                // Continue trying with a new random number
                                makeRequest3(String.valueOf(getRandomNumber()), imageView);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("API Error", "onError: " + anError.getErrorDetail());
                        // Handle the error case
                        // Continue trying with a new random number
                        makeRequest3(String.valueOf(getRandomNumber()), imageView);
                    }
                });
    }



    private void updateUI(String uri, ImageView one) {
        // Update UI elements here
        Picasso.get().load(uri).into(one);
        Log.d("curr", uri);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize your ImageViews
        one = findViewById(R.id.movieOneImage);
        two = findViewById(R.id.movieTwoImage);
        three = findViewById(R.id.movieThreeImage);
        four = findViewById(R.id.movieFourImage);
        five = findViewById(R.id.movieFiveImage);

        // Example usage of updateSelectedGenres
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim();

        makeRequest3(String.valueOf(getRandomNumber()), one);
        makeRequest3(String.valueOf(getRandomNumber()), two);
        makeRequest3(String.valueOf(getRandomNumber()), three);
        makeRequest3(String.valueOf(getRandomNumber()), four);
        makeRequest3(String.valueOf(getRandomNumber()), five);


    }
}
