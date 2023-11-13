package com.example.gestionuser;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserItemListActivity extends AppCompatActivity implements UserAdapter.OnDeleteClickListener {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private DatabaseHelper dbHelper;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_item_list);

        recyclerView = findViewById(R.id.recyclerView);
        dbHelper = new DatabaseHelper(this);

        // Fetch user data from the database
        userList = dbHelper.getAllUsers();

        if (userList.isEmpty()) {
            Toast.makeText(this, "No users found.", Toast.LENGTH_SHORT).show();
        } else {
            // Initialize and set up the RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            userAdapter = new UserAdapter(userList, this);
            recyclerView.setAdapter(userAdapter);
        }
    }

    @Override
    public void onDeleteClick(int userId) {
        // Delete the user from the database
        dbHelper.deleteUserById(userId);

        // Fetch the updated user list
        userList = dbHelper.getAllUsers();

        // Update the adapter with the new list
        userAdapter.setUserList(userList);
        userAdapter.notifyDataSetChanged();
    }
}

