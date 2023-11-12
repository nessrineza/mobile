package com.example.applicationom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class MakeupAdapter extends ArrayAdapter<makeupEntity> {

    private List<makeupEntity> productList;
    private MakeupDatabaseHelper databaseHelper;

    private AlertDialog alertDialog; // Declare the AlertDialog

    public MakeupAdapter(Context context, List<makeupEntity> products, MakeupDatabaseHelper dbHelper) {
        super(context, 0, products);
        this.productList = products;
        this.databaseHelper = dbHelper;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.makeup_product_item, parent, false);
        }

        makeupEntity product = getItem(position);

        TextView productNameTextView = convertView.findViewById(R.id.txtProductName);
        TextView brandTextView = convertView.findViewById(R.id.txtBrand);
        TextView categoryTextView = convertView.findViewById(R.id.txtCategory);
        TextView priceTextView = convertView.findViewById(R.id.txtPrice);

        if (product != null) {
            productNameTextView.setText(product.getProductName());
            brandTextView.setText(product.getBrand());
            categoryTextView.setText(product.getCategory());
            priceTextView.setText(String.valueOf(product.getPrice()));
        }

        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < productList.size()) {
                    // Your deletion logic...
                    makeupEntity productToDelete = getItem(position);
                    if (productToDelete != null) {
                        productToDelete.deleteProduct(databaseHelper.getWritableDatabase());
                    }

                    // Remove the item from the list and notify the adapter
                    deleteProduct(position);
                }
            }
        });

        Button btnUpdate = convertView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateProductDialog(product);
            }
        });

        return convertView;
    }

    // Ajoutez cette méthode pour supprimer un produit de la liste et de la base de données
    public void deleteProduct(int position) {
        // Supprimer de la liste
        productList.remove(position);
        notifyDataSetChanged();

        // Supprimer de la base de données
        makeupEntity productToDelete = getItem(position);
        if (productToDelete != null) {
            productToDelete.deleteProduct(databaseHelper.getWritableDatabase());
        }
    }

    private void showUpdateProductDialog(makeupEntity product) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.update_product_form, null);
        dialogBuilder.setView(dialogView);

        EditText editProductName = dialogView.findViewById(R.id.editUpdatedName);
        EditText editBrand = dialogView.findViewById(R.id.editUpdatedBrand);
        EditText editCategory = dialogView.findViewById(R.id.editUpdatedCategory);
        EditText editPrice = dialogView.findViewById(R.id.editUpdatedPrice);
        Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        editProductName.setText(product.getProductName());
        editBrand.setText(product.getBrand());
        editCategory.setText(product.getCategory());
        editPrice.setText(String.valueOf(product.getPrice()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update product information
                product.setProductName(editProductName.getText().toString());
                product.setBrand(editBrand.getText().toString());
                product.setCategory(editCategory.getText().toString());
                product.setPrice(Double.parseDouble(editPrice.getText().toString()));

                // Notify the adapter that the data has changed
                notifyDataSetChanged();

                // Update the product in the database
                product.saveToDatabase(databaseHelper.getWritableDatabase());

                // Dismiss the dialog
                alertDialog.dismiss();
            }
        });

        alertDialog = dialogBuilder.create(); // Assign the created dialog to the alertDialog
        alertDialog.show();
    }
}
