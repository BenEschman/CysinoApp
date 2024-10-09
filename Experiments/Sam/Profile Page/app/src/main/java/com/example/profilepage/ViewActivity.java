package com.example.profilepage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewActivity extends AppCompatActivity {

    private Button viewButton;
    RequestQueue requestQueue;
    private TextView nameOut;
    private TextView phoneNumOut;

    Map<String, String> serverOut = new HashMap<>();
    JSONArray jArr;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        viewButton = findViewById(R.id.returnButton);
        nameOut = findViewById(R.id.nameOut);
        phoneNumOut = findViewById(R.id.phoneNumOut);

        requestQueue = Volley.newRequestQueue(ViewActivity.this);
        viewButton.setText("Return to Edit");



        fetchDataFromBackend(6);



        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateScreen(JSONObject user) {

        try {

            String out = user.getString("firstName") + " " + user.getString("lastName");

            nameOut.setText(out);
            phoneNumOut.setText(user.getString("phoneNumber"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }




        private void fetchDataFromBackend(Integer id) {
            //String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/getData";
         String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/"+id;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(),"It worked", Toast.LENGTH_LONG).show();

                                Toast.makeText(getApplicationContext(),"It worked", Toast.LENGTH_LONG).show();
                                updateScreen(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(),"It failed", Toast.LENGTH_LONG).show();
                }
            });

            // Add the request to the RequestQueue
            requestQueue.add(jsonObjectRequest);
        }
}
