package com.h_3_22_proptech.fintech.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
//@Builder
public class LoanSimulationRequestDTO {

    private double capital;
    private int nPayments;
    private double TNA;
}
