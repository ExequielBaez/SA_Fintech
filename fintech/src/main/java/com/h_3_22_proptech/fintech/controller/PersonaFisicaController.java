package com.h_3_22_proptech.fintech.controller;

import com.h_3_22_proptech.fintech.dto.request.PersonaFisicaRequestDTO;
import com.h_3_22_proptech.fintech.dto.request.UpdatePersonaFisicaRequestDTO;
import com.h_3_22_proptech.fintech.persistance.entity.PersonaFisicaEntity;
import com.h_3_22_proptech.fintech.service.IPersonaFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/api/personaFisica")
public class PersonaFisicaController {

    @Autowired
    private IPersonaFisicaService personaFisicaService;

    @GetMapping("{idPersonaFisica}")
    public ResponseEntity<?> getPersonaFisicaById(@PathVariable String idPersonaFisica){

        PersonaFisicaEntity personaFisicaEntity = personaFisicaService.getPersonaFisicaById(idPersonaFisica);

        return ResponseEntity.status(HttpStatus.OK).body(personaFisicaEntity);

    }


    @PostMapping
    public ResponseEntity<?> createPersonaFisica(@RequestBody PersonaFisicaRequestDTO personaFisicaRequestDTO){

        PersonaFisicaEntity personaFisica = personaFisicaService.createPersonaFisica(personaFisicaRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(personaFisica);

    }

    @PutMapping("/update")
    private ResponseEntity<?> updatePersonaFisica(@RequestBody UpdatePersonaFisicaRequestDTO upPf){

        PersonaFisicaEntity personaFisica = personaFisicaService.updatePersonaFisica(upPf);

        return ResponseEntity.status(HttpStatus.CREATED).body(personaFisica);

    }


}
