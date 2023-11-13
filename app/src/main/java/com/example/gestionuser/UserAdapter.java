package com.example.gestionuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> userList;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int userId);
    }
    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    public UserAdapter(List<User> userList, OnDeleteClickListener onDeleteClickListener) {
        this.userList = userList;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.user_item, parent, false);
        return new ViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);

        holder.idTextView.setText("ID: " + user.getId());
        holder.usernameTextView.setText("Username: " + user.getUsername());
        holder.emailTextView.setText("Email: " + user.getEmail());
        holder.userTypeTextView.setText("User Type: " + user.getUserType());

        holder.deleteButton.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(user.getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView;
        public TextView usernameTextView;
        public TextView emailTextView;
        public TextView userTypeTextView;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            userTypeTextView = itemView.findViewById(R.id.userTypeTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
