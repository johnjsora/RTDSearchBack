package authorityLetter.authorityLetter.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
@Service
@Async("getAuthorityLetter_Executor")
public class executeprocess_AuthorityLetter {
        public void setProcessAsyncAuthorityLetter(BufferedReader br, String FileName){
            try{
                // Use for asyn Test
                // Thread.sleep(20000);
                String line = null;
                while((line = br.readLine()) != null){

                    System.out.println(line);
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }

        }
}
