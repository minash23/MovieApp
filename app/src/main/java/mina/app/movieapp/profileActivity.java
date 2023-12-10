package mina.app.movieapp;
// Import statements
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

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

    ImageView one2;
    ImageView two2;
    ImageView three2;
    ImageView four2;
    ImageView five2;



    ArrayList<String> selectedGenres = new ArrayList<>();

    int oneID;
    int twoID;
    int threeID;
    int fourID;
    int fiveID;
    int oneID2;
    int twoID2;
    int threeID2;
    int fourID2;
    int fiveID2;


    public static int getRandomNumber() {
        // Creating a Random object
        Random random = new Random();

        // Generating a random number between 1 and 100,000
        int randomNumber = random.nextInt(100000) + 1;

        return randomNumber;
    }

    public void movieOnClick(View view){
        Intent intent = new Intent(getApplicationContext(), resultsActivity.class);
        if (view == findViewById(R.id.movieOneImage)){
            intent.putExtra("ID", oneID);
        }
        else if(view == findViewById(R.id.movieTwoImage)){
            intent.putExtra("ID", twoID);
        }
        else if(view == findViewById(R.id.movieThreeImage)){
            intent.putExtra("ID", threeID);
        }
        else if(view == findViewById(R.id.movieFourImage)){
            intent.putExtra("ID", fourID);
        }
        else if(view == findViewById(R.id.movieFiveImage)){
            intent.putExtra("ID", fiveID);
        }
        else if(view == findViewById(R.id.movieOneImage2)){
            intent.putExtra("ID", oneID2);
        }
        else if(view == findViewById(R.id.movieTwoImage2)){
            intent.putExtra("ID", twoID2);
        }
        else if(view == findViewById(R.id.movieThreeImage2)){
            intent.putExtra("ID", threeID2);
        }
        else if(view == findViewById(R.id.movieFourImage2)){
            intent.putExtra("ID", fourID2);
        }
        else if(view == findViewById(R.id.movieFiveImage2)){
            intent.putExtra("ID", fiveID2);
        }
        startActivity(intent);
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
                                else if(imageView == two){
                                    twoID = Integer.parseInt(id);
                                }
                                else if(imageView == three){
                                    threeID = Integer.parseInt(id);
                                }
                                else if(imageView == four){
                                    fourID = Integer.parseInt(id);
                                }
                                else if(imageView == five){
                                    fiveID = Integer.parseInt(id);
                                }
                                if(imageView == one2){
                                    oneID2 = Integer.parseInt(id);
                                }
                                if(imageView == two2){
                                    twoID2 = Integer.parseInt(id);
                                }
                                if(imageView == three2){
                                    threeID2 = Integer.parseInt(id);
                                }
                                if(imageView == four2){
                                    fourID2 = Integer.parseInt(id);
                                }
                                if(imageView == five2){
                                    fiveID2 = Integer.parseInt(id);
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
    public void mainClick(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
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
        one2 = findViewById(R.id.movieOneImage2);
        two2 = findViewById(R.id.movieTwoImage2);
        three2 = findViewById(R.id.movieThreeImage2);
        four2 = findViewById(R.id.movieFourImage2);
        five2 = findViewById(R.id.movieFiveImage2);

        // Example usage of updateSelectedGenres
        String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim();

        makeRequest3(String.valueOf(getRandomNumber()), one);
        makeRequest3(String.valueOf(getRandomNumber()), two);
        makeRequest3(String.valueOf(getRandomNumber()), three);
        makeRequest3(String.valueOf(getRandomNumber()), four);
        makeRequest3(String.valueOf(getRandomNumber()), five);
        makeRequest3(String.valueOf(getRandomNumber()), one2);
        makeRequest3(String.valueOf(getRandomNumber()), two2);
        makeRequest3(String.valueOf(getRandomNumber()), three2);
        makeRequest3(String.valueOf(getRandomNumber()), four2);
        makeRequest3(String.valueOf(getRandomNumber()), five2);


    }
}
