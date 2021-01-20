package com.persons.colors.infrastructure.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PersonsCSVParser {
  public List<Person> parse(String fileName) {
    List<Person> persons = new ArrayList<>();
    try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
      int counter = 0;
      String[] personsData;
      while ((personsData = reader.readNext()) != null && !personsData[0].isEmpty()) {
        String zip = parseZip(personsData);
        String city = parseCity(personsData);
        int colorCode = parseColor(personsData);
        String name = personsData[1].strip();
        String lastName = personsData[0].strip();
        Person person = new Person(new PersonId(++counter), name, lastName, zip, city, colorCode);
        log.info("Read entry from CSV: "+ person.toString());
        persons.add(person);
      }
    } catch (IOException | CsvValidationException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    return persons;
  }

  private int parseColor(String... record) {
    if (record.length > 3)
      return Integer.parseInt(record[3].trim());
    return 0;
  }

  private String parseZip(String... record) {
    if (record.length > 2)
      return record[2].replaceAll("[^0-9,]", "");
    return "none";
  }

  private String parseCity(String... record) {
    if (record.length > 2)
      return record[2].replaceAll("[^A-Za-z\\s\\-]", "").strip();
    return "none";
  }


}
