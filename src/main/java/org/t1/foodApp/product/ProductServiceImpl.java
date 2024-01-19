package org.t1.foodApp.product;


import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.t1.foodApp.api.OpenFoodFacts;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl {
    public ProductDetail getProductById(String id) {
        var product = OpenFoodFacts.fetchProductData(id);
        if (product == null)
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Product not found");
        return product;
    }


}
