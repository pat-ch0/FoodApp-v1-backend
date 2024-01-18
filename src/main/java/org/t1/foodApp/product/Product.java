package org.t1.foodApp.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.t1.foodApp.api.OpenFoodFacts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    private List<String> composition;
    private String nutriScore;
    private double carbonFootprint;
    private List<String> allergens;
    private List<String> dietaryRestrictions;

    public static boolean isProductValid(Product product){
        if(product == null || product.barcode == null || product.barcode.isEmpty())
            return false;
        return barCodeIsValid(product.barcode);
    }
    public static boolean barCodeIsValid(String barCode){
        if(barCode == null || barCode.isEmpty())
            return false;
        var product = OpenFoodFacts.fetchProductData(barCode);
        return product != null;
    }

    public void CompositionFromJson(JSONObject productData){
        String compositionString = productData.optString("ingredients_text_en");
        if (compositionString != null && !compositionString.isEmpty()) {
            composition = Arrays.asList(compositionString.split(","));
        }
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
    public void dietaryRestrictionsJson(JSONObject productData){
        JSONArray dietaryRestrictionsJson = productData.optJSONArray("labels_tags");
        dietaryRestrictions = new ArrayList<>();
        if (dietaryRestrictionsJson != null) {
            for (int i = 0; i < dietaryRestrictionsJson.length(); i++) {
                dietaryRestrictions.add(dietaryRestrictionsJson.getString(i));
            }
        }
    }

}
