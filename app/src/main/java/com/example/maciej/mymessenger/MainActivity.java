package com.example.maciej.mymessenger;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.toString();

    private DatabaseReference mDatabase;
    private EditText mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessage = findViewById(R.id.user_message);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final TextView chatMessages = findViewById(R.id.chat);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    return;
                }
                chatMessages.append(dataSnapshot.getValue().toString() + "\n");
                Log.d(LOG_TAG, "Value " + dataSnapshot.getValue().toString() + " send");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                chatMessages.setText(R.string.error_db);
                Log.e(LOG_TAG, "Sending a message failed");
            }
        });
    }


    public void sendMessage(View view) {
        mDatabase.setValue(mMessage.getText().toString());
        mMessage.setText("");
    }
}
