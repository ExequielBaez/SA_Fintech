package com.h_3_22_proptech.fintech.service;


import com.h_3_22_proptech.fintech.dto.request.PersonaFisicaRequestDTO;
import com.h_3_22_proptech.fintech.dto.request.UpdatePersonaFisicaRequestDTO;
import com.h_3_22_proptech.fintech.persistance.entity.PersonaFisicaEntity;

public interface IPersonaFisicaService {

    PersonaFisicaEntity createPersonaFisica(PersonaFisicaRequestDTO personaFisicaRequestDTO);

    PersonaFisicaEntity getPersonaFisicaById(String idPersonaFisica);

    PersonaFisicaEntity updatePersonaFisica(UpdatePersonaFisicaRequestDTO upPf);
}
