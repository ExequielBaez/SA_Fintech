package com.h_3_22_proptech.fintech.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoanResponseDTO {

    private int nroFee;

    private double capital;

    private double interest;

    private double amortization;

    private double fee;

    private double balance;

    //cantidad de cuotas
    private int nPayments;

    //nro de cuota paga
    private int numberPayFee;
}
