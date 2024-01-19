package org.t1.foodApp.product;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;


@Getter
@Setter
public class Ingredient {

    public Ingredient(JSONObject ingredientJson) {
        this.setId(ingredientJson.optString("id"));
        this.setPercent(ingredientJson.optDouble("percent"));
        this.setPercentEstimate(ingredientJson.optDouble("percent_estimate"));
        this.setPercentMax(ingredientJson.optDouble("percent_max"));
        this.setPercentMin(ingredientJson.optDouble("percent_min"));
        this.setText(ingredientJson.optString("text"));

        String veganStatus = ingredientJson.optString("vegan");
        this.setVegan("yes".equals(veganStatus));

        String vegetarianStatus = ingredientJson.optString("vegetarian");
        this.setVegetarian("yes".equals(vegetarianStatus));
    }

    private String id;
    private double percent;
    private double percentEstimate;
    private double percentMax;
    private double percentMin;
    private String text;
    private boolean vegan;
    private boolean vegetarian;
}
