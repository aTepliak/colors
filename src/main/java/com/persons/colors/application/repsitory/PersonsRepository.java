package com.persons.colors.application.repsitory;

import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;

import java.util.List;
import java.util.Optional;

public interface PersonsRepository {
  List<Person> findAll();

  Optional<Person> findById(PersonId id);

  List<Person> findByColorCode(int colorId);

  void save(Person person);
}
