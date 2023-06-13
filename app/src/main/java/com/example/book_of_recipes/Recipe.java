package com.example.book_of_recipes;
import android.graphics.Bitmap;

public class Recipe {
    int id;
    String name;
    double time_hour;
    int portions;
    String ingridients;
    String instruction;
    int is_selected;
    Bitmap photo;

    public Recipe(int id, String name, double time_hour, int portions, String ingridients, String instruction, int is_selected, Bitmap photo) {
        this.id = id;
        this.name = name;
        this.time_hour = time_hour;
        this.portions = portions;
        this.ingridients = ingridients;
        this.instruction = instruction;
        this.is_selected = is_selected;
        this.photo = photo;

    }
}
