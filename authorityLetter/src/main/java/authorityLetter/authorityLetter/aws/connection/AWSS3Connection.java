package authorityLetter.authorityLetter.aws.connection;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
        ClientConfiguration configuration = new ClientConfiguration();
        configuration.setConnectionTimeout(900000);
        configuration.setSocketTimeout(900000);

        this.awsS3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(awsConfig.getRegion())
                .withClientConfiguration(configuration)
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

    public ArrayList<String> listFileByPrefix(String bucket, String prefix) {

        ArrayList<String> list = new ArrayList();

        ListObjectsV2Request req = new ListObjectsV2Request()
                .withBucketName(bucket).withPrefix(prefix).withMaxKeys(10);
        ListObjectsV2Result result;
        do {
            result = this.awsS3Client.listObjectsV2(req);

            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                list.add(objectSummary.getKey());
            }
            // If there are more than maxKeys keys in the bucket, get a continuation token
            // and list the next objects.
            String token = result.getNextContinuationToken();
            // System.out.println("Next Continuation Token: " + token);
            req.setContinuationToken(token);
        } while (result.isTruncated());

        return list;
    }

    public byte[] getFileBytesFromKey(String bucket, String key) {

        try {
            S3Object object = this.awsS3Client.getObject(new GetObjectRequest(bucket, key));
            InputStream objectData = object.getObjectContent();

            byte[] targetArray = IOUtils.toByteArray(objectData);
            objectData.close();

            return targetArray;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generatePresignedURL(String bucketName, String objectKey, int expirationTimeSeconds) {

        try {

            // Set the presigned URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * expirationTimeSeconds;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);

            URL url = this.awsS3Client.generatePresignedUrl(generatePresignedUrlRequest);

            return url.toString();

        } catch (SdkClientException e) {

            e.printStackTrace();
            return "";
        }
    }
    public AWSS3Connection(AWSConfig awsConfig) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey());
        this.awsS3Client = (AmazonS3) ((AmazonS3ClientBuilder) ((AmazonS3ClientBuilder) AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials))).withRegion(Regions.US_WEST_2)).build();
    }

    public void uploadFileBytes (String bucket, String key,byte[] fileBytes){

        System.out.println(fileBytes.length);
        System.out.println(fileBytes.toString());
        InputStream inputStream = new ByteArrayInputStream(fileBytes);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        PutObjectRequest objectRequest = new PutObjectRequest(bucket, key, inputStream, objectMetadata);

        // TODO Fix me!
        this.awsS3Client.putObject(objectRequest.withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
