package com.persons.colors.infrastructure.csv;

import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonsRepositoryCSVImplTest {

  @InjectMocks
  PersonsRepositoryCSVImpl personsRepositoryCSV;
  @Mock
  PersonsCSVParser parser;
  @Mock
  PersonCSVWriter writer;
  @Captor
  ArgumentCaptor<Person> personArgumentCaptor;

  @BeforeEach
  public void init() {
    PersonId personId = new PersonId(1l);
    Person person1 = new Person(personId, "Müller", "Hans", "67742", "Lauterecken", 1);
    Person person2 = new Person(new PersonId(2l), "Bart", "Bertram", "", "", 0);
    Person person3 = new Person(new PersonId(3l), "Test", "Mustermann", "", "", 1);
    when(parser.parse("src/main/resources/sample-input.csv")).thenReturn(Arrays.asList(person1, person2, person3));
    personsRepositoryCSV.parseSource();
  }

  @Test
  void canFindPersonById() {
    //given
    PersonId personId = new PersonId(1l);
    Person person1 = new Person(personId, "Müller", "Hans", "67742", "Lauterecken", 1);

    //when
    Person result = personsRepositoryCSV.findById(personId).get();

    //then
    assertThat(result).isEqualTo(person1);
  }

  @Test
  void returnsEmptyIfNoPersonWithGivenIdFound() {
    //given
    PersonId anotherPersonId = new PersonId();

    //when
    Optional<Person> result = personsRepositoryCSV.findById(anotherPersonId);

    //then
    assertThat(result).isEqualTo(Optional.empty());
  }

  @Test
  void findsPersonsByColor() {
    //given
    int color = 1;
    Person person1 = new Person(new PersonId(1l), "Müller", "Hans", "67742", "Lauterecken", color);
    Person person2 = new Person(new PersonId(3l), "Test", "Mustermann", "", "", color);

    //when
    List<Person> persons = personsRepositoryCSV.findByColorCode(color);

    //then
    assertThat(persons).containsExactlyInAnyOrder(person1, person2);
  }


  @Test
  void findsAllCorrectly() {
    //given
    Person person1 = new Person(new PersonId(1l), "Müller", "Hans", "67742", "Lauterecken", 1);
    Person person2 = new Person(new PersonId(2l), "Bart", "Bertram", "", "", 0);
    Person person3 = new Person(new PersonId(3l), "Test", "Mustermann", "", "", 1);

    //when
    List<Person> persons = personsRepositoryCSV.findAll();

    //then
    assertThat(persons).containsExactlyInAnyOrder(person1, person2, person3);
  }

  @Test
  void delegatesInsertionToCSVWriter() throws IOException {
    //given
    PersonId personId = new PersonId();
    Person person1 = new Person(personId, "Müller", "Hans", "67742", "Lauterecken", 1);


    //when
    personsRepositoryCSV.save(person1);

    //then
    verify(writer).writeToCSV(anyString(), personArgumentCaptor.capture());
    Person result = personArgumentCaptor.getValue();
    assertThat(result).isEqualTo(person1);
  }

}
