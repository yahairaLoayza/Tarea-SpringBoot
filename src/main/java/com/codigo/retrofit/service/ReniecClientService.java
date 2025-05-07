package com.codigo.retrofit.service;

import com.codigo.retrofit.aggregates.response.ReniecResponse;

import java.util.Optional;

public interface ReniecClientService {
    Optional<ReniecResponse> getInfoReniec(String dni);
}
