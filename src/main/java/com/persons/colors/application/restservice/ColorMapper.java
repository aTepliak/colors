package com.persons.colors.application.restservice;

public class ColorMapper {
  public static String fromColorId(int colorCode) {
    Colors color = Colors.fromId(colorCode);
    return color.name;
  }

  public static int fromColorName(String name) {
    Colors color = Colors.fromColorName(name);
    return color.color_id;
  }

  enum Colors {
    BLUE(1, "blau"),
    GREEN(2, "grün"),
    VIOLET(3, "violett"),
    RED(4, "rot"),
    YELLOW(5, "gelb"),
    TURQUOISE(6, "türkis"),
    WHITE(7, "weiß"),
    NONE(0, "keine");

    int color_id;
    String name;

    Colors(int color_id, String name) {
      this.color_id = color_id;
      this.name = name;
    }

    static Colors fromId(int colorCode) {
      if (colorCode <= 0) return Colors.NONE;
      for (Colors colorValue : Colors.values()) {
        if (colorValue.color_id == colorCode)
          return colorValue;
      }
      return Colors.NONE;
    }

    static Colors fromColorName(String name) {
      if (name == null) return Colors.NONE;
      for (Colors colorValue : Colors.values()) {
        if (colorValue.name.equals(name))
          return colorValue;
      }
      return Colors.NONE;
    }

    public String getName() {
      return name;
    }
  }
}
