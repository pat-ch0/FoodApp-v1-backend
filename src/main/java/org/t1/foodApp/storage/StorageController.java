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
  
    @GetMapping()
    public List<Storage> getStorages(@UserCookie String userCookie) {
        return service.findAll(userCookie);
    }

    @GetMapping("/{id}")
    public Storage getStorageById(@PathVariable String id, @UserCookie String userCookie) {
        return service.getStorageById(userCookie, id);
    }

    @DeleteMapping("/{id}")
    public void removeStorageById(@UserCookie String userCookie, @PathVariable String id) {
        service.removeStorageById(userCookie, id);
    }

    @PutMapping
    public Storage addStorage(@UserCookie String userCookie, @Validated @RequestBody Storage storage) {
        if(service.addStorage(userCookie, storage))
            return storage;
        throw new RuntimeException("Storage not added");
    }
}
