package com.persons.colors.application.appservice;

import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import com.persons.colors.application.repsitory.PersonsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonColorAppService {

  @Autowired
  private final PersonsRepository personsRepository;

  public List<Person> retrieveAll() {
    return personsRepository.findAll();
  }

  public Optional<Person> retrievePersonBy(PersonId personId) {
    return personsRepository.findById(personId);
  }

  public List<Person> retrieveAllPersonsByColor(int colorCode) {
    return personsRepository.findByColorCode(colorCode);
  }

  public void insertPerson(Person person) {
    personsRepository.save(person);
  }
}
