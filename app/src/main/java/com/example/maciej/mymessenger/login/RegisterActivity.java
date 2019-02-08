package com.example.maciej.mymessenger.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.maciej.mymessenger.MainActivity;
import com.example.maciej.mymessenger.R;
import com.example.maciej.mymessenger.chat.TemporaryChat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.toString();

    private EditText mUserEmail;
    private EditText mPasswordField;
    private EditText mPasswordFieldCheck;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserEmail = (EditText) findViewById(R.id.email_field);
        mPasswordField = (EditText) findViewById(R.id.password_register);
        mPasswordFieldCheck = (EditText) findViewById(R.id.password_check_register);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_register);

        mAuth = FirebaseAuth.getInstance();
    }

    public void startSignUp(View view) {

        String email = mUserEmail.getText().toString();
        String password = mPasswordField.getText().toString();
        String passwordCheck = mPasswordFieldCheck.getText().toString();

        if(email.contains("@") && password.equals(passwordCheck) && password.length() > 6) {

            mProgressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    mProgressBar.setVisibility(View.INVISIBLE);

                    if(task.isSuccessful()) {
                        Log.d(LOG_TAG, "created user with email");
                        Toast.makeText(getApplicationContext(), "Successfully created account", Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d(LOG_TAG, "user wasn't created");
                        Toast.makeText(RegisterActivity.this, "Unsuccessfully created account", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            Toast.makeText(RegisterActivity.this, "Invalid passwords or email", Toast.LENGTH_LONG).show();
        }
    }
}
