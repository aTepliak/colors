package com.persons.colors.infrastructure.csv;

import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersonCSVWriterTest {

  private final static String FILE_NAME = "src/test/resources/sample-input.csv";

  @AfterEach
  void cleanUp() {
    try {
      RandomAccessFile f = new RandomAccessFile(FILE_NAME, "rw");
      long length = f.length() - 1;
      byte b;
      do {
        length -= 1;
        f.seek(length);
        b = f.readByte();
      } while (b != 10 && length > 0);
      if (length == 0) {
        f.setLength(length);
      } else {
        f.setLength(length + 1);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void canWriteTOCSVCorrectly() throws IOException {
    //given
    Person person = new Person(new PersonId(11l), "Name", "LastName", "22222", "Test-test new", 1);

    //when
    new PersonCSVWriter().writeToCSV(FILE_NAME, person);

    //then
    List<Person> persons = new PersonsCSVParser().parse(FILE_NAME);
    assertThat(persons.size()).isEqualTo(11);
    assertThat(persons).contains(person);
  }
}
