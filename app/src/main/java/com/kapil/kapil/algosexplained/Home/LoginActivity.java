package com.kapil.kapil.algosexplained.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kapil.kapil.algosexplained.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;

public class LoginActivity extends AppCompatActivity {

    private RelativeLayout loginLayout;
    private RelativeLayout signUpLayout;
    private TextView skipLogin;
    private TextView allreadyLogin;
    private TextView allreadySignUp;
    private String url = "https://algorithms-explained-backend.herokuapp.com/";
    public String username;
    private EditText registerUsername, registerEmail,registerPassword;
    private EditText loginEmail,loginPassword;
    private Button loginButton;
    private Button registerButton;
    private ProgressBar progressBar;

    Boolean allRegisterTextCompleted() {
        String valueEmail = registerEmail.getText().toString();
        if (valueEmail == null || valueEmail.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Email",
                    Toast.LENGTH_LONG).show();
            return FALSE;
        }
        String valueUsername = registerUsername.getText().toString();
        if (valueUsername == null || valueUsername.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Username",
                    Toast.LENGTH_LONG).show();
            return FALSE;
        }
        String valuePassword = registerPassword.getText().toString();
        if (valuePassword == null || valuePassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Password",
                    Toast.LENGTH_LONG).show();
            return FALSE;
        }
        return Boolean.TRUE;
    }

    Boolean allLoginTextCompleted() {
        String valueEmail = loginEmail.getText().toString();
        if (valueEmail == null || valueEmail.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Email",
                    Toast.LENGTH_LONG).show();
            return FALSE;
        }
        String valuePassword = loginPassword.getText().toString();
        if (valuePassword == null || valuePassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Password",
                    Toast.LENGTH_LONG).show();
            return FALSE;
        }
        return Boolean.TRUE;
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = findViewById(R.id.progress_circular);

        loginLayout = findViewById(R.id.layout_login);
        signUpLayout = findViewById(R.id.layout_register);

        allreadyLogin = findViewById(R.id.login_screen);
        allreadySignUp = findViewById(R.id.signup_screen);

        registerEmail = findViewById(R.id.register_email);
        registerUsername = findViewById(R.id.register_username);
        registerPassword = findViewById(R.id.register_password);

        loginPassword = findViewById(R.id.login_password);
        loginEmail = findViewById(R.id.login_email);

        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allRegisterTextCompleted()) {
                    showProgressBar();
                    String signupUrl = url + "user/signup";
                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("username", registerUsername.getText().toString());
                        postData.put("email", registerEmail.getText().toString());
                        postData.put("password", registerPassword.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        hideProgressBar();
                    }

                    volleyPost(signupUrl, postData, "Signup");
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allLoginTextCompleted()) {
                    showProgressBar();
                    String loginUrl = url + "user/login";
                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("email", loginEmail.getText().toString());
                        postData.put("password", loginPassword.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        hideProgressBar();
                    }

                    volleyPost(loginUrl, postData, "Login");
                }
            }
        });

        allreadySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
            }
        });

        allreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpLayout.setVisibility(View.GONE);
                loginLayout.setVisibility(View.VISIBLE);
            }
        });

        skipLogin = findViewById(R.id.skip_button);
        skipLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        automaticLogin();
    }

    private void automaticLogin() {
        String loginUrl = url + "user/me";
        showProgressBar();
        volleyGet(loginUrl,"Automatic Login");
    }

    private void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void volleyPost(String postUrl, JSONObject postData, final String operation){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                parseJSON(response,operation);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(LoginActivity.this, operation + " Failed",
                        Toast.LENGTH_LONG).show();
                hideProgressBar();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void volleyGet(String getUrl, final String operation) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getUrl,null,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    username = (String) response.get("username");
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();;
                    myEdit.putString("username", username);
                    myEdit.commit();
                    System.out.println(username);
                    Toast.makeText(LoginActivity.this, operation + " Successfully!!",
                            Toast.LENGTH_LONG).show();
                    hideProgressBar();
                    openMainActivity();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, operation + " Failed",
                            Toast.LENGTH_LONG).show();
                    hideProgressBar();
                    deleteSharedPref();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(LoginActivity.this, operation + " Failed",
                        Toast.LENGTH_LONG).show();
                hideProgressBar();
                deleteSharedPref();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("token", token);

                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void deleteSharedPref() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("token", "");
        myEdit.commit();
    }

    private void parseJSON(JSONObject objResponse, String operation) {
        try {
            username = (String) objResponse.get("username");
            String token = (String) objResponse.get("token");
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit;
            String temp = sharedPreferences.getString("token", "");
            if (!sharedPreferences.contains("token") || !temp.equals(token)) {
                myEdit = sharedPreferences.edit();
                myEdit.putString("token", token);
                myEdit.putString("username", username);
                myEdit.commit();
            }

            Toast.makeText(LoginActivity.this,  operation + " Successful!!!",
                    Toast.LENGTH_LONG).show();
            hideProgressBar();
            openMainActivity();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, operation + " Failed",
                    Toast.LENGTH_LONG).show();
            hideProgressBar();
//            deleteSharedPref();
        }
    }

}