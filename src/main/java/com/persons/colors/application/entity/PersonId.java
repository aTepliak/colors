package com.persons.colors.application.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Embeddable
public class PersonId implements Serializable {
  private static final AtomicLong sequence = new AtomicLong(0L);
  private long personRawId;

  public PersonId() {
    this.personRawId = sequence.incrementAndGet();
  }

  public PersonId(long personRawId) {
    this.personRawId = personRawId;
  }
}
