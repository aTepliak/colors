package com.persons.colors.infrastructure.csv;

import com.opencsv.CSVWriter;
import com.persons.colors.application.entity.Person;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class PersonCSVWriter {

  public void writeToCSV(String fileName, Person person) throws IOException {
    PrintWriter csvWriter;
    try {
      CSVWriter writer = new CSVWriter(new FileWriter(fileName, true), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.RFC4180_LINE_END);

      String[] record = personToCsV(person);

      writer.writeNext(record);
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private String[] personToCsV(Person person) {
    return new String[]{
      person.getLastName(),
      person.getName(),
      person.getZipCode() + " " + person.getCity(),
      String.valueOf(person.getColorCode())};
  }
}
