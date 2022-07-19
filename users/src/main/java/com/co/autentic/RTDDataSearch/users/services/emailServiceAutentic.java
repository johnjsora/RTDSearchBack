package com.co.autentic.RTDDataSearch.users.services;

import com.co.autentic.RTDDataSearch.common.utils.GenUtils;
import com.co.autentic.RTDDataSearch.users.aws.AWSLambdaConnection;
import com.co.autentic.RTDDataSearch.users.aws.models.AWSConfig;
import com.co.autentic.RTDDataSearch.users.models.SendEmailRequest;
import com.co.autentic.RTDDataSearch.users.models.SendEmailResponse;

import java.util.Properties;

public class emailServiceAutentic {
    private final EmailServiceLambda emailServiceLambda;
    public emailServiceAutentic() {

        this(null);
    }
    public emailServiceAutentic(AWSConfig awsConfig) {

        Properties properties = GenUtils.loadPropertiesObj();

        AWSLambdaConnection<EmailServiceLambda> awsLambdaConnection = (awsConfig == null) ?
                new AWSLambdaConnection<>(EmailServiceLambda.class) :
                new AWSLambdaConnection<>(EmailServiceLambda.class, awsConfig, false);
        this.emailServiceLambda = awsLambdaConnection.getService("PROD");
    }

    public SendEmailResponse sendEmail(SendEmailRequest request) {
        return this.emailServiceLambda.sendEmail(request);
    }
}
