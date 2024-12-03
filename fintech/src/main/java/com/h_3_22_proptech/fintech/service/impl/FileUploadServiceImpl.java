package com.h_3_22_proptech.fintech.service.impl;

import com.h_3_22_proptech.fintech.persistance.entity.FileEntity;
import com.h_3_22_proptech.fintech.persistance.entity.UserEntity;
import com.h_3_22_proptech.fintech.persistance.repository.IFileRepository;
import com.h_3_22_proptech.fintech.persistance.repository.IUserRepository;
import com.h_3_22_proptech.fintech.service.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileUploadServiceImpl implements IFileUploadService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IFileRepository fileRepository;

    private final Path rootLocation = Paths.get("uploads");

    @Override
    public String saveFile(MultipartFile file, String idUser) {
        try {
            // Verifica si el usuario existe
            UserEntity user = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));

            // Guardar el archivo en el sistema de archivos
            String filename = idUser + "_" + file.getOriginalFilename();
            Path destinationFile = this.rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Crear y guardar la entidad File
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFilePath(destinationFile.toString());
            //fileEntity.setContentType(file.getContentType());
            //fileEntity.setFileSize(file.getSize());
            fileEntity.setUser(user); // Asociar al usuario

            fileRepository.save(fileEntity);

            return destinationFile.toString();
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public List<FileEntity> getFilesByUser(String idUser) {
        UserEntity user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFilesList();
    }
}

