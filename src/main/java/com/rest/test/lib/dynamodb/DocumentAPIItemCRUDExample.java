package com.rest.test.lib.dynamodb;

import java.io.IOException;
import java.util.*;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.rest.test.model.Media;

public class DocumentAPIItemCRUDExample {

    //private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()..withRegion(Regions.US_WEST_2).build();

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion(Regions.US_WEST_2)
            .build();

    private static DynamoDB dynamoDB = new DynamoDB(client);

    private String tableName ;
    /*
    public static void main(String[] args) throws IOException {

        createItems();

        retrieveItem();

        // Perform various updates.
        updateMultipleAttributes();
        updateAddNewAttribute();
        updateExistingAttributeConditionally();

        // Delete the item.
        deleteItem();

    }
    */

    public DocumentAPIItemCRUDExample(String tn){
        this.tableName=tn;
    }

    public  void createItems(Item item) {

        Table table = dynamoDB.getTable(this.tableName);
        try {
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
            table.putItem(item);

        }
        catch (Exception e) {
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());

        }
    }

    public  Item retrieveItem(String hashkeyName, String hashKeyValue, String projectionExpression ) {
        Table table = dynamoDB.getTable(this.tableName);

        try {

            //Item item = table.getItem("Id", 120, "Id, ISBN, Title, Authors", null);
            Item item=table.getItem(hashkeyName, hashKeyValue, projectionExpression, null);
            System.out.println("Printing item after retrieving it....");
            System.out.println(item.toJSONPretty());
            return item;

        }
        catch (Exception e) {
            System.err.println("GetItem failed.");
            System.err.println(e.getMessage());
        }

        return null;

    }


    public  List<Map<String,AttributeValue>> getAll() {

        List <Map<String,AttributeValue>> hMap = new ArrayList<>();
        ScanRequest scanRequest = new ScanRequest()
                .withTableName(this.tableName);

        ScanResult result = client.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()){
            hMap.add(item);
        }

        System.out.println(hMap.toString());
        return hMap;

    }

    public  void updateAddNewAttribute(String hashkeyName, String hashKeyValue, String updExpr, Map <String,String> nameMap, Map <String,Object> valueMap ) {
        Table table = dynamoDB.getTable(this.tableName);

        try {
            /*
            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", 121)
                    .withUpdateExpression("set #na = :val1").withNameMap(new NameMap().with("#na", "NewAttribute"))
                    .withValueMap(new ValueMap().withString(":val1", "Some value")).withReturnValues(ReturnValue.ALL_NEW);
            */

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(hashkeyName, hashKeyValue)
                    .withUpdateExpression(updExpr).withNameMap(nameMap)
                    .withValueMap(valueMap).withReturnValues(ReturnValue.ALL_NEW);


            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

            // Check the response.
            System.out.println("Printing item after adding new attribute...");
            System.out.println(outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Failed to add new attribute in " + this.tableName);
            System.err.println(e.getMessage());
        }
    }

    public  void updateMultipleAttributes() {

        Table table = dynamoDB.getTable(this.tableName);

        try {

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", 120)
                    .withUpdateExpression("add #a :val1 set #na=:val2")
                    .withNameMap(new NameMap().with("#a", "Authors").with("#na", "NewAttribute"))
                    .withValueMap(
                            new ValueMap().withStringSet(":val1", "Author YY", "Author ZZ").withString(":val2", "someValue"))
                    .withReturnValues(ReturnValue.ALL_NEW);

            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

            // Check the response.
            System.out.println("Printing item after multiple attribute update...");
            System.out.println(outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Failed to update multiple attributes in " + this.tableName);
            System.err.println(e.getMessage());

        }
    }

    public  void updateExistingAttributeConditionally() {

        Table table = dynamoDB.getTable(this.tableName);

        try {

            // Specify the desired price (25.00) and also the condition (price =
            // 20.00)

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", 120)
                    .withReturnValues(ReturnValue.ALL_NEW).withUpdateExpression("set #p = :val1")
                    .withConditionExpression("#p = :val2").withNameMap(new NameMap().with("#p", "Price"))
                    .withValueMap(new ValueMap().withNumber(":val1", 25).withNumber(":val2", 20));

            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

            // Check the response.
            System.out.println("Printing item after conditional update to new attribute...");
            System.out.println(outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Error updating item in " + this.tableName);
            System.err.println(e.getMessage());
        }
    }

    public  void deleteItem() {

        Table table = dynamoDB.getTable(this.tableName);

        try {

            DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("Id", 120)
                    .withConditionExpression("#ip = :val").withNameMap(new NameMap().with("#ip", "InPublication"))
                    .withValueMap(new ValueMap().withBoolean(":val", false)).withReturnValues(ReturnValue.ALL_OLD);

            DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);

            // Check the response.
            System.out.println("Printing item that was deleted...");
            System.out.println(outcome.getItem().toJSONPretty());

        }
        catch (Exception e) {
            System.err.println("Error deleting item in " + this.tableName);
            System.err.println(e.getMessage());
        }
    }
}
