package com.persons.colors.infrastructure.persistence;

import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import com.persons.colors.application.repsitory.PersonsRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class PersonsRepositoryJPAImpl implements PersonsRepository {

  @Autowired
  private PersonsRepositoryJpa personsRepositoryJpa;

  @Override
  public List<Person> findAll() {
    return IterableUtils.toList(personsRepositoryJpa.findAll());
  }

  @Override
  public Optional<Person> findById(PersonId id) {
    return personsRepositoryJpa.findById(id);
  }

  @Override
  public List<Person> findByColorCode(int colorCode) {
    return personsRepositoryJpa.findColorCode(colorCode);
  }

  @Override
  public void save(Person person) {
    personsRepositoryJpa.save(person);

  }
}
