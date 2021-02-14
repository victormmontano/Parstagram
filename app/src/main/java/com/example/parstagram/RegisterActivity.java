package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {
    public static String TAG = "RegisterActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validInput())
                    return;
                createUser(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private boolean validInput() {
        boolean valid = true;
        if(etUsername.getText().toString().isEmpty()){
            etUsername.setError("Required field");
            valid = false;
        }
        if(etPassword.getText().toString().isEmpty()){
            etPassword.setError("Required field");
            valid = false;
        }

        return valid;
    }

    public void createUser(String password, String username) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.i(TAG, "success register");
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "failed to  register");
                }
            }
        });
    }
}