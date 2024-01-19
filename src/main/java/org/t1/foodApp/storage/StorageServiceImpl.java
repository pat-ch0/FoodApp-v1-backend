package org.t1.foodApp.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.t1.foodApp.product.DeleteProduct;
import org.t1.foodApp.product.Product;

import java.util.*;

@Service
public class StorageServiceImpl {

    private final Map<String, List<Storage>> storages = new HashMap<>();

    public List<Storage> findAll(String userCookie) {
        return storages.getOrDefault(userCookie, new ArrayList<>());
    }

    public boolean addStorage(String userCookie, Storage storage) {
        // if name all ready exists
        if (storages.getOrDefault(userCookie, new ArrayList<>()).stream().anyMatch(s -> s.getLabel().equals(storage.getLabel())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Storage name already exists");
        if(storage.getId() == null || storage.getId().isEmpty())
            storage.setId(String.valueOf(Math.random()).substring(2, 15));
        storage.setProducts(new ArrayList<>());
        storages.computeIfAbsent(userCookie, k -> new ArrayList<>()).add(storage);
        return true;
    }

    public Storage getStorageById(String userCookie, String id) {
        List<Storage> userStorages = storages.get(userCookie);
        if (userStorages == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No storages found for user");

        return userStorages.stream()
                .filter(storage -> id.equals(storage.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Storage not found"));
    }

    public void removeStorageById(String userCookie, String id) {
        List<Storage> userStorages = storages.get(userCookie);
        if (userStorages == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No storages found for user");
        boolean removed = userStorages.removeIf(storage -> id.equals(storage.getId()));
        if (!removed)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Storage not found");
    }

    public void addProductStorage(String userCookie, Product product) {
        String storageId = product.getStorageId();
        if(product.isProductValid())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found");
        Storage storage = getStorageById(userCookie, storageId);
        if (storage.getProducts().stream().anyMatch(p -> p.getBarcode().equals(product.getBarcode())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already exists in storage");
        storage.addProduct(product);
    }

    public void updateProductStorage(String userCookie, Product product) {
        String storageId = product.getStorageId();
        Storage storage = getStorageById(userCookie, storageId);
        storage.updateProduct(product);
    }

    public void removeProductStorage(String userCookie, DeleteProduct product) {
        String storageId = product.getStorageId();
        Storage storage = getStorageById(userCookie, storageId);
        storage.removeProductBarcode(product.getBarcode());
    }


}