package com.example.db;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailBookActivity extends AppCompatActivity{
    private dbHelper dbHelper;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        dbHelper = new dbHelper(this);
        Button button = findViewById(R.id.exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailBookActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("bookId")) {
            bookId = intent.getIntExtra("bookId", -1);
            if (bookId != -1) {
                loadBookDetails();
            } else {
                Toast.makeText(this, "Error: Book ID not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Error: Invalid Intent", Toast.LENGTH_SHORT).show();
            finish();
        }

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> deleteBook());
    }

    private void loadBookDetails() {
        Cursor cursor = dbHelper.getBookById(bookId);
        if (cursor != null && cursor.moveToFirst()) {
            TextView nameTextView = findViewById(R.id.detail_book_name);
            TextView authorTextView = findViewById(R.id.detail_book_author);
            nameTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_NAME)));
            authorTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COLUMN_AUTHOR)));
            cursor.close();
        } else {
            Toast.makeText(this, "Не получилось загрузить(", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void deleteBook() {
        int result = dbHelper.deleteBook(bookId);
        if (result > 0) {
            Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Ошибка при удалении книги", Toast.LENGTH_SHORT).show();
        }
    }
}
