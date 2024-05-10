package com.example.projecto;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private int i = 1;
    private final ArrayList<User> userList;
    public UserAdapter(ArrayList<User> userList) {
        this.userList = userList;
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_position;
        public TextView txt_user_username;
        public TextView txt_user_points;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_position = itemView.findViewById(R.id.txt_position);
            txt_user_username = itemView.findViewById(R.id.txt_user_username);
            txt_user_points = itemView.findViewById(R.id.txt_user_points);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Get Layout inflater from Context
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        //Inflate Layout
        View userView = layoutInflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(userView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder viewHolder, int position) {
        //Get the data model based on position
        User user = userList.get(position);
        TextView textView = viewHolder.txt_position;
        textView.setText("" + i);
        i++;
        //Set username
        TextView textView1 = viewHolder.txt_user_username;
        textView1.setText(user.getUsername());
        //Set points
        TextView textView2 = viewHolder.txt_user_points;
        textView2.setText("" + user.getPoints());

    }
}
