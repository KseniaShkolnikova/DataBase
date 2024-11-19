package com.example.db;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private dbHelper dbHelper;
    private ArrayList<books> booksArrayList;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new dbHelper(this);
        booksArrayList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.list_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerViewAdapter(this, booksArrayList);
        recyclerView.setAdapter(adapter);

        Button fab = findViewById(R.id.perehodBtm);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadBooks(){
        booksArrayList.clear();
        Cursor cursor = dbHelper.getAllBooks();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_NAME));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_AUTHOR));
                booksArrayList.add(new books(id,author,name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadBooks();
    }
}