package com.h_3_22_proptech.fintech.service.impl;

import com.h_3_22_proptech.fintech.dto.request.LoanRequestDTO;
import com.h_3_22_proptech.fintech.dto.response.LoanResponseDTO;
import com.h_3_22_proptech.fintech.dto.response.LoanSimulationResponseDTO;
import com.h_3_22_proptech.fintech.persistance.entity.LoanEntity;
import com.h_3_22_proptech.fintech.persistance.entity.PersonaFisicaEntity;
import com.h_3_22_proptech.fintech.persistance.mapper.ILoanMapper;
import com.h_3_22_proptech.fintech.persistance.repository.ILoanRepository;
import com.h_3_22_proptech.fintech.persistance.repository.IPersonaFisicaRepository;
import com.h_3_22_proptech.fintech.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements ILoanService {

    @Autowired
    private IPersonaFisicaRepository personaFisicaRepository;

    @Autowired
    private ILoanRepository loanRepository;

    @Autowired
    private ILoanMapper loanMapper;

    @Override
    public LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO) {

        PersonaFisicaEntity pfE = personaFisicaRepository.findById(loanRequestDTO.getIdPF()).orElseThrow();

        int nPayments = loanRequestDTO.getNPayments();
        double capital = loanRequestDTO.getCapital();
        double saldo = loanRequestDTO.getCapital();
        double tasaPeriodo = (loanRequestDTO.getTNA() / 100) / 12;
        double cuotaFija = (capital * tasaPeriodo * Math.pow(1 + tasaPeriodo, nPayments)) /
                (Math.pow(1 + tasaPeriodo, nPayments) - 1);

        cuotaFija = Math.round(cuotaFija * 100.0) / 100.0;

        double interes = Math.round((saldo * tasaPeriodo) * 100.0) / 100.0;
        double amortizacion = Math.round((cuotaFija - interes) * 100.0) / 100.0;
        saldo = Math.round((saldo - amortizacion) * 100.0) / 100.0;

        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setCapital(capital);
        loanEntity.setAmortization(amortizacion);
        loanEntity.setBalance(saldo);
        loanEntity.setFee(cuotaFija);
        loanEntity.setInterest(interes);
        loanEntity.setNroFee(1);
        loanEntity.setNumberPayFee(0);
        loanEntity.setPersonaFisicaEntity(pfE);
        loanEntity.setNPayments(nPayments);


        loanRepository.save(loanEntity);

        return loanMapper.toLoanResponseDTO(loanEntity);
    }
}
