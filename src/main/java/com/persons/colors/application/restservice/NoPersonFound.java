package com.persons.colors.application.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoPersonFound extends RuntimeException {


  public NoPersonFound(String message) {
    super(message);
  }

}
