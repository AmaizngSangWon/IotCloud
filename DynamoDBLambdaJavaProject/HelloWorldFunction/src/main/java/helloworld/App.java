package helloworld;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class App implements RequestHandler<Noise, String> {
    private DynamoDB dynamoDb;
    private String TABLE_NAME = "Noise";
    private String REGION = "ap-northeast-2";

    @Override
    public String handleRequest(Noise input, Context context) {
        this.initDynamoDbClient();

        putData(input);
        return "Saved Successfully!!";
    }

    private void initDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION).build();
        this.dynamoDb = new DynamoDB(client);
    }

    private PutItemOutcome putData(Person person)
            throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable(TABLE_NAME)
                .putItem(
                        new PutItemSpec().withItem(new Item()
                                .withPrimaryKey("floor",Noise.floor)
                                .withString("Roomnum", Noise.roomnum)
                                .withInt("db", Noise.db)));
    }
}

class Noise {
    public String floor;
    public String roomnum;
    public int db;
}