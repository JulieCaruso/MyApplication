package com.example.jcaruso.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.jcaruso.myapplication.movie.MovieActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.main_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });
    }

    private void signin() {
        EditText usernameEditText = (EditText) findViewById(R.id.main_username);
        EditText passwordEditText = (EditText) findViewById(R.id.main_password);

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean valid = true;

        if (username.isEmpty()) {
            valid = false;
            usernameEditText.setError(getString(R.string.error_empty));
        }

        if (password.isEmpty()) {
            valid = false;
            passwordEditText.setError(getString(R.string.error_empty));
        }

        if (valid) {
            startActivity(new Intent(MainActivity.this, MovieActivity.class));
        }
    }

}
