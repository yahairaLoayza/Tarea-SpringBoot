package com.codigo.retrofit.controller;

import com.codigo.retrofit.aggregates.response.ReniecResponse;
import com.codigo.retrofit.model.Person;
import com.codigo.retrofit.service.PersonService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/person/")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/find/{dni}")
    public ResponseEntity<ReniecResponse> findPerson(@PathVariable String dni) throws IOException {
        return new ResponseEntity<>(personService.findByDni(dni), HttpStatus.OK);
    }

    @PostMapping("/save/{dni}")
    public ResponseEntity<Person> savePerson(@PathVariable String dni) throws IOException{
        Person person = personService.savePerson(dni);
        if (person!= null) {
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons(){
        return new ResponseEntity<>(personService.getAllPersons(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id){
        Person person = personService.getPersonById(id);
        if (person != null) {
            return new ResponseEntity<>(person, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personaDetails) {
        Person updated = personService.updatePerson(id, personaDetails);
        if (updated != null){
           return new ResponseEntity<>(updated, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}