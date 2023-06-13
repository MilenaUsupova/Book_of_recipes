package com.example.book_of_recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    public RecipeAdapter(@NonNull Context context, ArrayList<Recipe> recipes) {
        super(context, R.layout.layout_recipe_item, recipes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_recipe_item, null);
        }

        Recipe recipe = getItem(position);

        ((TextView) convertView.findViewById(R.id.name)).setText(recipe.name);
        ((TextView) convertView.findViewById(R.id.time_hour)).setText("Время: " + String.valueOf(recipe.time_hour) + " ч.");
        ((TextView) convertView.findViewById(R.id.portions)).setText("Порции: " + String.valueOf(recipe.portions));
        ((ImageView) convertView.findViewById(R.id.photo)).setImageBitmap(recipe.photo);

        return convertView;
    }
}
