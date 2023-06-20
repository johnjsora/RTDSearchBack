package authorityLetter.authorityLetter.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ExecutorServiceConfig {


    @Bean(name = {"getAuthorityLetter_Executor"})
    public TaskExecutor PoolTaskExecutorAWS() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setThreadNamePrefix("getAuthorityLetter_Executor");

        executor.setCorePoolSize(10000);
        executor.setMaxPoolSize(10000);
        executor.setKeepAliveSeconds(130);
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }

}