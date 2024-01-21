package org.t1.foodApp.storage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
        var test = service.findAll(userCookie);
        return test;
    }

    @PostMapping
    public Storage createStorage(@UserCookie String userCookie, @Validated @RequestBody Storage storage) {
        if (service.addStorage(userCookie, storage)) {
            return storage;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Storage not added");
        }
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
