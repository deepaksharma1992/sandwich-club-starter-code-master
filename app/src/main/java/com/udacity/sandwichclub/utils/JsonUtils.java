package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOWN = "alsoKnownAs";
    private static final String KEY_ORIGIN_PLACE = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";


    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject rootObject = new JSONObject(json);
            JSONObject name = rootObject.optJSONObject(KEY_NAME);
            String mainName = name.optString(KEY_MAIN_NAME);
            JSONArray alsoKnowsAsArray = name.optJSONArray(KEY_ALSO_KNOWN);
            List<String> alsoKnownAsList = new ArrayList<>();

            for (int i = 0; i < alsoKnowsAsArray.length(); i++)
                alsoKnownAsList.add(alsoKnowsAsArray.optString(i));

            String placeOfOrigin = rootObject.optString(KEY_ORIGIN_PLACE);
            String description = rootObject.optString(KEY_DESCRIPTION);
            String imageUrl = rootObject.optString(KEY_IMAGE);
            JSONArray ingredientsArray = rootObject.optJSONArray(KEY_INGREDIENTS);
            List<String> ingredientsList = new ArrayList<>();

            for (int i = 0; i < ingredientsArray.length(); i++)
                ingredientsList.add(ingredientsArray.optString(i));

            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, imageUrl, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
