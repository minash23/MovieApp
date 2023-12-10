package mina.app.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class resultsActivity extends AppCompatActivity {

    String value;
    String genreId;
    String[] array;
    ImageView imageView;
    ImageButton returnButton;
    Button againButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        imageView = findViewById(R.id.I1);
        returnButton = findViewById(R.id.comedyBT4);
        againButton = findViewById(R.id.B2);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String movieID = String.valueOf(extras.getInt("ID"));
            Log.d("movie", movieID);
            if (movieID != null && Integer.parseInt(movieID) != 0) {
                makeRequestInf(movieID);
                makeRequestInf2(movieID);
                makeRequest3(movieID);
                movieID = null;
            } else {
                value = extras.getString("vibe");
                array = extras.getStringArray("ArrayOfButtons");
                String genre = extras.getString("genre");

                // Map the genre to its corresponding ID
                String genreId = mapGenreToId(genre);

                if (genreId != null) {
                    makeRequest(genreId);
                    makeRequest2(genreId);
                    makeRequest3(genreId);
                }
            }
        }

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(resultsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        againButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest(genreId);
                makeRequest2(genreId);
                makeRequest3(genreId);
            }
        });
    }
    public String returnGens(JSONArray array) throws JSONException {
        String genres = "";
        for (int i = 0; i < array.length(); i++){
            JSONObject genreObject = array.getJSONObject(i);
            String genreName = genreObject.getString("name");
            genres += genreName + ", ";
        }
        return genres;
    }

    JSONObjectRequestListener requestListener2 = new JSONObjectRequestListener() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                String title = response.getString("original_title");
                ((TextView) findViewById(R.id.T1)).setText(title);

                JSONArray genres = response.getJSONArray("genres");
                Log.d("gen", genres.toString());
                String genre = returnGens(genres);
                Log.d("gen", genre);
                ((TextView) findViewById(R.id.T2)).setText(genre);

                String date = response.getString("release_date");
                String year = date.split("-")[0]; // This will display the year only
                ((TextView) findViewById(R.id.T4)).setText(year);

                String posterPath = response.getString("poster_path");
                String imageURL = "https://image.tmdb.org/t/p/w500" + posterPath;
                Picasso.get().load(imageURL).into(imageView);

            } catch (JSONException e) {
                Log.e("JSON Parsing Error", "Error parsing JSON response", e);
            }
        }

        @Override
        public void onError(ANError anError) {
            Log.e("API Error", "onError: " + anError.getErrorDetail());
        }
    };


    JSONObjectRequestListener requestListener = new JSONObjectRequestListener() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray results = response.getJSONArray("results");
                int randomIndex = new Random().nextInt(results.length());
                JSONObject randomMovie = results.getJSONObject(randomIndex);

                String title = randomMovie.getString("original_title");

                ((TextView) findViewById(R.id.T1)).setText(title);

                JSONArray genreIds = randomMovie.getJSONArray("genre_ids");
                String genre = mapGenreIdsToNames(genreIds);
                ((TextView) findViewById(R.id.T2)).setText(genre);

                String date = randomMovie.getString("release_date");
                String year = date.split("-")[0]; // This will display the year only
                ((TextView) findViewById(R.id.T4)).setText(year);

                String posterPath = randomMovie.getString("poster_path");
                String imageURL = "https://image.tmdb.org/t/p/w500" + posterPath;
                Picasso.get().load(imageURL).into(imageView);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(ANError anError) {
            Log.e("API Error", "onError: " + anError.getErrorDetail());
        }
    };

    private void makeRequest(String genreId) {
        ANRequest req = AndroidNetworking.get("https://api.themoviedb.org/3/discover/movie?api_key=dbc8826d07a32ea3f193cf0541626b5e&with_genres={genreId}")
                .addPathParameter("genreId", genreId)
                .setPriority(Priority.LOW)
                .build();
        req.getAsJSONObject(requestListener);
    }

    private void makeRequestInf(String movieId) {
        ANRequest req = AndroidNetworking.get("https://api.themoviedb.org/3/movie/{id}?api_key=dbc8826d07a32ea3f193cf0541626b5e")
                .addPathParameter("id", movieId)
                .setPriority(Priority.LOW)
                .build();
        req.getAsJSONObject(requestListener2);
    }
    private void makeRequestInf2(String id) {
        ANRequest req = AndroidNetworking.get("https://api.themoviedb.org/3/movie/{id}/release_dates?api_key=dbc8826d07a32ea3f193cf0541626b5e")
                .addPathParameter("id", id)
                .setPriority(Priority.LOW)
                .build();
        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    if (results.length() > 0) {
                        JSONObject releaseInfo = results.getJSONObject(0);

                        String certification = releaseInfo.getString("release_date");
                        ((TextView) findViewById(R.id.T5)).setText(certification);

                        int runtime = releaseInfo.getInt("runtime");
                        ((TextView) findViewById(R.id.T3)).setText(runtime + " mins");
                    } else {
                        Log.e("API Error", "No release date information found.");
                    }
                } catch (JSONException e) {
                    Log.e("JSON Parsing Error", "Error parsing JSON response", e);
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("API Error", "onError: " + anError.getErrorDetail());
            }
        });
    }


    private void makeRequest2(String id) {
        ANRequest req = AndroidNetworking.get("https://api.themoviedb.org/3/movie/{id}/release_dates?api_key=dbc8826d07a32ea3f193cf0541626b5e")
                .addPathParameter("id", id)
                .setPriority(Priority.LOW)
                .build();
        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);
                        if (result.getString("iso_3166_1").equals("US")) {
                            JSONArray releaseDates = result.getJSONArray("release_dates");
                            for (int j = 0; j < releaseDates.length(); j++) {
                                JSONObject releaseDate = releaseDates.getJSONObject(j);
                                String certification = releaseDate.getString("certification");
                                if (!certification.isEmpty()) {
                                    ((TextView) findViewById(R.id.T5)).setText(certification);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("API Error", "onError: " + anError.getErrorDetail());
            }
        });
    }

    private void makeRequest3(String id) {
        ANRequest req = AndroidNetworking.get("https://api.themoviedb.org/3/movie/{id}?api_key=dbc8826d07a32ea3f193cf0541626b5e")
                .addPathParameter("id", id)
                .setPriority(Priority.LOW)
                .build();
        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int runtime = response.getInt("runtime");
                    ((TextView) findViewById(R.id.T3)).setText(runtime + " mins");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("API Error", "onError: " + anError.getErrorDetail());
            }
        });
    }

    public void onClickTwo(View view){
        Intent intent = new Intent(getApplicationContext(), profileActivity.class);
        startActivity(intent);
    }



    private String mapGenreIdsToNames(JSONArray genreIds) {
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < genreIds.length(); i++) {
            try {
                int id = genreIds.getInt(i);
                String genre = mapIdToGenre(id);
                if (genre != null) {
                    if (genres.length() > 0) {
                        genres.append(", ");
                    }
                    genres.append(genre);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return genres.toString();
    }

    private String mapGenreToId(String genre) {
        switch (genre.toLowerCase()) {
            case "action":
                return "28";
            case "adventure":
                return "12";
            case "animation":
                return "16";
            case "comedy":
                return "35";
            case "crime":
                return "80";
            case "documentary":
                return "99";
            case "drama":
                return "18";
            case "family":
                return "10751";
            case "fantasy":
                return "14";
            case "history":
                return "36";
            case "horror":
                return "27";
            case "music":
                return "10402";
            case "mystery":
                return "9648";
            case "romance":
                return "10749";
            case "science fiction":
                return "878";
            case "tv movie":
                return "10770";
            case "thriller":
                return "53";
            case "war":
                return "10752";
            case "western":
                return "37";
            default:
                return null;
        }
    }
    private String mapIdToGenre(int id) {
        switch (id) {
            case 28:
                return "Action";
            case 12:
                return "Adventure";
            case 16:
                return "Animation";
            case 35:
                return "Comedy";
            case 80:
                return "Crime";
            case 99:
                return "Documentary";
            case 18:
                return "Drama";
            case 10751:
                return "Family";
            case 14:
                return "Fantasy";
            case 36:
                return "History";
            case 27:
                return "Horror";
            case 10402:
                return "Music";
            case 9648:
                return "Mystery";
            case 10749:
                return "Romance";
            case 878:
                return "Science Fiction";
            case 10770:
                return "TV Movie";
            case 53:
                return "Thriller";
            case 10752:
                return "War";
            case 37:
                return "Western";
            default:
                return null;
        }
    }
}
