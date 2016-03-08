package com.example.paarthmadan.giphyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String baseUrl = "http://api.giphy.com/v1/gifs/";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(baseUrl + "search?q=funny+cat&api_key=dc6zaTOxFJmzC", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    System.out.println("Success");

                    String json = new String(response);

                    try {
                        JSONObject allJSON = new JSONObject(json);
                        JSONObject imageData = allJSON.getJSONArray("data").getJSONObject(0).getJSONObject("images");
                        JSONObject image = imageData.getJSONObject("fixed_height");
                        String imageUrl = image.get("url").toString();

                        System.out.println(imageUrl);

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
