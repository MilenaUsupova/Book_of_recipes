package com.example.book_of_recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {

    ArrayList<Recipe> recipes;
    SQLiteDatabase db;
    RecipeAdapter recipeAdapter;
    ListView recipeList;
    EditText text_search;
    int in_selected = 0;
    int no_recipes = 0;
    String button_name = "";

    @SuppressLint("Range")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recipes_list);

        button_name = getIntent().getStringExtra("Message");
        //Toast.makeText(MainActivity2.this, button_num, Toast.LENGTH_SHORT).show(); // какая кнопка была нажата


        db = new DBHelper(this).getReadableDatabase();
        String sql = "";
        if (Objects.equals(button_name, "Первые блюда")) {
            sql = "SELECT * FROM first_course";
        } else if (Objects.equals(button_name, "Вторые блюда")) {
            sql = "SELECT * FROM second_course";
        }
        else if (Objects.equals(button_name, "Салаты")) {
            sql = "SELECT * FROM salads";
        }
        else if (Objects.equals(button_name, "Закуски")) {
            sql = "SELECT * FROM snacks";
        }
        else if (Objects.equals(button_name, "Десерты")) {
            sql = "SELECT * FROM desserts";
        }
        else sql = "";

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
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Данные в БД отсутствуют!", Toast.LENGTH_SHORT).show();
        }
        cursor.close();


        recipeAdapter = new RecipeAdapter(this, recipes);
        recipeList = findViewById(R.id.recipeList);
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                intent.putExtra("id", Integer.toString(recipes.get(position).id));

                startActivity(intent);
            }
        });
        recipeList.setAdapter(recipeAdapter);


        // Кнопка возврата к категориям блюд
        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(in_selected == 0){
                    setResult(RESULT_OK, new Intent().putExtra("Message", "back"));
                    finish();
                }
                else {
                    if(no_recipes == 0){
                        String sql = "";
                        if (Objects.equals(button_name, "Первые блюда")) {
                            sql = "SELECT * FROM first_course";
                        } else if (Objects.equals(button_name, "Вторые блюда")) {
                            sql = "SELECT * FROM second_course";
                        }
                        else if (Objects.equals(button_name, "Салаты")) {
                            sql = "SELECT * FROM salads";
                        }
                        else if (Objects.equals(button_name, "Закуски")) {
                            sql = "SELECT * FROM snacks";
                        }
                        else if (Objects.equals(button_name, "Десерты")) {
                            sql = "SELECT * FROM desserts";
                        }
                        else sql = "";
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
                            } while (cursor.moveToNext());
                            recipeAdapter = new RecipeAdapter(getApplicationContext(), recipes);
                            recipeList.setAdapter(recipeAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "Данные в БД отсутствуют!", Toast.LENGTH_SHORT).show();
                        }
                        cursor.close();
                        in_selected = 0;
                        ((Button) findViewById(R.id.buttonSelect)).setEnabled(true);
                    }
                }
            }
        });

        findViewById(R.id.buttonSortA).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "";
                if (Objects.equals(button_name, "Первые блюда")) {
                    sql = "SELECT * FROM first_course ORDER BY name";
                } else if (Objects.equals(button_name, "Вторые блюда")) {
                    sql = "SELECT * FROM second_course ORDER BY name";
                }
                else if (Objects.equals(button_name, "Салаты")) {
                    sql = "SELECT * FROM salads ORDER BY name";
                }
                else if (Objects.equals(button_name, "Закуски")) {
                    sql = "SELECT * FROM snacks ORDER BY name";
                }
                else if (Objects.equals(button_name, "Десерты")) {
                    sql = "SELECT * FROM desserts ORDER BY name";
                }
                else sql = "";
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
                    } while (cursor.moveToNext());
                    recipeAdapter = new RecipeAdapter(getApplicationContext(), recipes);
                    recipeList.setAdapter(recipeAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Данные в БД отсутствуют!", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });


        // Поиск рецепта
        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_search = (EditText) findViewById(R.id.text_search);
                String txt = text_search.getText().toString();
                String sql = "";

                if (Objects.equals(button_name, "Первые блюда")) {
                    // поиск в таблице только с первыми блюдами
                    sql = "SELECT * FROM first_course WHERE name LIKE '%" + txt + "%'";
                } else if (Objects.equals(button_name, "Вторые блюда")) {
                    sql = "SELECT * FROM second_course WHERE name LIKE '%" + txt + "%'";
                }
                else if (Objects.equals(button_name, "Салаты")) {
                    sql = "SELECT * FROM salads WHERE name LIKE '%" + txt + "%'";
                }
                else if (Objects.equals(button_name, "Закуски")) {
                    sql = "SELECT * FROM snacks WHERE name LIKE '%" + txt + "%'";
                }
                else if (Objects.equals(button_name, "Десерты")) {
                    sql = "SELECT * FROM desserts WHERE name LIKE '%" + txt + "%'";
                }
                else sql = "";

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
                    } while (cursor.moveToNext());
                    recipeAdapter = new RecipeAdapter(getApplicationContext(), recipes);
                    recipeList.setAdapter(recipeAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Данные в БД отсутствуют!", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });


        // Избранное (отображение)
        findViewById(R.id.buttonSelect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "SELECT * FROM first_course WHERE is_selected = 1 \n" +
                    "UNION \n" +
                    "SELECT * FROM second_course WHERE is_selected = 1 \n" +
                    "UNION \n" +
                    "SELECT * FROM salads WHERE is_selected = 1 \n" +
                    "UNION \n" +
                    "SELECT * FROM snacks WHERE is_selected = 1 \n" +
                    "UNION \n" +
                    "SELECT * FROM desserts WHERE is_selected = 1 \n";
                Cursor cursor = db.rawQuery(sql, null);
                if (cursor.getCount() > 0) {
                    in_selected = 1;
                    ((Button) findViewById(R.id.buttonSelect)).setEnabled(false);
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
                    } while (cursor.moveToNext());
                    recipeAdapter = new RecipeAdapter(getApplicationContext(), recipes);
                    recipeList.setAdapter(recipeAdapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Данные в БД отсутствуют!", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String sql = "SELECT * FROM first_course WHERE is_selected = 1 \n" +
                "UNION \n" +
                "SELECT * FROM second_course WHERE is_selected = 1 \n" +
                "UNION \n" +
                "SELECT * FROM salads WHERE is_selected = 1 \n" +
                "UNION \n" +
                "SELECT * FROM snacks WHERE is_selected = 1 \n" +
                "UNION \n" +
                "SELECT * FROM desserts WHERE is_selected = 1 \n";
        Cursor cursor = db.rawQuery(sql, null);
        if(in_selected == 1 && cursor.getCount() > 0) {
            recipes = new ArrayList<>();
            cursor.moveToFirst();
            do {
                @SuppressLint("Range")
                byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                @SuppressLint("Range")
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
            } while (cursor.moveToNext());
            recipeAdapter = new RecipeAdapter(getApplicationContext(), recipes);
            recipeList.setAdapter(recipeAdapter);
        } else {
            if (Objects.equals(button_name, "Первые блюда")) {
                sql = "SELECT * FROM first_course";
            } else if (Objects.equals(button_name, "Вторые блюда")) {
                sql = "SELECT * FROM second_course";
            } else if (Objects.equals(button_name, "Салаты")) {
                sql = "SELECT * FROM salads";
            } else if (Objects.equals(button_name, "Закуски")) {
                sql = "SELECT * FROM snacks";
            } else if (Objects.equals(button_name, "Десерты")) {
                sql = "SELECT * FROM desserts";
            } else sql = "";
            cursor = db.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                recipes = new ArrayList<>();
                cursor.moveToFirst();
                do {
                    @SuppressLint("Range") byte[] photo = cursor.getBlob(cursor.getColumnIndex("photo"));
                    @SuppressLint("Range") Recipe recipe = new Recipe(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getDouble(cursor.getColumnIndex("time_hour")),
                            cursor.getInt(cursor.getColumnIndex("portions")),
                            cursor.getString(cursor.getColumnIndex("ingridients")),
                            cursor.getString(cursor.getColumnIndex("instruction")),
                            cursor.getInt(cursor.getColumnIndex("is_selected")),
                            BitmapFactory.decodeByteArray(photo, 0, photo.length));
                    recipes.add(recipe);
                } while (cursor.moveToNext());
                recipeAdapter = new RecipeAdapter(getApplicationContext(), recipes);
                recipeList.setAdapter(recipeAdapter);
                in_selected = 0;
                ((Button) findViewById(R.id.buttonSelect)).setEnabled(true);
            }
        }
        cursor.close();
    }
}