package com.persons.colors.infrastructure.persistence;

import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonsRepositoryJpa extends CrudRepository<Person, PersonId> {

  @Query("SELECT r  FROM Person r where r.colorCode = :code")
  List<Person> findColorCode(@Param("code") int code);
}
