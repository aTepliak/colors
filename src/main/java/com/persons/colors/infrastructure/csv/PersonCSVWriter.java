package com.persons.colors.infrastructure.csv;

import com.opencsv.CSVWriter;
import com.persons.colors.application.entity.Person;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PersonCSVWriter {

  public void writeToCSV(String fileName, Person person) throws IOException {
    addLineBreaksToFileIfNotPresent(fileName);
    try {
      try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, true), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_ESCAPE_CHARACTER,
              CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.RFC4180_LINE_END)) {
        String[] record = personToCsV(person);
        writer.writeNext(record);
        writer.flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void addLineBreaksToFileIfNotPresent(String fileName) throws IOException {
    StringBuilder result = new StringBuilder();

    File file = new File(fileName);
    LineIterator it =  FileUtils.lineIterator(file);
    try {
      while (it.hasNext()) {
        String line = it.nextLine();
        if(!line.endsWith(CSVWriter.RFC4180_LINE_END))
          line += CSVWriter.RFC4180_LINE_END;
        result.append(line);
      }
    } finally {
      it.close();
    }
    FileUtils.writeStringToFile(file, result.toString(), "UTF-8");
  }

  private String[] personToCsV(Person person) {
    return new String[]{
      person.getLastName(),
      person.getName(),
      person.getZipCode() + " " + person.getCity(),
      String.valueOf(person.getColorCode())};
  }
}
