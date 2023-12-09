package mina.app.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;

public class movieInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        AndroidNetworking.initialize(getApplicationContext());
    }

    private void makeRequest(String id){
        Log.d("url", "https://api.themoviedb.org/3/movie/{id}?api_key=dbc8826d07a32ea3f193cf0541626b5e");
        ANRequest req =
                AndroidNetworking.get("https://api.themoviedb.org/3/movie/{id}?api_key=dbc8826d07a32ea3f193cf0541626b5e")
                        .addPathParameter("name", id)
                        .setPriority(Priority.LOW)
                        .build();
        //req.getAsJSONObject(requestListener);
        //https://api.themoviedb.org/3/search/movie?query={Movie Name}&api_key=dbc8826d07a32ea3f193cf0541626b5e
    }
}