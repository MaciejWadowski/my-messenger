package com.example.maciej.mymessenger.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.maciej.mymessenger.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA = "USER_MAIL";

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = (EditText) findViewById(R.id.user_login);
        mPasswordField = (EditText) findViewById(R.id.user_password);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Log.d(LoginActivity.class.toString(), "Logged in succesful!");
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void startSignIn(View view) {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if(email.contains("@") && password.length() >= 6) {

            mProgressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Sign In problem", Toast.LENGTH_LONG).show();
                        mPasswordField.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Succesfully signed in", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, ApplicationActivity.class);
                        intent.putExtra(EXTRA, mEmailField.getText().toString());
                        startActivity(intent);
                    }
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
          Toast.makeText(LoginActivity.this, "Invalid email or empty fields", Toast.LENGTH_LONG).show();
        }
    }
}
