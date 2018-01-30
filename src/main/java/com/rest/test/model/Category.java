package com.rest.test.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets category
 */
public enum Category {
  
  ACTION("Action"),
  
  POPULAR("Popular"),
  
  HORROR("Horror"),
  
  DOCUMENTARY("Documentary"),
  
  COMEDY("Comedy"),
  
  DRAMA("Drama");

  private String value;

  Category(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static Category fromValue(String text) {
    for (Category b : Category.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}

