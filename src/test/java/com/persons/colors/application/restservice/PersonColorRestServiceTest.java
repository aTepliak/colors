package com.persons.colors.application.restservice;

import com.persons.colors.application.appservice.PersonColorAppService;
import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonColorRestService.class)
class PersonColorRestServiceTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonColorAppService appService;

  void setUp() {
    Person person1 = new Person(new PersonId(), "Müller", "Hans", "67742", "Lauterecken", 1);
    Person person2 = new Person(new PersonId(), "Bart", "Bertram", "", "", 0);
    Person person3 = new Person(new PersonId(), "Test", "Mustermann", "", "", 1);
    when(appService.retrievePersonBy(person1.getId())).thenReturn(Optional.of(person1));
    when(appService.retrieveAll()).thenReturn(Arrays.asList(person1, person2, person3));
    when(appService.retrieveAllPersonsByColor(1)).thenReturn(Arrays.asList(person1, person3));
  }

  @Test
  void retrievsAllPersons() throws Exception {
    //given
    Person person1 = new Person(new PersonId(1l), "Max", "Maxmann", "67742", "Lauterecken", 1);
    Person person2 = new Person(new PersonId(2l), "FirstName", "LastName", "", "", 0);
    Person person3 = new Person(new PersonId(3l), "Test", "Test", "", "", 1);
    when(appService.retrieveAll()).thenReturn(Arrays.asList(person1, person2, person3));
    String result = "[{\"id\":1,\"name\":\"Max\",\"lastName\":\"Maxmann\",\"zipCode\":\"67742\",\"city\":\"Lauterecken\",\"color\":\"blau\"},{\"id\":2," +
      "\"name\":\"FirstName\",\"lastName\":\"LastName\",\"zipCode\":\"\",\"city\":\"\",\"color\":\"keine\"},{\"id\":3,\"name\":\"Test\"," +
      "\"lastName\":\"Test\",\"zipCode\":\"\",\"city\":\"\",\"color\":\"blau\"}]";

    //when
    //then
    mockMvc.perform(get("/persons").contentType(MediaType.APPLICATION_JSON)
      .characterEncoding("utf-8"))
      .andExpect(status().isOk()).andExpect(content().string(result));
  }

  @Test
  void canRetrievePersonsByColor() throws Exception {
    //given
    int colorCode = 1;
    Person person1 = new Person(new PersonId(1l), "Max", "Maxmann", "67742", "Lauterecken", colorCode);
    Person person2 = new Person(new PersonId(3l), "Test", "Test", "", "", colorCode);
    when(appService.retrieveAllPersonsByColor(colorCode)).thenReturn(Arrays.asList(person1, person2));
    String result = "[{\"id\":1,\"name\":\"Max\",\"lastName\":\"Maxmann\",\"zipCode\":\"67742\",\"city\":\"Lauterecken\",\"color\":\"blau\"},{\"id\":3," +
      "\"name\":\"Test\",\"lastName\":\"Test\",\"zipCode\":\"\",\"city\":\"\",\"color\":\"blau\"}]";

    //when
    //then
    mockMvc.perform(get("/persons/color/1").contentType(MediaType.APPLICATION_JSON)
      .characterEncoding("utf-8"))
      .andExpect(status().isOk()).andExpect(content().string(result));
  }

  @Test
  void returnsEmptyResponseIfNoPersonsByColorFound() throws Exception {
    //given
    int colorCode = 1;
    ;
    Person person1 = new Person(new PersonId(), "Max", "Maxmann", "67742", "Lauterecken", colorCode);
    Person person2 = new Person(new PersonId(), "Test", "Test", "", "", colorCode);
    when(appService.retrieveAllPersonsByColor(115)).thenReturn(Collections.emptyList());

    //when
    //then
    mockMvc.perform(get("/persons/color/1").contentType(MediaType.APPLICATION_JSON)
      .characterEncoding("utf-8"))
      .andExpect(status().isOk()).andExpect(content().string("[]"));
  }

  @Test
  void canFindPersonById() throws Exception {
    //given
    int colorCode = 1;
    ;
    PersonId personId = new PersonId(1l);
    Person person1 = new Person(personId, "Max", "Maxmann", "67742", "Lauterecken", colorCode);
    when(appService.retrievePersonBy(personId)).thenReturn(Optional.of(person1));
    String result = "{\"id\":1,\"name\":\"Max\",\"lastName\":\"Maxmann\",\"zipCode\":\"67742\",\"city\":\"Lauterecken\",\"color\":\"blau\"}";

    //when
    //then
    mockMvc.perform(get("/persons/1").contentType(MediaType.APPLICATION_JSON)
      .characterEncoding("utf-8"))
      .andExpect(status().isOk()).andExpect(content().string(result));
  }

  @Test
  void returnsEmptyResponseIfNoPersonFoundById() throws Exception {
    //given
    int colorCode = 1;
    PersonId personId = new PersonId(1l);
    Person person1 = new Person(personId, "Max", "Maxmann", "67742", "Lauterecken", colorCode);
    when(appService.retrievePersonBy(personId)).thenReturn(Optional.of(person1));

    //when
    //then
    mockMvc.perform(get("/persons/2").contentType(MediaType.APPLICATION_JSON)
      .characterEncoding("utf-8"))
      .andExpect(status().isNotFound())
      .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(NoPersonFound.class))
      .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("Person with Id '2' could not be found."));
  }

  @Test
  void canInsertPerson() throws Exception {
    //given
    int colorCode = 1;
    ;
    PersonId personId = new PersonId();
    Person person1 = new Person(personId, "Max", "Maxmann", "67742", "Lauterecken", colorCode);

    //when
    //then
    mockMvc.perform(post("/person")
      .contentType(MediaType.APPLICATION_JSON)
      .content("{\"id\":1,\"name\":\"Hans\",\"lastName\":\"Müller\",\"zipCode\":\"67742\",\"city\":\"Lauterecken\",\"color\":\"blau\"}"))
      .andExpect(status().is2xxSuccessful());
  }

}
