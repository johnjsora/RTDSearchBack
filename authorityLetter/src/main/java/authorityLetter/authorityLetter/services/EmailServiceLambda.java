package authorityLetter.authorityLetter.services;

import authorityLetter.authorityLetter.models.SendEmailRequest;
import authorityLetter.authorityLetter.models.SendEmailResponse;
import com.amazonaws.services.lambda.invoke.LambdaFunction;


public interface EmailServiceLambda {

    @LambdaFunction(functionName = "AS-WC-emailService")
    SendEmailResponse sendEmail(SendEmailRequest request);
}