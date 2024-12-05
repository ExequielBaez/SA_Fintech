package com.h_3_22_proptech.fintech.controller;

import com.h_3_22_proptech.fintech.dto.request.LoanRequestDTO;
import com.h_3_22_proptech.fintech.dto.response.LoanResponseDTO;
import com.h_3_22_proptech.fintech.dto.response.LoanSimulationResponseDTO;
import com.h_3_22_proptech.fintech.persistance.entity.LoanEntity;
import com.h_3_22_proptech.fintech.persistance.entity.UserEntity;
import com.h_3_22_proptech.fintech.service.ILoanService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/api/loan")
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody LoanRequestDTO loanRequestDTO){

        LoanResponseDTO loanResponseDTO= loanService.createLoan(loanRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponseDTO);

    }


    @GetMapping("/simulation")
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

    @GetMapping("/reporte")
    public ResponseEntity<byte[]> generarReporte(
            @RequestParam("capital") Double capital,
            @RequestParam("nPayments") Integer nPayments,
            @RequestParam("TNA") Double TNA) throws Exception {

        //Esto lo hice aqui en la linea 57 y no en la 86 para que conserve los valores pasados antes que se modifiquen
        //Creo un mapa para parametros si no tengo parametros pasandole la lista al datasource es suficiente
        //recordar que si el datasource le paso una lista esos en el reporte son fields
        //si le paso otro dato que no esta en la lista y por lo general son datos que no se tienen que repetir como si fuera una tabla
        //se los paso como parametros
        Map<String, Object> myParameters = new HashMap<>();
        myParameters.put("TNA", TNA);
        myParameters.put("nPayments" , nPayments);
        myParameters.put("capital" , capital);

        // Genero la lista de cuotas
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



        // Cargo el reporte desde el archivo .jasper
        InputStream jasperStream = this.getClass().getResourceAsStream("/reportes/simulador.jasper");

        //esto lo agrego para que me de los logs por si hay errores
        System.setProperty("net.sf.jasperreports.debug", "true");
        System.setProperty("java.awt.headless", "true");

        //esto es para saber si encuentra el archivo
        if (jasperStream == null) {
            throw new RuntimeException("Archivo '.jasper' no encontrado en el classpath.");
        }

        // Creo el datasource a partir de la lista
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaCuotas);

        // Relleno el reporte con la lista que meti en el datasource y si tengo parametros con el map. Sino null en parametros
        //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, null, dataSource);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, myParameters, dataSource);
        // Exporta el reporte a PDF
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        // Configuro la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "loanSimulationReport.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
