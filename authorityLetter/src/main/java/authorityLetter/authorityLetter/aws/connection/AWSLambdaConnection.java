package authorityLetter.authorityLetter.aws.connection;


import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;

public class AWSLambdaConnection<T> {
    private AWSLambda awsLambdaClient;
    private final Class<T> serviceClass;

    public AWSLambdaConnection(Class<T> serviceClass, AWSConfig awsConfig) {
        ClientConfiguration configuration = new ClientConfiguration();
//        configuration.setMaxErrorRetry(3);
        configuration.setConnectionTimeout(900000);
        configuration.setSocketTimeout(900000);

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsConfig.getAccessKey(), awsConfig.getSecretKey());
        this.awsLambdaClient = (AWSLambda)((AWSLambdaClientBuilder)((AWSLambdaClientBuilder)AWSLambdaClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials))).withRegion(Regions.US_WEST_2)).withClientConfiguration(configuration).build();
        this.serviceClass = serviceClass;
    }

    public T getService() {
        return LambdaInvokerFactory.builder().lambdaClient(this.awsLambdaClient).build(this.serviceClass);
    }

    public T getService(String alias) {
        return LambdaInvokerFactory.builder().functionAlias(alias).lambdaClient(this.awsLambdaClient).build(this.serviceClass);
    }
}
