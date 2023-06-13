package com.example.book_of_recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity {
    ArrayList<Recipe> recipes;
    SQLiteDatabase db;

    int selected = 0;

    @SuppressLint("Range")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recipe);
        String id = getIntent().getStringExtra("id");
        System.out.println(id);
        // Кнопка возврата к списку блюд
        findViewById(R.id.buttonBack2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK, new Intent().putExtra("Message", "back2"));
                finish();
            }
        });


        db = new DBHelper(this).getReadableDatabase();
        String sql = "";
        sql = "SELECT * FROM first_course WHERE id = '" + id +"'\n" +
            "UNION\n" +
            "SELECT * FROM second_course WHERE id = '" + id +"'\n" +
            "UNION\n" +
            "SELECT * FROM salads WHERE id = '" + id +"'\n" +
            "UNION\n" +
            "SELECT * FROM snacks WHERE id = '" + id +"'\n" +
            "UNION\n" +
            "SELECT * FROM desserts WHERE id = '" + id +"'\n";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            recipes = new ArrayList<>();
            cursor.moveToFirst();
            do {
                byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                Recipe recipe = new Recipe(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getDouble(cursor.getColumnIndex("time_hour")),
                    cursor.getInt(cursor.getColumnIndex("portions")),
                    cursor.getString(cursor.getColumnIndex("ingridients")),
                    cursor.getString(cursor.getColumnIndex("instruction")),
                    cursor.getInt(cursor.getColumnIndex("is_selected")),
                    BitmapFactory.decodeByteArray(photo, 0, photo.length));
                recipes.add(recipe);

                ((TextView) findViewById(R.id.name)).setText(recipe.name);
                ((TextView) findViewById(R.id.time_hour)).setText("Время: " + String.valueOf(recipe.time_hour) + " ч.");
                ((TextView) findViewById(R.id.portions)).setText("Порции: " + String.valueOf(recipe.portions));
                ((TextView) findViewById(R.id.ingridients)).setText("Ингридиенты: " + recipe.ingridients);
                ((TextView) findViewById(R.id.instruction)).setText("Инструкция приготовления:  " + recipe.instruction);
                ((ImageView) findViewById(R.id.photo)).setImageBitmap(recipe.photo);

                selected = recipe.is_selected;
                if (selected == 0){
                    ((Button) findViewById(R.id.AddBtn)).setText("Добавить в избранное");
                } else {
                    ((Button) findViewById(R.id.AddBtn)).setText("Удалить из избранного");
                }

            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Данные в БД отсутствуют!", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        // Добавление в избранное
        findViewById(R.id.AddBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected == 0){
                    String sql = "UPDATE first_course SET is_selected = 1 WHERE id = " + id;
                    db.execSQL(sql);

                    sql = "UPDATE second_course SET is_selected = 1 WHERE id = " + id;
                    db.execSQL(sql);

                    sql = "UPDATE salads SET is_selected = 1 WHERE id = " + id;
                    db.execSQL(sql);

                    sql = "UPDATE snacks SET is_selected = 1 WHERE id = " + id;
                    db.execSQL(sql);

                    sql = "UPDATE desserts SET is_selected = 1 WHERE id = " + id;
                    db.execSQL(sql);

                    selected = 1;
                    ((Button) findViewById(R.id.AddBtn)).setText("Удалить из избранного");
                } else {
                    String sql = "UPDATE first_course SET is_selected = 0 WHERE id = " + id;
                    db.execSQL(sql);

                    sql = "UPDATE second_course SET is_selected = 0 WHERE id = " + id;
                    db.execSQL(sql);

                    sql = "UPDATE salads SET is_selected = 0 WHERE id = " + id;
                    db.execSQL(sql);

                    sql = "UPDATE snacks SET is_selected = 0 WHERE id = " + id;
                    db.execSQL(sql);

                    sql = "UPDATE desserts SET is_selected = 0 WHERE id = " + id;
                    db.execSQL(sql);

                    selected = 0;
                    ((Button) findViewById(R.id.AddBtn)).setText("Добавить в избранное");
                }

            }
        });

    }
}