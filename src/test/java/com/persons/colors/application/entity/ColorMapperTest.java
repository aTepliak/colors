package com.persons.colors.application.entity;

import com.persons.colors.application.restservice.ColorMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColorMapperTest {

  @Test
  void mapsColorIdtoColorNameCorrectly() {
    //given
    //when
    String blue = new ColorMapper().fromColorId(1);

    //then
    assertThat(blue).isEqualTo("blau");
  }

  @Test
  void mapsInvalidColorIdtoColorNameCorrectly() {
    //given
    //when
    String noColor = new ColorMapper().fromColorId(0);

    //then
    assertThat(noColor).isEqualTo("keine");
  }

  @Test
  void mapsMissingColorIdtoColorNameCorrectly() {
    //given
    //when
    String noValidColor = new ColorMapper().fromColorId(32423);

    //then
    assertThat(noValidColor).isEqualTo("keine");
  }

}
