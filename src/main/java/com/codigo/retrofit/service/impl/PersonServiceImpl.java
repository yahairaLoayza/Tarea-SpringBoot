package com.codigo.retrofit.service.impl;


import com.codigo.retrofit.aggregates.constants.Constants;
import com.codigo.retrofit.aggregates.response.ReniecResponse;
import com.codigo.retrofit.model.Person;
import com.codigo.retrofit.repository.PersonRepository;
import com.codigo.retrofit.retrofit.ClientReniecService;
import com.codigo.retrofit.retrofit.ClientRetrofit;
import com.codigo.retrofit.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Log4j2
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    ClientReniecService retrofitPreConfig =
            ClientRetrofit.getRetrofit()
                    .create(ClientReniecService.class);

    @Value("${token.api}")
    private String token;
    private PersonRepository personRepository;

    @Override
    public ReniecResponse findByDni(String dni) throws IOException {
        Response<ReniecResponse>  executeReniec = preparedClient(dni).execute();
        if(executeReniec.isSuccessful() && Objects.nonNull(executeReniec.body())){
            return executeReniec.body();
        }
        return new ReniecResponse();
    }

    public Person savePerson(String dni) throws IOException {
        Response<ReniecResponse> response = preparedClient(dni).execute();
        if (response.isSuccessful() && response.body() != null) {
            ReniecResponse data = response.body();

            if (!personRepository.existsByNumeroDocumento(data.getNumeroDocumento())) {
                Person person = new Person();
                person.setNombres(data.getNombres());
                person.setApellidoPaterno(data.getApellidoPaterno());
                person.setApellidoMaterno(data.getApellidoMaterno());
                person.setNombreCompleto(data.getNombreCompleto());
                person.setTipoDocumento(data.getTipoDocumento());
                person.setNumeroDocumento(data.getNumeroDocumento());
                person.setDigitoVerificador(data.getDigitoVerificador());
                person.setEstado(Constants.STATUS_ACTIVE);
                person.setUsuarioCreacion(Constants.USER_ADMIN);
                return personRepository.save(person);
            }
        }
        return null;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person updatePerson(Long id, Person personDetails) {
        Optional<Person> optional = personRepository.findById(id);
        if (optional.isPresent()) {
            Person existing = optional.get();
            existing.setNombres(personDetails.getNombres());
            existing.setApellidoPaterno(personDetails.getApellidoPaterno());
            existing.setApellidoMaterno(personDetails.getApellidoMaterno());
            existing.setNombreCompleto(personDetails.getNombreCompleto());
            existing.setTipoDocumento(personDetails.getTipoDocumento());
            existing.setNumeroDocumento(personDetails.getNumeroDocumento());
            existing.setDigitoVerificador(personDetails.getDigitoVerificador());
            existing.setEstado(personDetails.getEstado());
            return personRepository.save(existing);
        }
        return null;
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    private Call<ReniecResponse> preparedClient(String dni) {
        return retrofitPreConfig.findReniec("Bearer " + token, dni);
    }
}