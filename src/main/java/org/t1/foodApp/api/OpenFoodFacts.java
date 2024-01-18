package org.t1.foodApp.api;

import org.json.JSONObject;
import org.json.JSONArray;
import org.t1.foodApp.product.Product;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OpenFoodFacts {

    public static Product fetchProductData(String barCode) {
        try {
            URL url = new URL("https://world.openfoodfacts.org/api/v2/product/" + barCode + ".json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(content.toString());
                if (jsonResponse.getInt("status") == 1) {
                    JSONObject productData = jsonResponse.getJSONObject("product");
                    Product product = new Product();
                    product.setBarcode(barCode);
                    product.setName(productData.optString("product_name"));
                    product.setImageSrc(productData.optString("image_front_url"));
                    product.setStock(0);
                    product.setPrice(0.0);
                    product.CompositionFromJson(productData);
                    product.setNutriScore(productData.optString("nutriscore_grade"));
                    product.setCarbonFootprint(0.0);
                    product.AllergensFromJson(productData);
                    product.dietaryRestrictionsJson(productData);
                    return product;

                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}
