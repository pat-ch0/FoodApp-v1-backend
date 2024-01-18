package org.t1.foodApp.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.t1.foodApp.annotation.UserCookie;
import org.t1.foodApp.storage.StorageServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private StorageServiceImpl storageService;
    @GetMapping("/{barCode}")
    public Product getProduct(@PathVariable String barCode) {
        return productService.getProductById(barCode);
    }

    @PutMapping
    public void add(@UserCookie String userCookie, @Validated @RequestBody Product product) {
        storageService.addProductStorage(userCookie, product);
    }

    @PostMapping
    public void update(@UserCookie String userCookie,
                       @Validated @RequestBody Product product) {
        storageService.updateProductStorage(userCookie, product);
    }

    @DeleteMapping
    public void delete(@UserCookie String userCookie,
                       @Validated @RequestBody DeleteProduct product) {
        storageService.removeProductStorage(userCookie, product);
    }

}