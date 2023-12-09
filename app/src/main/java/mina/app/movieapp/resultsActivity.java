package mina.app.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class resultsActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        imageView = findViewById(R.id.I1);
    }

    private void makeRequest(String id){
        ANRequest req = AndroidNetworking.get("https://api.themoviedb.org/3/movie/{id}?api_key=dbc8826d07a32ea3f193cf0541626b5e")
                        .addPathParameter("id", id)
                        .setPriority(Priority.LOW)
                        .build();
        req.getAsJSONObject(requestListener);

                String imageURL = "https://api.themoviedb.org/3/movie/" + getPoster_path() + "jpg";

                Picasso.get().load(imageURL).into(imageView);

                ((TextView) findViewById(R.id.T1)).setText(getTitle());

        //https://api.themoviedb.org/3/search/movie?query={Movie Name}&api_key=dbc8826d07a32ea3f193cf0541626b5e
    }

}