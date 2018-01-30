package com.rest.test.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import com.rest.test.model.Category;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.rest.test.lib.dynamodb.DocumentAPIItemCRUDExample;

/**
 * Media
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-25T12:34:51.836-08:00")

public class Media   {

  @JsonProperty("up")
  private String up = null;

  @JsonProperty("down")
  private String down = null;

  @JsonProperty("id")
  private String id = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("category")
  private String category = null;

  @JsonProperty("type")
  private String type = null;

  public Media id(String id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  //@ApiModelProperty(value = "")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUp() {
    return up;
  }

  public void setUp(String up) {
    this.up = up;
  }

  public String getDown() {
    return down;
  }

  public void setDown(String down) {
    this.down = down;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Media description(String description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  //@ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Media category(String  category) {
    this.category = category;
    return this;
  }

   /**
   * Get category
   * @return category
  **/
  //@ApiModelProperty(value = "")

  @Valid

  public String  getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Media media = (Media) o;
    return Objects.equals(this.id, media.id) &&
        Objects.equals(this.description, media.description) &&
        Objects.equals(this.category, media.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, category);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Media {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
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

  public Boolean save(){
    DocumentAPIItemCRUDExample ddb=new DocumentAPIItemCRUDExample("Media");
    /*
            item.withPrimaryKey("Id", 120).withString("Title", "Book 120 Title")
                    .withString("ISBN", "120-1111111111")
                    .withStringSet("Authors", new HashSet<String>(Arrays.asList("Author12", "Author22")))
                    .withNumber("Price", 20).withString("Dimensions", "8.5x11.0x.75").withNumber("PageCount", 500)
                    .withBoolean("InPublication", false).withString("ProductCategory", "Book");
            table.putItem(item);

            item = new Item().withPrimaryKey("Id", 121).withString("Title", "Book 121 Title")
                    .withString("ISBN", "121-1111111111")
                    .withStringSet("Authors", new HashSet<String>(Arrays.asList("Author21", "Author 22")))
                    .withNumber("Price", 20).withString("Dimensions", "8.5x11.0x.75").withNumber("PageCount", 500)
                    .withBoolean("InPublication", true).withString("ProductCategory", "Book");
     */
    category=this.category;
    description=this.description;
    id=this.id;
    type=this.type;

    Item item = new Item()
            .withPrimaryKey("MediaId", id)
            .withString("category", category)
            .withString("description", description)
            .withString("type", type);

    ddb.createItems(item);
    return true;
  }

  public List<Map<String, AttributeValue>> get(){
    DocumentAPIItemCRUDExample ddb=new DocumentAPIItemCRUDExample("Media");
    return (ddb.getAll());
  }
}

