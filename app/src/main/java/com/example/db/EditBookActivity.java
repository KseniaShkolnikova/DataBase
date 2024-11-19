package com.example.db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditBookActivity extends AppCompatActivity {

    private EditText editTextName, editTextAuthor;
    private Button updateButton;
    private dbHelper db;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);

        Button button = findViewById(R.id.exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBookActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        editTextName = findViewById(R.id.editTextName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        updateButton = findViewById(R.id.updateButton);
        db = new dbHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            bookId = intent.getIntExtra("bookId", -1);
            editTextName.setText(intent.getStringExtra("bookAuthor"));
            editTextAuthor.setText(intent.getStringExtra("bookName"));
        }

        updateButton.setOnClickListener(v -> updateBook());
    }


    private void updateBook() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()) {
            Toast.makeText(this, "Заполняйте все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int result = db.updateBook(bookId, bookName, bookAuthor);
        if (result > 0) {
            Toast.makeText(this, "Обновили!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditBookActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Не получилось", Toast.LENGTH_SHORT).show();
        }
    }
}