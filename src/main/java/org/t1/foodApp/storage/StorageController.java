package org.t1.foodApp.storage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.t1.foodApp.annotation.UserCookie;

import java.util.List;


@RestController
@RequestMapping("/storages")
@CrossOrigin
public class StorageController {


    @Autowired
    private StorageServiceImpl service;
    @Operation(summary = "Get All Storages", description = "Récupère tous les espaces de stockage de l'utilisateur")
    @GetMapping()
    public List<Storage> getStorages(@Parameter(description = "Cookie de l'utilisateur") @UserCookie String userCookie) {
        return service.findAll(userCookie);
    }

    @Operation(summary = "Get Storage by ID", description = "Récupère un espace de stockage par son ID")
    @GetMapping("/{id}")
    public Storage getStorageById(@Parameter(description = "Cookie de l'utilisateur") @UserCookie String userCookie,
                                  @Parameter(description = "ID de l'espace de stockage") @PathVariable String id) {
        return service.getStorageById(userCookie, id);
    }

    @Operation(summary = "Remove Storage by ID", description = "Supprime un espace de stockage par son ID")
    @DeleteMapping("/{id}")
    public void removeStorageById(@Parameter(description = "Cookie de l'utilisateur") @UserCookie String userCookie,
                                  @Parameter(description = "ID de l'espace de stockage") @PathVariable String id) {
        service.removeStorageById(userCookie, id);
    }

    @Operation(summary = "Add Storage", description = "Ajoute un nouvel espace de stockage")
    @PostMapping
    public Storage addStorage(@Parameter(description = "Cookie de l'utilisateur") @UserCookie String userCookie,
                              @Parameter(description = "Nouvel espace de stockage") @Validated @RequestBody Storage storage) {
        if(service.addStorage(userCookie, storage))
            return storage;
        throw new RuntimeException("Storage not added");
    }
}
