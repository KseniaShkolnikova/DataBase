package com.example.db;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private Context context;
    private ArrayList<books> booksArrayList;
    public RecyclerViewAdapter(Context context,ArrayList<books> booksArrayList){
        this.context=context;
        this.booksArrayList = booksArrayList;

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_book_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        books book = booksArrayList.get(position);
        holder.bookname.setText(book.getName());
        holder.bookAuthor.setText(book.getAuthor());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailBookActivity.class);
            intent.putExtra("bookId", book.getId());
            context.startActivity(intent);
        });
            holder.editButton.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditBookActivity.class);
                intent.putExtra("bookId", book.getId());
                intent.putExtra("bookName", book.getName());
                intent.putExtra("bookAuthor", book.getAuthor());
                context.startActivity(intent);
            });
    }

    @Override
    public int getItemCount() {
        return booksArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookname;
        TextView bookAuthor;
        Button editButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookname = itemView.findViewById(R.id.b_name);
            bookAuthor = itemView.findViewById(R.id.b_author);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }


}
