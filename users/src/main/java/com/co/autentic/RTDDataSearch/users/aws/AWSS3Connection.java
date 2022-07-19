package com.co.autentic.RTDDataSearch.users.aws;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;


import com.co.autentic.RTDDataSearch.users.aws.models.AWSConfig;
import com.co.autentic.RTDDataSearch.users.models.ResponseDocuments;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class AWSS3Connection {

    private final AmazonS3 awsS3Client;

    public AWSS3Connection() {

        this(new AWSConfig(
                        System.getenv("AWS_ACCESS_KEY_ID"),
                        System.getenv("AWS_SECRET_ACCESS_KEY"),
                        System.getenv("AWS_REGION")),
                true);
    }

    public AWSS3Connection(AWSConfig awsConfig, boolean useSessionToken) {

        AWSCredentials awsCredentials = (useSessionToken && System.getenv("AWS_SESSION_TOKEN") != null) ?
                new BasicSessionCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey(), System.getenv("AWS_SESSION_TOKEN")) :
                new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey());

        this.awsS3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(awsConfig.getRegion())
                .build();
    }

    public void deleteKey(String bucket, String key) {

        this.awsS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
    }

    public void deleteMultipleKey(String bucket, ArrayList<String> key) {

        ArrayList<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<DeleteObjectsRequest.KeyVersion>();
        key.forEach((keyString) -> {
            keys.add(new DeleteObjectsRequest.KeyVersion(keyString));
        });
        DeleteObjectsRequest multipleKeys = new DeleteObjectsRequest(bucket).withKeys(keys).withQuiet(false);
        this.awsS3Client.deleteObjects(multipleKeys);
    }

    public void listBuckets() {
        System.out.println("Your Amazon S3 buckets:");

        List<Bucket> buckets = this.awsS3Client.listBuckets();

        for (Bucket b : buckets) {
            System.out.println("* " + b.getName());
        }
    }

    public byte[] getFileBytesFromKey(String bucket, String key) {

        try {
            S3Object object = this.awsS3Client.getObject(new GetObjectRequest(bucket, key));
            InputStream objectData = object.getObjectContent();
            ObjectMetadata metadata = object.getObjectMetadata();
            byte[] targetArray = IOUtils.toByteArray(objectData);
            objectData.close();

            return targetArray;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResponseDocuments getFileBase64FromKey(String bucket, String key) {

        try {
            ResponseDocuments  response = new ResponseDocuments();
            S3Object object = this.awsS3Client.getObject(new GetObjectRequest(bucket, key));
            InputStream objectData = object.getObjectContent();
            response.setDocumentBase(Base64.getEncoder().encodeToString(IOUtils.toByteArray(objectData)));
            objectData.close();

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void uploadFileBytes(String bucket, String key, byte[] fileBytes) {

        System.out.println(fileBytes.length);
        System.out.println(fileBytes.toString());
        InputStream inputStream = new ByteArrayInputStream(fileBytes);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest objectRequest = new PutObjectRequest(bucket, key, inputStream, objectMetadata);

        // TODO Fix me!
        this.awsS3Client.putObject(objectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public void createKeyFromByteArray(String bucket, String key, byte[] byteArray) {

        InputStream inputStream = new ByteArrayInputStream(byteArray);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest objectRequest = new PutObjectRequest(bucket, key, inputStream, objectMetadata);

        this.awsS3Client.putObject(objectRequest.withCannedAcl(CannedAccessControlList.Private));
    }
}

