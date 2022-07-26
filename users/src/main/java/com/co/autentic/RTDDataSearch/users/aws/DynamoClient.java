package com.co.autentic.RTDDataSearch.users.aws;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.co.autentic.RTDDataSearch.users.aws.models.TransactionItem;
import com.co.autentic.RTDDataSearch.users.aws.models.proposalmoldel;
import com.co.autentic.RTDDataSearch.users.models.ResponseListProposal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DynamoClient<T> {
    private final AmazonDynamoDB dynamoClient;
    private final String tableName;
    private final Class<T> typeParameterClass;
    public DynamoClient(String tableName, Class<T> typeParameterClass) {

        this.tableName = tableName;
        this.typeParameterClass = typeParameterClass;

        AWSCredentials awsCredentials = (System.getenv("AWS_SESSION_TOKEN") != null) ?
                new BasicSessionCredentials(System.getenv("AWS_ACCESS_KEY_ID"), System.getenv("AWS_SECRET_ACCESS_KEY"), System.getenv("AWS_SESSION_TOKEN")) :
                new BasicAWSCredentials(System.getenv("AWS_ACCESS_KEY_ID"), System.getenv("AWS_SECRET_ACCESS_KEY"));

        this.dynamoClient = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(System.getenv("AWS_REGION"))
                .build();
    }
    public T getItem(String keyValue) {

        try {
            DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder()
                    .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                    .build();

            DynamoDBMapper mapper = new DynamoDBMapper(this.dynamoClient);
            T item = mapper.load(this.typeParameterClass, keyValue,
                    mapperConfig);

            return item;
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
            return null;
        }
    }

    public ResponseListProposal getAllItem(Map<String, AttributeValue> lastKeyEvaluated, int limit) {
        try {
            List<proposalmoldel> ResponseList = new ArrayList<>();
            ResponseListProposal Response = new ResponseListProposal();

                ScanRequest scanRequest = new ScanRequest()
                        .withTableName("rtd-Clients-Proposal")
                        .withExclusiveStartKey(lastKeyEvaluated)
                        .withLimit(limit);

                        ScanResult result = this.dynamoClient.scan(scanRequest);
                for (Map<String, AttributeValue> item : result.getItems()){
                    ResponseList.add(new proposalmoldel(item.get("idProp").getS(),
                            Long.parseLong(item.get("dateInserted").getN()),
                            item.get("sendBy").getS(),
                            item.get("sendByEmail").getS(),
                            item.get("status").getS(),
                            item.get("reference").getS(),
                            Double.parseDouble(item.get("amount").getN()),
                            item.get("typeProposal").getS(),
                            item.get("keyFile").getS(),
                            Integer.parseInt(item.get("usersCount").getN()),
                            item.get("haveInexists").getBOOL(),
                            item.get("description").getS(),
                            item.get("bank").getS(),
                            item.get("userDocument").getS(),
                            item.get("userDocumentType").getS()));

                }
                Response.setLastKeyEvaluated(result.getLastEvaluatedKey());
                Response.setProposals(ResponseList);

            return Response;

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
            return null;

        }
    }
    public ResponseListProposal  getAllItem() {
        try {
            List<proposalmoldel> ResponseList = new ArrayList<>();
            ResponseListProposal Response = new ResponseListProposal();

            Map<String, AttributeValue> lastKeyEvaluated = null;
            do {
                ScanRequest scanRequest = new ScanRequest()
                        .withTableName("rtd-Clients-Proposal")
                        .withExclusiveStartKey(lastKeyEvaluated);

                ScanResult result = this.dynamoClient.scan(scanRequest);
                for (Map<String, AttributeValue> item : result.getItems()){
                    ResponseList.add(new proposalmoldel(item.get("idProp").getS(),
                            Long.parseLong(item.get("dateInserted").getN()),
                            item.get("sendBy").getS(),
                            item.get("sendByEmail").getS(),
                            item.get("status") == null? "N/A": item.get("status").getS(),
                            item.get("reference") == null? "N/A": item.get("reference").getS(),
                            item.get("amount")== null? 0 : Double.parseDouble(item.get("amount").getN()),
                            item.get("typeProposal")== null? "N/A":  item.get("typeProposal").getS(),
                            item.get("keyFile")== null? "N/A":  item.get("keyFile").getS(),
                            item.get("usersCount")== null? 0 :Integer.parseInt(item.get("usersCount").getN()),
                            item.get("keyFile")== null? false :item.get("haveInexists").getBOOL(),
                            item.get("description")== null? "N/A":  item.get("description").getS(),
                            item.get("bank")== null? "N/A":  item.get("bank").getS(),
                            item.get("userDocument").getS(),
                            item.get("userDocumentType").getS()));

                }
                lastKeyEvaluated = result.getLastEvaluatedKey();
                Response.setProposals(ResponseList);
            } while (lastKeyEvaluated != null);
            return Response;

        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
            return null;

        }
    }
    public boolean updateRow(T item) {

        try {
            DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder()
                    .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                    .build();

            DynamoDBMapper mapper = new DynamoDBMapper(this.dynamoClient);
            mapper.save(item, mapperConfig);
            // this.dynamoClient.updateItem(this.tableName, item_key, updated_values);

        } catch (ResourceNotFoundException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (AmazonServiceException ase) {
            System.err.println(ase.getMessage());
            System.err.println("Could not complete operation");
            System.err.println("Error Message:  " + ase.getMessage());
            System.err.println("HTTP Status:    " + ase.getStatusCode());
            System.err.println("AWS Error Code: " + ase.getErrorCode());
            System.err.println("Error Type:     " + ase.getErrorType());
            System.err.println("Request ID:     " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
            System.err.println("Internal error occurred communicating with DynamoDB");
            System.out.println("Error Message:  " + ace.getMessage());
        }
        return true;
    }
    public boolean deleteRow(T item) {

        try {
            DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder()
                    .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                    .build();

            DynamoDBMapper mapper = new DynamoDBMapper(this.dynamoClient);
            mapper.delete(item, mapperConfig);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
            return false;
        }
        return true;
    }
    public boolean addRow(T item) {

        try {
            DynamoDBMapper mapper = new DynamoDBMapper(this.dynamoClient);
            mapper.save(item);
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", this.tableName);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            return false;
        } catch (AmazonServiceException ase) {
            System.err.println(ase.getMessage());
            System.err.println("Could not complete operation");
            System.err.println("Error Message:  " + ase.getMessage());
            System.err.println("HTTP Status:    " + ase.getStatusCode());
            System.err.println("AWS Error Code: " + ase.getErrorCode());
            System.err.println("Error Type:     " + ase.getErrorType());
            System.err.println("Request ID:     " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
            System.err.println("Internal error occurred communicating with DynamoDB");
            System.out.println("Error Message:  " + ace.getMessage());
        }

        return true;
    }
}
