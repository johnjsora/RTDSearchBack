package authorityLetter.authorityLetter.services;

import authorityLetter.authorityLetter.models.directAuthorityRequest;
import authorityLetter.authorityLetter.models.responseAuthorityLetterLAmbda;
import com.amazonaws.services.lambda.invoke.LambdaFunction;

public interface getAuthoritiesLettersLambdaInt {
    @LambdaFunction(functionName = "RTDUsersSearch")
    responseAuthorityLetterLAmbda saveProcess(directAuthorityRequest input);
}
