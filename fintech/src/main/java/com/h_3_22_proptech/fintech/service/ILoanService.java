package com.h_3_22_proptech.fintech.service;

import com.h_3_22_proptech.fintech.dto.request.LoanRequestDTO;
import com.h_3_22_proptech.fintech.dto.response.LoanResponseDTO;
import com.h_3_22_proptech.fintech.persistance.entity.LoanEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface ILoanService {

    LoanResponseDTO createLoan(@RequestBody LoanRequestDTO loanRequestDTO);
}
