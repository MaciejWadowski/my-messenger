package com.example.maciej.mymessenger.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.maciej.mymessenger.R;
import com.example.maciej.mymessenger.user.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FriendsRecyclerViewAdapter extends RecyclerView.Adapter<FriendsRecyclerViewAdapter.MyViewHolder> {

    private DatabaseReference mDatabase;
    private User user;
    private String[] dummyData = {
            "smth",":))", "12", "123", "123123", "12312312"
    };

    public FriendsRecyclerViewAdapter(User user) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Users/" + user.getUserId() + "/friends");
        this.user = user;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view =  LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.friend_list_element, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String str = dummyData[i];
        myViewHolder.mTextView.setText(str);
    }

    @Override
    public int getItemCount() {
        return dummyData.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(@NonNull View view) {
            super(view);
            mTextView = view.findViewById(R.id.recycler_view_friend_element);
        }
    }
}
