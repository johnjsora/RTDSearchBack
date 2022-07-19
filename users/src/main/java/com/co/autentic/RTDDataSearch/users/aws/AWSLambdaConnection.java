package com.co.autentic.RTDDataSearch.users.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.co.autentic.RTDDataSearch.users.aws.models.AWSConfig;

public class AWSLambdaConnection<T> {
    private final AWSLambda awsLambdaClient;
    private final Class<T> serviceClass;

    public AWSLambdaConnection(Class<T> serviceClass) {

        this(serviceClass, new AWSConfig(
                        System.getenv("AWS_ACCESS_KEY_ID"),
                        System.getenv("AWS_SECRET_ACCESS_KEY"),
                        System.getenv("AWS_REGION")),
                true);
    }

    public AWSLambdaConnection(Class<T> serviceClass, AWSConfig awsConfig, boolean useSessionToken) {

        AWSCredentials awsCredentials = (useSessionToken && System.getenv("AWS_SESSION_TOKEN") != null) ?
                new BasicSessionCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey(), System.getenv("AWS_SESSION_TOKEN")) :
                new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey());

        this.awsLambdaClient = AWSLambdaClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(awsConfig.getRegion())
                .build();

        this.serviceClass = serviceClass;
    }

    public T getService() {

        return LambdaInvokerFactory.builder()
                .lambdaClient(awsLambdaClient)
                .build(serviceClass);
    }

    public T getService(String alias) {

        return LambdaInvokerFactory.builder()
                .functionAlias(alias)
                .lambdaClient(awsLambdaClient)
                .build(serviceClass);
    }
}
