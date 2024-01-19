package org.t1.foodApp.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.t1.foodApp.api.OpenFoodFacts;

import java.util.ArrayList;
import java.util.List;

import static org.t1.foodApp.Utils.JsonUtil.getLocalizedValue;


@Getter
@Setter
public class Product {

    @NotBlank(message = "Barcode cannot be blank")
    @NotNull(message = "Barcode cannot be null")
    private String barcode;
    @Min(value = 0, message = "The value must be positive")
    private int stock;
    @NotBlank(message = "storageId cannot be blank")
    @NotNull(message = "storageId cannot be null")
    private String storageId;
    private String name;
    private String imageSrc;

    private double price;
    private Composition composition;
    private String nutriScore;
    private double carbonFootprint;
    private List<String> allergens;
    private List<String> dietaryRestrictions;
    private  boolean isVegan = false;
    private  boolean isVegetarian = false;

    public boolean isProductValid(){
        if( this.barcode == null || this.barcode.isEmpty())
            return true;
        return !barCodeIsValid(this.barcode);
    }
    public static boolean barCodeIsValid(String barCode){
        if(barCode == null || barCode.isEmpty())
            return false;
        var product = OpenFoodFacts.fetchProductData(barCode);
        return product != null;
    }

    public void CompositionFromJson(JSONObject productData) {
        JSONArray ingredientsJson = productData.optJSONArray("ingredients");
        if (ingredientsJson == null)
            return;
        this.composition = new Composition();
        for (int i = 0; i < ingredientsJson.length(); i++) {
            JSONObject ingredientJson = ingredientsJson.getJSONObject(i);
            Ingredient ingredient = new Ingredient(ingredientJson);
            this.composition.addIngredient(ingredient);
        }
        this.isVegan = this.composition.isVegan();
        this.isVegetarian = this.composition.isVegetarian();
    }

    public void AllergensFromJson(JSONObject productData){
        JSONArray allergensJson = productData.optJSONArray("allergens_hierarchy");
        allergens = new ArrayList<>();
        if (allergensJson != null) {
            for (int i = 0; i < allergensJson.length(); i++) {
                allergens.add(allergensJson.getString(i));
            }
        }
    }

    public void getImageSrcFromJson(JSONObject productData) {
        String[] imagePaths = new String[]{
                "image_front_url",
                "selected_images.front.display.{lang}",
                "selected_images.ingredients.display.{lang}",
        };
        for (String path : imagePaths) {
            String imageUrl = getLocalizedValue(productData, path);
            if (imageUrl.startsWith("http")) {
                this.imageSrc = imageUrl;
                return;
            }
        }
    }


}
