package com.persons.colors.application.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
  @EmbeddedId
  private PersonId id;
  private String name;
  private String lastName;
  private String zipCode;
  private String city;
  private int colorCode;
}
