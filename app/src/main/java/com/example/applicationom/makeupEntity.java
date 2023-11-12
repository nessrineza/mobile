package com.example.applicationom;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class makeupEntity implements BaseColumns {

    private long id;
    private String productName;
    private String brand;
    private String category;
    private double price;

    public makeupEntity() {
        // Constructeur par défaut nécessaire pour l'instanciation
    }

    public makeupEntity(long id, String productName, String brand, String category, double price) {
        this.id = id;
        this.productName = productName;
        this.brand = brand;
        this.category = category;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long saveToDatabase(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(MakeupDatabaseHelper.COLUMN_PRODUCT_NAME, productName);
        values.put(MakeupDatabaseHelper.COLUMN_BRAND, brand);
        values.put(MakeupDatabaseHelper.COLUMN_CATEGORY, category);
        values.put(MakeupDatabaseHelper.COLUMN_PRICE, price);

        if (id != 0) {
            values.put(MakeupDatabaseHelper.COLUMN_ID, id);
            return db.replace(MakeupDatabaseHelper.TABLE_PRODUCTS, null, values);
        } else {
            return db.insert(MakeupDatabaseHelper.TABLE_PRODUCTS, null, values);
        }
    }

    public static List<makeupEntity> getAllProducts(SQLiteDatabase db) {
        List<makeupEntity> productList = new ArrayList<>();

        String[] projection = {
                MakeupDatabaseHelper.COLUMN_ID,
                MakeupDatabaseHelper.COLUMN_PRODUCT_NAME,
                MakeupDatabaseHelper.COLUMN_BRAND,
                MakeupDatabaseHelper.COLUMN_CATEGORY,
                MakeupDatabaseHelper.COLUMN_PRICE
        };

        Cursor cursor = db.query(
                MakeupDatabaseHelper.TABLE_PRODUCTS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            makeupEntity product = cursorToMakeupProduct(cursor);
            productList.add(product);
        }

        cursor.close();

        return productList;
    }
    public void deleteProduct(SQLiteDatabase db) {
        String selection = MakeupDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        db.delete(MakeupDatabaseHelper.TABLE_PRODUCTS, selection, selectionArgs);
    }


    public static makeupEntity cursorToMakeupProduct(Cursor cursor) {
        makeupEntity product = new makeupEntity();
        product.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MakeupDatabaseHelper.COLUMN_ID)));
        product.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(MakeupDatabaseHelper.COLUMN_PRODUCT_NAME)));
        product.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(MakeupDatabaseHelper.COLUMN_BRAND)));
        product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(MakeupDatabaseHelper.COLUMN_CATEGORY)));
        product.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(MakeupDatabaseHelper.COLUMN_PRICE)));

        return product;
    }
    public void updateProduct(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(MakeupDatabaseHelper.COLUMN_PRODUCT_NAME, productName);
        values.put(MakeupDatabaseHelper.COLUMN_BRAND, brand);
        values.put(MakeupDatabaseHelper.COLUMN_CATEGORY, category);
        values.put(MakeupDatabaseHelper.COLUMN_PRICE, price);

        String selection = MakeupDatabaseHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        db.update(MakeupDatabaseHelper.TABLE_PRODUCTS, values, selection, selectionArgs);
    }

}
