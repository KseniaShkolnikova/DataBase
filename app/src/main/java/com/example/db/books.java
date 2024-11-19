package com.example.db;

public class books {
    private int id;
    private String name;
    private String author;

    public books(int id,String name, String author){
        this.id = id;
        this.name = name;
        this.author = author;
    }
    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
