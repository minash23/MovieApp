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

import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.androidnetworking.interfaces.ParsedRequestListener;

import org.json.JSONObject;

import java.lang.reflect.Array;

public class resultsActivity extends AppCompatActivity {

    String value;
    String[] array;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            value = extras.getString("vibe");
            array = extras.getStringArray("ArrayOfButtons");

        }

        imageView = findViewById(R.id.I1);



        //String imageURL = "https://api.themoviedb.org/3/movie/" + getPoster_path() + "jpg";
        //Picasso.get().load(imageURL).into(imageView);
       // ((TextView) findViewById(R.id.T1)).setText(getTitle());
    }

    JSONObjectRequestListener requestListener = new JSONObjectRequestListener() {
        @Override
        public void onResponse(JSONObject response) {

        }

        @Override
        public void onError(ANError anError) {

        }
    };
    private void makeRequest(String id) {
        //https://api.themoviedb.org/3/search/movie?query={Movie Name}&api_key=dbc8826d07a32ea3f193cf0541626b5e
        ANRequest req = AndroidNetworking.get("https://api.themoviedb.org/3/movie/{id}?api_key=dbc8826d07a32ea3f193cf0541626b5e")
                .addPathParameter("id", id)
                .setPriority(Priority.LOW)
                .build();
        req.getAsJSONObject(requestListener);
    }

}
