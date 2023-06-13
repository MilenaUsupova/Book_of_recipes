package com.example.book_of_recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.f_course_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Message", "Первые блюда");
                startActivity(intent);
            }
        });

/*
        findViewById(R.id.f_course_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                //startActivityForResult(intent, 2);
                startActivity(intent);
            }
        });

 */

        findViewById(R.id.s_course_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Message", "Вторые блюда");
                startActivity(intent);
            }
        });

        findViewById(R.id.salads_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Message", "Салаты");
                startActivity(intent);
            }
        });

        findViewById(R.id.snacks_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Message", "Закуски");
                startActivity(intent);
            }
        });

        findViewById(R.id.des_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("Message", "Десерты");
                startActivity(intent);
            }
        });
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case 2: case 3: ((TextView) findViewById(R.id.textViewR)).setText("Экран №" + requestCode + ": "
                    + data.getStringExtra("Message")); break;
                case 1: if (null != data) {
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ((TextView) findViewById(R.id.textViewR)).setText(text.get(0));
                }
            }
        }
    }

     */
}