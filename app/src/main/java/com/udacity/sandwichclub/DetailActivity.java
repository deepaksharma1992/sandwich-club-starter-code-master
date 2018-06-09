package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAsText = findViewById(R.id.tv_also_known_as);
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        StringBuffer alsoKnownAsSb = new StringBuffer();
        for (int i = 0; i < alsoKnownAsList.size(); i++) {
            if (i == alsoKnownAsList.size() - 1)
                alsoKnownAsSb.append(alsoKnownAsList.get(i));
            else
                alsoKnownAsSb.append(alsoKnownAsList.get(i) + ", ");
        }
        alsoKnownAsText.setText(alsoKnownAsSb.toString());

        TextView ingredientsText = findViewById(R.id.tv_ingredients);
        List<String> ingredientsList = sandwich.getIngredients();
        StringBuffer ingredientsSb = new StringBuffer();
        for (int i = 0; i < ingredientsList.size(); i++) {
            if (i == ingredientsList.size() - 1)
                ingredientsSb.append(ingredientsList.get(i));
            else
                ingredientsSb.append(ingredientsList.get(i));
        }
        ingredientsText.setText(ingredientsSb.toString());

        TextView placeOfOriginText = findViewById(R.id.tv_origin);
        if (!TextUtils.isEmpty(sandwich.getPlaceOfOrigin()))
            placeOfOriginText.setText(sandwich.getPlaceOfOrigin());

        TextView descriptionText = findViewById(R.id.tv_description);
        descriptionText.setText(sandwich.getDescription());

    }
}
