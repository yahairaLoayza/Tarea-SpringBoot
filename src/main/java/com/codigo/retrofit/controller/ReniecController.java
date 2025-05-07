package com.codigo.retrofit.controller;

import com.codigo.retrofit.exception.ConsultaReniecException;
import com.codigo.retrofit.service.ReniecClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reniec")
@RequiredArgsConstructor
public class ReniecController {

    private final ReniecClientService reniecClientService;

    @GetMapping("/{dni}")
    public ResponseEntity<?> getDatosPorDni(@PathVariable String dni) {
        try {
            return reniecClientService.getInfoReniec(dni)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new ConsultaReniecException("No se encontraron datos para el DNI: " + dni));
        } catch (ConsultaReniecException ex) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                    Map.of("error", "RENIEC_SERVICE_ERROR", "message", ex.getMessage())
            );
        }
    }
}
