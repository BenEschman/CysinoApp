package com.example.cycino;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class ViewFriendActivity extends AppCompatActivity {

    private Button friendsButton;
    RequestQueue requestQueue;
    private LinearLayout viewLayout;
    private TextView nameText, biographyText;



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfriend);
        friendsButton = findViewById(R.id.fPage);
        viewLayout = findViewById(R.id.layout_view);
        nameText = findViewById(R.id.userNameText);
        biographyText = findViewById(R.id.biographyText);


        requestQueue = Volley.newRequestQueue(ViewFriendActivity.this);
        friendsButton.setText("Return to Friends Page");

        Intent intent = getIntent();
        Integer userID = intent.getIntExtra("UUID",0);
        Integer loggedInUser = intent.getIntExtra("lUUID",0);
        String userName = intent.getStringExtra("USERNAME");

        System.out.println(userID);


        getOneUser(userID);


        //getAllUsers();



        friendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewFriendActivity.this, FriendPageActivity.class);
                intent.putExtra("UUID",loggedInUser);
                intent.putExtra("USERNAME",userName);
                startActivity(intent);
            }
        });


    }

    private void showOneUser(JSONObject user) {
        TextView nameOut = new TextView(this);
        TextView phoneNumOut = new TextView(this);

        nameOut.setTextSize(30);
        phoneNumOut.setTextSize(20);


        try {

            String out = user.getString("firstName") + " " + user.getString("lastName");
            String biography = user.getString("userBiography");

            nameText.setText(out);
            biographyText.setText(biography);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

        private void getOneUser(Integer id) {
            //String url = "https://10c011fe-3b08-4ae2-96a7-71049edb34ae.mock.pstmn.io/getData";
         String url = "http://coms-3090-052.class.las.iastate.edu:8080/users/"+id;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(),"view worked", Toast.LENGTH_LONG).show();

                            showOneUser(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getApplicationContext(),"view failed", Toast.LENGTH_LONG).show();
                }
            });

            // Add the request to the RequestQueue
            requestQueue.add(jsonObjectRequest);
        }

}
