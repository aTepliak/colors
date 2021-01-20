package com.persons.colors.application.restservice;

import com.persons.colors.application.appservice.PersonColorAppService;
import com.persons.colors.application.entity.Person;
import com.persons.colors.application.entity.PersonId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PersonColorRestService {

  @Autowired
  PersonColorAppService personColorAppService;


  @GetMapping("/persons")
  public List<PersonDto> retrieveAll() {
    return personColorAppService.retrieveAll().stream().map(this::toDto).collect(Collectors.toList());
  }

  @GetMapping("/persons/color/{colorId}")
  public List<PersonDto> retrieveAllPersonsByColor(@PathVariable String colorId) {
    return personColorAppService.retrieveAllPersonsByColor(Integer.parseInt(colorId)).stream().map(this::toDto).collect(Collectors.toList());
  }

  @GetMapping("/persons/{personId}")
  public PersonDto retrievePersonBy(@PathVariable String personId) {
    return personColorAppService.retrievePersonBy(new PersonId(Long.parseLong(personId))).map(this::toDto).orElseThrow(() -> new NoPersonFound("Person " +
      "with Id '" + personId + "' could not be found."));
  }

  @RequestMapping(value = "/person", method = RequestMethod.POST)
  @ResponseBody
  public void addNewPerson(@RequestBody PersonDto personDto) {
    personColorAppService.insertPerson(fromDto(personDto));
  }

  private Person fromDto(PersonDto personDto) {
    PersonId personId = personDto.getId() > 0 ? new PersonId(personDto.getId()) : new PersonId();
    return new Person(personId, personDto.getName(), personDto.getLastName(), personDto.getZipCode(), personDto.getCity(),
      ColorMapper.fromColorName(personDto.getColor()));
  }

  private PersonDto toDto(Person person) {
    return new PersonDto(person.getId().getPersonRawId(), person.getName(),
      person.getLastName(), person.getZipCode(), person.getCity(), ColorMapper.fromColorId(person.getColorCode()));
  }


}
