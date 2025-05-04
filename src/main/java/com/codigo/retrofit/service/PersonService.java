package com.codigo.retrofit.service;


import com.codigo.retrofit.aggregates.response.ReniecResponse;
import com.codigo.retrofit.model.Person;

import java.io.IOException;
import java.util.List;

public interface PersonService {

    ReniecResponse findByDni(String dni) throws IOException;

    Person savePerson(String dni) throws IOException;

    List<Person> getAllPersons();
    Person getPersonById(Long id);
    Person updatePerson(Long id, Person personDetails);
    void deletePerson(Long id);
}
