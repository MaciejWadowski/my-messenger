package com.example.maciej.mymessenger.chat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.example.maciej.mymessenger.R;
import com.example.maciej.mymessenger.user.User;
import com.google.firebase.database.*;

public class TemporaryChat extends AppCompatActivity {

    private static final String LOG_TAG = TemporaryChat.class.toString();

    private DatabaseReference mDatabase;
    private EditText mMessageField;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_chat);

        mMessageField = (EditText) findViewById(R.id.message_text);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final TextView chatMessages = findViewById(R.id.chat_text);

        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        user = new User(userEmail);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    chatMessages.append(user.getName()  + ": " + dataSnapshot.getValue().toString() + '\n');
                    Log.d(LOG_TAG,  "Value " + dataSnapshot.getValue().toString() + " sent");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                chatMessages.setText(R.string.error_db);
                Log.e(LOG_TAG, "Sending a message failed");
            }
        });
    }

    public void sendMessage(View view) {
        mDatabase.setValue(mMessageField.getText().toString());
        mMessageField.setText("");
    }
}
