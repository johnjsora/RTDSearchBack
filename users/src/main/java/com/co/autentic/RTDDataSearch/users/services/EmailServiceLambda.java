package com.co.autentic.RTDDataSearch.users.services;

import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.co.autentic.RTDDataSearch.users.models.SendEmailRequest;
import com.co.autentic.RTDDataSearch.users.models.SendEmailResponse;

public interface EmailServiceLambda {

    @LambdaFunction(functionName = "AS-WC-emailService")
    SendEmailResponse sendEmail(SendEmailRequest request);
}