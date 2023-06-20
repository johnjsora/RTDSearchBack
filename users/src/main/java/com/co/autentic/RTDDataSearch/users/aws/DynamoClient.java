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
import com.amazonaws.services.dynamodbv2.model.*;
import com.co.autentic.RTDDataSearch.users.aws.models.*;
import com.co.autentic.RTDDataSearch.users.models.ResponseListClient;
import com.co.autentic.RTDDataSearch.users.models.ResponseListProposal;

import java.util.*;

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

    public  List<urlAccessmodel> getURLAccessIndex(String keyValue) {
        List<urlAccessmodel> itemsListURLAccess = new ArrayList<>();
        try {

            QueryRequest request = new QueryRequest();
            request.setTableName(tableName);
            request.setIndexName("gsi_userDoc-index");
            request.setKeyConditionExpression("gsi_userDoc = :v_userDoc");


            Map<String, AttributeValue> queryConditions = new HashMap<>();
            queryConditions.put(":v_userDoc", new AttributeValue().withS(keyValue));


            request.setExpressionAttributeValues(queryConditions);

            Map<String, AttributeValue> lastEvaluatedKey = null;
            do {
                request.setExclusiveStartKey(lastEvaluatedKey);
                QueryResult result = this.dynamoClient.query(request);
                lastEvaluatedKey = result.getLastEvaluatedKey();
                for(int y=0; y<result.getItems().size();y++){
                    urlAccessmodel tempAcces = new urlAccessmodel();
                    tempAcces.setCaseNumber(result.getItems().get(y).get("caseNumber").getS());
                    tempAcces.setUserDoc(result.getItems().get(y).get("userDoc").getS());
                    itemsListURLAccess.add(tempAcces);
                }

            } while (lastEvaluatedKey != null && lastEvaluatedKey.size() != 0);

            return itemsListURLAccess;

        } catch (AmazonServiceException e) {

            System.err.println(e.getErrorMessage());
            System.exit(1);
            return itemsListURLAccess;

        }
    }

    public  presignermodel getDocumentsNames(String keyValue) {

        try {

            QueryRequest request = new QueryRequest();
            request.setTableName(tableName);
            //request.setIndexName("preSigner99");
            request.setKeyConditionExpression("caseNumber = :v_caseNumber");


            Map<String, AttributeValue> queryConditions = new HashMap<>();
            queryConditions.put(":v_caseNumber", new AttributeValue().withS(keyValue));


            request.setExpressionAttributeValues(queryConditions);

            Map<String, AttributeValue> lastEvaluatedKey = null;
            do {
                request.setExclusiveStartKey(lastEvaluatedKey);
                QueryResult result = this.dynamoClient.query(request);
                lastEvaluatedKey = result.getLastEvaluatedKey();
                for(int y=0; y<result.getItems().size();y++){
                    presignermodel tempAcces = new presignermodel();
                    for(int i=0; i< result.getItems().get(y).get("documents").getL().size();i++){
                        if(result.getItems().get(y).get("documents").getL().get(i).getM().get("doc_name").getS().toLowerCase().equals("carta poder")){
                            tempAcces.setDocumentName(result.getItems().get(y).get("documents").getL().get(i).getM().get("doc_name").getS());
                            tempAcces.setDocumentId(result.getItems().get(y).get("documents").getL().get(i).getM().get("id").getS());
                            tempAcces.setProccessId(keyValue);
                            return tempAcces;
                        }
                    }

                }
            } while (lastEvaluatedKey != null && lastEvaluatedKey.size() != 0);
            return new presignermodel();

        } catch (AmazonServiceException e) {

            System.err.println(e.getErrorMessage());
            System.exit(1);
            return new presignermodel();

        }
    }
    public signeddocsmodel getDocumentsSigneds(String keyValue, String idDoc) {

        try {

            QueryRequest request = new QueryRequest();
            request.setTableName(tableName);
            //request.setIndexName("preSigner99");
            request.setKeyConditionExpression("caseNumber = :v_caseNumber");


            Map<String, AttributeValue> queryConditions = new HashMap<>();
            queryConditions.put(":v_caseNumber", new AttributeValue().withS(keyValue));


            request.setExpressionAttributeValues(queryConditions);

            Map<String, AttributeValue> lastEvaluatedKey = null;
            do {
                request.setExclusiveStartKey(lastEvaluatedKey);
                QueryResult result = this.dynamoClient.query(request);
                lastEvaluatedKey = result.getLastEvaluatedKey();
                for(int y=0; y<result.getItems().size();y++){
                    signeddocsmodel tempAcces = new signeddocsmodel();
                    if(result.getItems().get(y).get("state").getS().toLowerCase().equals("firmado")){
                        tempAcces.setDateSigned(result.getItems().get(y).get("changeStateDate").getS());
                        tempAcces.setUrl(result.getItems().get(y).get("mapDocDig_signed").getM().get(idDoc).getM().get("URLDocumentoFirmadoAmazon__c").getS());
                        return tempAcces;
                    }
                }
            } while (lastEvaluatedKey != null && lastEvaluatedKey.size() != 0);
            return new signeddocsmodel();

        } catch (AmazonServiceException e) {

            System.err.println(e.getErrorMessage());
            System.exit(1);
            return new signeddocsmodel();

        }
    }


    public T getItemRange(String keyValue, String rangeKey) {

        try {
            DynamoDBMapperConfig mapperConfig = new DynamoDBMapperConfig.Builder()
                    .withConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT)
                    .build();

            DynamoDBMapper mapper = new DynamoDBMapper(this.dynamoClient);
            T item = mapper.load(this.typeParameterClass, keyValue,rangeKey,
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
                    ResponseList.add(new proposalmoldel(
                            item.get("idProp") == null? "N/A":item.get("idProp").getS(),
                            Long.parseLong(item.get("dateInserted").getN()),
                            item.get("sendBy") == null? "N/A": item.get("sendBy").getS(),
                            item.get("sendByEmail") == null? "N/A": item.get("sendByEmail").getS(),
                            item.get("status") == null? "N/A": item.get("status").getS(),
                            item.get("reference") == null? "N/A": item.get("reference").getS(),
                            item.get("amount")== null? 0 : Double.parseDouble(item.get("amount").getN()),
                            item.get("typeProposal")== null? "N/A":  item.get("typeProposal").getS(),
                            item.get("keyFile")== null? "N/A":  item.get("keyFile").getS(),
                            item.get("usersCount")== null? 0 :Integer.parseInt(item.get("usersCount").getN()),
                            item.get("keyFile")== null? false :item.get("haveInexists").getBOOL(),
                            item.get("description")== null? "N/A":  item.get("description").getS(),
                            item.get("bank")== null? "N/A":  item.get("bank").getS(),
                            item.get("userDocument") == null? "N/A": item.get("userDocument").getS(),
                            item.get("userDocumentType") == null? "N/A":item.get("userDocumentType").getS()));

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

    public ResponseListClient getClientsByEntity(String entity) {
        try {
            List<TransactionItem> ResponseList = new ArrayList<>();
            ResponseListClient Response = new ResponseListClient();

            Map<String, AttributeValue> lastKeyEvaluated = null;
            do {
                Condition scanFilterCondition = new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ.toString())
                        .withAttributeValueList(new AttributeValue().withS(entity));
                Map<String, Condition> conditions = new HashMap<>();
                conditions.put("Entity", scanFilterCondition);

                ScanRequest scanRequest = new ScanRequest()
                        .withTableName("rtd-Clients-New-Import")
                        .withScanFilter(conditions)
                        .withExclusiveStartKey(lastKeyEvaluated);

                ScanResult result = this.dynamoClient.scan(scanRequest);
                for (Map<String, AttributeValue> item : result.getItems()){
                    ResponseList.add(new TransactionItem(
                            item.get("DocumentcustomerId").getS(),
                            item.get("Entity").getS()));

                }
                lastKeyEvaluated = result.getLastEvaluatedKey();
                Response.setClients(ResponseList);
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
