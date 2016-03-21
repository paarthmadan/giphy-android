package com.example.paarthmadan.giphyapp;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.*;
import org.json.JSONObject;
import com.squareup.picasso.*;



import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    final String baseUrl = "http://api.giphy.com/v1/gifs/search";
    final String apiKey = "dc6zaTOxFJmzC";
    ImageView imageV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button)findViewById(R.id.button);
        imageV = (ImageView)findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.input);
                String queryText = text.getText().toString();


                //hides keyboard onClick
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                fetch(queryText);

            }
        });




    }

    public void fetch(String query){


        String url = baseUrl + "?q=" + query + "&api_key="  + apiKey;


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String json = new String(response);

                try {
                    JSONObject allJSON = new JSONObject(json);
                    JSONObject imageData = allJSON.getJSONArray("data").getJSONObject(0).getJSONObject("images");
                    JSONObject image = imageData.getJSONObject("fixed_height");
                    String imageUrl = image.get("url").toString();

                    System.out.println(imageUrl);

                    Picasso.with(MainActivity.this).load(imageUrl).into(imageV);




                } catch (Exception e){
                    System.out.println(e.getMessage());
                }




            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });



    }
}
