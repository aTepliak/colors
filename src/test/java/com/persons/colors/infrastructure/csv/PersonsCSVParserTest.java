package com.persons.colors.infrastructure.csv;

import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersonsCSVParserTest {

  @Test
  void canParseCSVCorrectly() {
    //given
    Person person1 = new Person(new PersonId(1l), "Hans", "Müller", "67742", "Lauterecken", 1);
    Person person2 = new Person(new PersonId(8l), "Bertram", "Bart", "", "", 0);

    //when
    List<Person> persons = new PersonsCSVParser().parse("src/test/resources/sample-input.csv");

    //then
    assertThat(persons.size()).isEqualTo(10);
    assertThat(persons).contains(person1, person2);
  }
}
