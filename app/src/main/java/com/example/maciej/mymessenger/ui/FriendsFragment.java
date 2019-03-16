package com.example.maciej.mymessenger.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.maciej.mymessenger.R;
import com.example.maciej.mymessenger.user.User;
import com.example.maciej.mymessenger.utils.FriendsRecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private EditText mFriendToAddText;
    private Button mAddFriendButton;
    private FirebaseAuth mAuth;
    private FriendsRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private User user;

    public FriendsFragment() {
        // Required empty public constructor
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friends, container, false);

        mFriendToAddText = (EditText) view.findViewById(R.id.add_friend_text);
        mAddFriendButton = (Button) view.findViewById(R.id.add_friend_button);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.friend_list);
        mAdapter = new FriendsRecyclerViewAdapter(user);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAuth = FirebaseAuth.getInstance();

        mAddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mFriendToAddText.getText().toString();
                mFriendToAddText.setText("");

                mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        if (task.getResult().getSignInMethods() != null) {

                            boolean check = !task.getResult().getSignInMethods().isEmpty();

                            if (check) {
                                Toast.makeText(getContext(),"User found", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(),"User not found", Toast.LENGTH_LONG). show();
                            }
                        } else {
                            Toast.makeText(getContext(),"User not found", Toast.LENGTH_LONG). show();
                        }
                    }
                });
            }
        });
        return view;
    }
}