package com.persons.colors.application.restservice;

import lombok.Data;

@Data
public class PersonDto {
  private final long id;
  private final String name;
  private final String lastName;
  private final String zipCode;
  private final String city;
  private final String color;
}
