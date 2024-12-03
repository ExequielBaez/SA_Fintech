package com.h_3_22_proptech.fintech.controller;

import com.h_3_22_proptech.fintech.dto.request.PersonaJuridicaRequestDTO;
import com.h_3_22_proptech.fintech.dto.request.UpdatePersonaJuridicaRequestDTO;
import com.h_3_22_proptech.fintech.persistance.entity.PersonaJuridicaEntity;
import com.h_3_22_proptech.fintech.service.IPersonaJuridicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/personaJuridica")
public class PersonaJuridicaController {

    @Autowired
    private IPersonaJuridicaService personaJuridicaService;

    @GetMapping("{idPersonaJuridica}")
    public ResponseEntity<?> getPersonaJuridicaById(@PathVariable String idPersonaJuridica){

        PersonaJuridicaEntity personaJuridicaEntity = personaJuridicaService.getPersonaJuridicaById(idPersonaJuridica);

        return ResponseEntity.status(HttpStatus.OK).body(personaJuridicaEntity);

    }


    @PostMapping
    public ResponseEntity<?> createPersonaJuridica(@RequestBody PersonaJuridicaRequestDTO personaJuridicaRequestDTO){

        PersonaJuridicaEntity personaJuridica = personaJuridicaService.createPersonaJuridica(personaJuridicaRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(personaJuridica);

    }

    @PutMapping("/update")
    private ResponseEntity<?> updatePersonaJuridica(@RequestBody UpdatePersonaJuridicaRequestDTO upPj){

        PersonaJuridicaEntity personaJuridica = personaJuridicaService.updatePersonaJuridica(upPj);

        return ResponseEntity.status(HttpStatus.CREATED).body(personaJuridica);

    }
}
