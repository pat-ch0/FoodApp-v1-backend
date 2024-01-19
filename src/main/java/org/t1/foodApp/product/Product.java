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
    protected String barcode;
    @Min(value = 0, message = "The value must be positive")
    protected int stock;
    @NotBlank(message = "storageId cannot be blank")
    @NotNull(message = "storageId cannot be null")
    protected String storageId;

    public boolean isProductValid(){
        if( this.barcode == null || this.barcode.isEmpty())
            return false;
        return !barCodeIsValid(this.barcode);
    }
    public static boolean barCodeIsValid(String barCode){
        if(barCode == null || barCode.isEmpty())
            return false;
        var product = OpenFoodFacts.fetchProductData(barCode);
        return product != null;
    }
}
