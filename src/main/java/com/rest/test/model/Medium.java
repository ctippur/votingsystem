package com.rest.test.model;

import java.util.HashMap;
import java.util.Objects;
import com.rest.test.model.Media;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.rest.test.lib.dynamodb.DocumentAPIItemCRUDExample;
import org.omg.CORBA.Object;

/**
 * Medium
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-25T12:34:51.836-08:00")

public class Medium extends ArrayList<Media>  {

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Medium {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  /**
   * Save a Medium
   *
   */
  public boolean save() {

    /*
    for (int i = 0; i < this.size(); i++) {
      System.out.println(this.get(i));
      Media m=new Media();

      // Media attributes
      m.setDescription("");
      m.setId("");
      Category c= Category.valueOf("");

      m.setCategory(c);
      m.setDescription("");
      m.save();



    }
    */

    return true;
  }
}

