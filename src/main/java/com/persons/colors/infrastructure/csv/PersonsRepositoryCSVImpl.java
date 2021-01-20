package com.persons.colors.infrastructure.csv;

import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import com.persons.colors.application.repsitory.PersonsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class PersonsRepositoryCSVImpl implements PersonsRepository {
  private static final String CSV_FILE_NAME = "src/main/resources/sample-input.csv";

  @Autowired
  private final PersonsCSVParser personsCSVParser;
  @Autowired
  private final PersonCSVWriter personCSVWriter;
  private List<Person> persons;

  @PostConstruct
  void parseSource() {

    persons = personsCSVParser.parse(CSV_FILE_NAME);
  }

  @Override
  public List<Person> findAll() {
    return persons;
  }

  @Override
  public Optional<Person> findById(PersonId id) {
    return persons.stream().filter(person -> person.getId().equals(id)).findFirst();
  }

  @Override
  public List<Person> findByColorCode(int colorId) {
    return persons.stream().filter(person -> person.getColorCode() == colorId).collect(Collectors.toList());
  }

  @Override
  public void save(Person person) {
    try {
      personCSVWriter.writeToCSV(CSV_FILE_NAME, person);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
