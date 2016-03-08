package com.example.paarthmadan.giphyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText)findViewById(R.id.input);
                String queryText = text.getText().toString();


                //hides keyboard onClick
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                System.out.println(queryText);

            }
        });


        final String baseUrl = "http://api.giphy.com/v1/gifs/search";
        final String apiKey = "dc6zaTOxFJmzC";

        String query = "drake";
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
