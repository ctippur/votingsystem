package com.rest.test.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;
import com.rest.test.lib.dynamodb.DocumentAPIItemCRUDExample;

/**
 * Vote
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-01-25T12:34:51.836-08:00")

public class Vote   {
  @JsonProperty("user_id")
  private String userId = null;

  /**
   * Gets or Sets vote
   */
  public enum VoteEnum {
    UP("up"),

    DOWN("down");

    private String value;

    VoteEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static VoteEnum fromValue(String text) {
      for (VoteEnum b : VoteEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("vote")
  private VoteEnum vote = null;

  @JsonProperty("media_id")
  private String mediaId = null;

  @JsonProperty("up")
  private String up = null;

  @JsonProperty("down")
  private String down = null;



  public Vote userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  //@ApiModelProperty(required = true, value = "")
  @NotNull


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public Vote vote(VoteEnum vote) {
    this.vote = vote;
    return this;
  }

  /**
   * Get vote
   * @return vote
   **/
  //@ApiModelProperty(required = true, value = "")
  @NotNull


  public VoteEnum getVote() {
    return vote;
  }

  public void setVote(VoteEnum vote) {
    this.vote = vote;
  }

  public Vote mediaId(String mediaId) {
    this.mediaId = mediaId;
    return this;
  }

  /**
   * Get mediaId
   * @return mediaId
   **/
  //@ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vote vote = (Vote) o;
    return Objects.equals(this.userId, vote.userId) &&
            Objects.equals(this.vote, vote.vote) &&
            Objects.equals(this.mediaId, vote.mediaId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, vote, mediaId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vote {\n");

    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    vote: ").append(toIndentedString(vote)).append("\n");
    sb.append("    mediaId: ").append(toIndentedString(mediaId)).append("\n");
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

  /*
   * Save vote
   */
  public Item save(String upDown){


    String id=this.mediaId;

    System.out.println(id);

    // Get vote item for the corresponding media id
    // public  void retrieveItem(String hashkeyName, String hashKeyValue, String projectionExpression ) {
    DocumentAPIItemCRUDExample ddb=new DocumentAPIItemCRUDExample("Vote");

    Item item = ddb.retrieveItem("MediaId", id, "MediaId,up,down");
    return item;
  }

  public Item get(){
    String id=this.mediaId;
    DocumentAPIItemCRUDExample ddb=new DocumentAPIItemCRUDExample("Vote");
    return (ddb.retrieveItem("MediaId", id, "MediaId,up,down"));
  }
  public Item update(){
    Item item = new Item()
            .withPrimaryKey("MediaId", this.mediaId)
            .withString("up", this.getUp())
            .withString("down", this.getDown());

    DocumentAPIItemCRUDExample ddb=new DocumentAPIItemCRUDExample("Vote");
    ddb.createItems(item);
    return item;
  }

  public List<Map<String, AttributeValue>> getAll(){
    DocumentAPIItemCRUDExample ddb=new DocumentAPIItemCRUDExample("Vote");
    return (ddb.getAll());
  }

}