package org.t1.foodApp.storage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.t1.foodApp.product.Product;

import java.util.ArrayList;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
public class Storage {

    private String id;

    @NotNull(message = "Label cannot be null")
    @NotBlank(message = "Label cannot be blank")
    private String label;

    private ArrayList<Product> products;

    public void addProduct(Product product){
        if (product == null || product.getStock() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be added");
        if(product.isProductValid())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product is not valid");
        products.add(product);
    }

    public void removeProduct(Product product){
        if (product == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be removed");
        products.remove(product);
    }

    public void updateProduct(Product updatedProduct){
        if (updatedProduct == null || updatedProduct.getStock() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product cannot be updated");
        }
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.getBarcode().equals(updatedProduct.getBarcode()))
                .findFirst();
    if (productOptional.isEmpty())
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    productOptional.ifPresent(product -> {
        int index = products.indexOf(product);
        products.set(index, updatedProduct);
    });
    }

    public void removeProductBarcode(String barcode) {
Optional<Product> productOptional = products.stream()
                .filter(product -> product.getBarcode().equals(barcode))
                .findFirst();
        if (productOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        productOptional.ifPresent(product -> {
            products.remove(product);
        });
    }
}
