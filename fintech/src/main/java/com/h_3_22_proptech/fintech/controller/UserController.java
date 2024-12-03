package com.h_3_22_proptech.fintech.controller;

import com.h_3_22_proptech.fintech.persistance.entity.FileEntity;
import com.h_3_22_proptech.fintech.persistance.entity.UserEntity;
import com.h_3_22_proptech.fintech.service.IFileUploadService;
import com.h_3_22_proptech.fintech.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("v1/api/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileUploadService fileService;

    @GetMapping("{idUser}")
    public ResponseEntity<?> getUserById(@PathVariable String idUser){

        UserEntity userEntity = userService.getUserById(idUser);

        return ResponseEntity.status(HttpStatus.OK).body(userEntity);

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity userEntity){

        UserEntity user = userService.createUser(userEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("idUser") String idUser) {
        try {
            String filePath = fileService.saveFile(file, idUser);
            return ResponseEntity.ok("File uploaded successfully: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/listFiles/{idUser}")
    public ResponseEntity<List<FileEntity>> getFilesByUser(@PathVariable String idUser) {

        //tengo que cambiar este endpoint con una consulta sql q me traiga los path y sus nombres

        try {
            List<FileEntity> files = fileService.getFilesByUser(idUser);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
