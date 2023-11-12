package com.example.applicationom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

// ...

public class crudActivity extends AppCompatActivity {

    private List<makeupEntity> productList;
    private MakeupAdapter makeupAdapter;
    private MakeupDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        productList = new ArrayList<>();

        databaseHelper = new MakeupDatabaseHelper(this);

        // Load products from the database initially
        loadProductsFromDatabase();

        makeupAdapter = new MakeupAdapter(this, productList, databaseHelper);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(makeupAdapter);

        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProductDialog();
            }
        });
    }

    private void showAddProductDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_product_form, null);
        dialogBuilder.setView(dialogView);

        final EditText editProductName = dialogView.findViewById(R.id.editProductName);
        final EditText editBrand = dialogView.findViewById(R.id.editBrand);
        final EditText editCategory = dialogView.findViewById(R.id.editCategory);
        final EditText editPrice = dialogView.findViewById(R.id.editPrice);
        Button btnAdd = dialogView.findViewById(R.id.btnAdd);

        final AlertDialog alertDialog = dialogBuilder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Generate a new ID (you should implement this logic)
                Integer id = generateNewID();

                String productName = editProductName.getText().toString();
                String brand = editBrand.getText().toString();
                String category = editCategory.getText().toString();
                double price = Double.parseDouble(editPrice.getText().toString());

                makeupEntity newProduct = new makeupEntity(id, productName, brand, category, price);
                productList.add(newProduct);
                makeupAdapter.notifyDataSetChanged();

                // Save in the database
                newProduct.saveToDatabase(databaseHelper.getWritableDatabase());

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadProductsFromDatabase() {
        productList.clear();
        productList.addAll(makeupEntity.getAllProducts(databaseHelper.getWritableDatabase()));
    }

    // ...

    // Implement your logic to generate a new ID
    private int generateNewID() {
        // This is just a placeholder, you should implement a proper logic
        return productList.size() + 1;
    }
}
