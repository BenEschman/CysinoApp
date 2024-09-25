package com.example.profilepage;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ViewActivity extends AppCompatActivity {


    private Button viewButton;
    private Button updateButton;
    RequestQueue requestQueue;
    private TextView fNameOut;
    private TextView lNameOut;

    Map<String, String> serverOut = new HashMap<>();
    JSONArray jArr;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        viewButton = findViewById(R.id.returnButton);
        updateButton = findViewById(R.id.updateButton);
        fNameOut = findViewById(R.id.firstNameOut);
        lNameOut = findViewById(R.id.lastNameOut);

        requestQueue = Volley.newRequestQueue(ViewActivity.this);
        viewButton.setText("Return to Edit");



        fetchDataFromBackend();









        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    fNameOut.setText(serverOut.get("fName"));
                    lNameOut.setText(serverOut.get("lName"));

                for (int i = 0; i < jArr.length(); i++) {
                    String key;
                    String value;
                    try {

                        JSONObject jObj = new JSONObject(String.valueOf(jArr.get(i)));
                        key = jObj.getString("key");
                        value = jObj.getString("value");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    if (key.equals("fName")){
                        fNameOut.setText(value);
                    } else if (key.equals("lName")) {
                        lNameOut.setText(value);
                    }

                }


            }
        });




    }


        private void fetchDataFromBackend() {
         String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/getData";

        // Create a request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            jArr = new JSONArray(response);
                            System.out.println(jArr);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Display error message
                Toast.makeText(ViewActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the request queue
        requestQueue.add(stringRequest);
    }
}
