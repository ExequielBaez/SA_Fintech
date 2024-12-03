package com.h_3_22_proptech.fintech.persistance.entity;

import com.h_3_22_proptech.fintech.enums.TypeUser;
import com.h_3_22_proptech.fintech.enums.UserSearch;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="users")
public class UserEntity {
    @Id
    @UuidGenerator
    private String idUser;

    private String email;

    private String password;

    private String password2;

    @Enumerated(EnumType.STRING)
    private TypeUser TypeUser;

    @Enumerated(EnumType.STRING)
    private UserSearch UserSearch;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileEntity> filesList;


}
