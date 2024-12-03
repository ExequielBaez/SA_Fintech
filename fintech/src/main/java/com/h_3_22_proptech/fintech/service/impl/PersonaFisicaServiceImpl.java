package com.h_3_22_proptech.fintech.service.impl;

import com.h_3_22_proptech.fintech.dto.request.PersonaFisicaRequestDTO;
import com.h_3_22_proptech.fintech.dto.request.UpdatePersonaFisicaRequestDTO;
import com.h_3_22_proptech.fintech.persistance.entity.PersonaFisicaEntity;
import com.h_3_22_proptech.fintech.persistance.entity.UserEntity;
import com.h_3_22_proptech.fintech.persistance.mapper.IPersonaFisicaMapper;
import com.h_3_22_proptech.fintech.persistance.repository.IPersonaFisicaRepository;
import com.h_3_22_proptech.fintech.persistance.repository.IUserRepository;
import com.h_3_22_proptech.fintech.service.IPersonaFisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaFisicaServiceImpl implements IPersonaFisicaService {

    @Autowired
    private IPersonaFisicaRepository personaFisicaRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPersonaFisicaMapper personaFisicaMapper;

    @Override
    public PersonaFisicaEntity getPersonaFisicaById(String idPersonaFisica) {

        PersonaFisicaEntity personaFisica = personaFisicaRepository.findById(idPersonaFisica).orElseThrow();

        return personaFisica;
    }


    @Override
    public PersonaFisicaEntity createPersonaFisica(PersonaFisicaRequestDTO personaFisicaRequestDTO) {

        UserEntity user = userRepository.findById(personaFisicaRequestDTO.getIdUser()).orElseThrow();

        PersonaFisicaEntity personaFisicaEntity = personaFisicaMapper.toPersonaFisicaEntity(personaFisicaRequestDTO);

        personaFisicaEntity.setUserEntity(user);

        return personaFisicaRepository.save(personaFisicaEntity);

    }

    @Override
    public PersonaFisicaEntity updatePersonaFisica(UpdatePersonaFisicaRequestDTO upPf) {

        PersonaFisicaEntity personaFisicaEntity = personaFisicaRepository.findById(upPf.getIdPF()).orElseThrow();

        personaFisicaEntity.setAddress(upPf.getAddress());
        personaFisicaEntity.setLocalidad(upPf.getLocalidad());
        personaFisicaEntity.setProvincia(upPf.getProvincia());
        personaFisicaEntity.setPostalCode(upPf.getPostalCode());


        return personaFisicaRepository.save(personaFisicaEntity);
    }


}
