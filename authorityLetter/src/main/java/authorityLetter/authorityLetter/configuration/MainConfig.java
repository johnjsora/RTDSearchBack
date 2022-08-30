package authorityLetter.authorityLetter.configuration;

import authorityLetter.authorityLetter.aws.connection.AWSConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
@Configuration
public class MainConfig {

    @Bean
    public AWSConfig awsConfig(Environment env) {
        return new AWSConfig(env.getProperty("aws.credentials.accessKey"), env.getProperty("aws.credentials.secretKey"),env.getProperty("aws.credentials.region"));
        //return new AWSConfig(System.getenv("AWS_ACCESS_KEY_ID"), System.getenv("AWS_SECRET_ACCESS_KEY"),System.getenv("AWS_REGION"));
    }
}
