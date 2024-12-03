package com.h_3_22_proptech.fintech.persistance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class LoanEntity {
    @Id
    @UuidGenerator
    private String idLoan;

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

    @ManyToOne
    private PersonaFisicaEntity personaFisicaEntity;

}
