package com.codigo.retrofit.service.impl;

import com.codigo.retrofit.api.ReniecApi;
import com.codigo.retrofit.aggregates.response.ReniecResponse;
import com.codigo.retrofit.exception.ConsultaReniecException;
import com.codigo.retrofit.service.ReniecClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReniecClientServiceImpl implements ReniecClientService {

    private final ReniecApi reniecApi;

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public Optional<ReniecResponse> getInfoReniec(String dni) {
        try {
            Call<ReniecResponse> call = reniecApi.getPersona(dni);
            Response<ReniecResponse> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                return Optional.of(response.body());
            } else {
                throw new ConsultaReniecException("Error en respuesta de RENIEC: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            throw new ConsultaReniecException("Error al conectarse con RENIEC", e);
        }
    }
}
