package com.co.autentic.RTDDataSearch.importData.services;

import com.co.autentic.RTDDataSearch.common.utils.GenUtils;
import com.co.autentic.RTDDataSearch.importData.aws.AWSS3Connection;
import com.co.autentic.RTDDataSearch.importData.aws.DynamoClient;
import com.co.autentic.RTDDataSearch.importData.aws.models.TransactionItem;
import com.co.autentic.RTDDataSearch.importData.models.s3FileInformation;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class getDataService {

    private String TableData ="";
    private String docConfig = "";
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy HH:mm:ss z");
    public getDataService(){
        Properties properties = GenUtils.loadPropertiesObj();
        TableData = properties.getProperty("aws.dynamo.tableData");
        docConfig = properties.getProperty("aws.dynamo.conficDoc");
    }
    public void getFileS3() throws IOException, ParseException {
        try{
            AWSS3Connection awss3Connection = new AWSS3Connection();
            String key = "Imports/database.csv";
            String bucket = "rtd-autentic-clientsintegration";
            s3FileInformation data = awss3Connection.getFileBytesFromKeyLast(bucket, key);
            TransactionItem config = getlastDade();
            Date LastDateImport = dateFormat.parse(config.getLastDateImport());

            if (LastDateImport.before(data.getLastModifiedDate()) && !data.getLastModifiedDate().equals(LastDateImport)){
                InputStream is = new ByteArrayInputStream(data.getFile());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line = null;
                while((line = br.readLine()) != null){
                    String[] values = line.split(",");
                    if(values.length < 3){
                        TransactionItem item = new TransactionItem(values[0],values[1],null);
                        TransactionItem itemExists = getData(values[0]);
                        if(itemExists == null || !itemExists.getDocumentType().equals(item.getDocumentType())){
                            setData(item);
                        }
                    }
                }
                config.setLastDateImport(dateFormat.format(data.getLastModifiedDate()));
                setData(config);
            }
            else
            {
                System.out.println("Completed without changes");
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
    public TransactionItem getData(String value){
        DynamoClient<TransactionItem> db = new DynamoClient<>(TableData, TransactionItem.class);
        TransactionItem TR = db.getItem(value);
        return TR;
    }
    public void setData(TransactionItem item){
        DynamoClient<TransactionItem> db = new DynamoClient<>(TableData, TransactionItem.class);
        db.addRow(item);
    }

    public TransactionItem getlastDade() throws ParseException {
        TransactionItem response = new TransactionItem();
        DynamoClient<TransactionItem> db = new DynamoClient<>(TableData, TransactionItem.class);
        response =db.getItem(docConfig);
        return response;
    }
}
