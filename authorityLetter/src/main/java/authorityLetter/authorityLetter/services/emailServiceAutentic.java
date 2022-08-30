package authorityLetter.authorityLetter.services;



import authorityLetter.authorityLetter.aws.connection.AWSConfig;
import authorityLetter.authorityLetter.aws.connection.AWSLambdaConnection;
import authorityLetter.authorityLetter.models.SendEmailRequest;
import authorityLetter.authorityLetter.models.SendEmailResponse;
import org.springframework.stereotype.Service;

@Service
public class emailServiceAutentic {
    private final EmailServiceLambda emailServiceLambda;
    public emailServiceAutentic(AWSConfig awsConfig) {
        this.emailServiceLambda = (EmailServiceLambda)(new AWSLambdaConnection(EmailServiceLambda.class, awsConfig)).getService("PROD");
    }

    public SendEmailResponse sendEmail(SendEmailRequest request) {
        return this.emailServiceLambda.sendEmail(request);
    }
}
