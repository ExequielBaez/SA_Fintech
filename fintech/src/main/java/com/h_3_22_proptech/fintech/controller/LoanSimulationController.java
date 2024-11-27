package com.h_3_22_proptech.fintech.controller;

import com.h_3_22_proptech.fintech.dto.response.LoanSimulationResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/api/loanSimulation")
public class LoanSimulationController {

    @GetMapping
    public ResponseEntity<?> createSimulation(@RequestParam ("capital") Double capital,
                                              @RequestParam ("nPayments") Integer nPayments,
                                              @RequestParam ("TNA") Double TNA
                                             ) {
        List<LoanSimulationResponseDTO> listaCuotas = new ArrayList<>();
        double saldo = capital;
        double tasaPeriodo = (TNA / 100) / 12;
        double cuotaFija = (capital * tasaPeriodo * Math.pow(1 + tasaPeriodo, nPayments)) /
                (Math.pow(1 + tasaPeriodo, nPayments) - 1);

        cuotaFija = Math.round(cuotaFija * 100.0) / 100.0;

        for (int i = 1; i <= nPayments; i++) {
            double interes = Math.round((saldo * tasaPeriodo) * 100.0) / 100.0;
            double amortizacion = Math.round((cuotaFija - interes) * 100.0) / 100.0;
            saldo = Math.round((saldo - amortizacion) * 100.0) / 100.0;

            listaCuotas.add(new LoanSimulationResponseDTO(i, capital, interes, amortizacion, cuotaFija, saldo));

            capital -= capital - saldo;
        }


        return ResponseEntity.status(HttpStatus.OK).body(listaCuotas);
    }
}
