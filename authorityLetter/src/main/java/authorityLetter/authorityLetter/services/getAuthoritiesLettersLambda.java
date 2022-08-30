package authorityLetter.authorityLetter.services;
import authorityLetter.authorityLetter.aws.connection.AWSConfig;
import authorityLetter.authorityLetter.aws.connection.AWSLambdaConnection;
import authorityLetter.authorityLetter.models.directAuthorityRequest;
import authorityLetter.authorityLetter.models.responseAuthorityLetterLAmbda;
import org.springframework.stereotype.Service;
@Service
public class getAuthoritiesLettersLambda {
    private final getAuthoritiesLettersLambdaInt lambdaFunction;

    public getAuthoritiesLettersLambda(AWSConfig awsConfig) {
        this.lambdaFunction = (getAuthoritiesLettersLambdaInt)(new AWSLambdaConnection(getAuthoritiesLettersLambdaInt.class, awsConfig)).getService();
    }

    public responseAuthorityLetterLAmbda getAuthorityLetter(directAuthorityRequest input) {
        return this.lambdaFunction.saveProcess(input);
    }
}
