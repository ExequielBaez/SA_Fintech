package com.h_3_22_proptech.fintech.persistance.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class FileEntity {

    @Id
    @UuidGenerator
    private String idFile;

    private String fileName;

    private String filePath;

    @ManyToOne
    private UserEntity user;
}
