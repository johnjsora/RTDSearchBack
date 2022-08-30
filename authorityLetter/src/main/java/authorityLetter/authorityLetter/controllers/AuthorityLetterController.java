package authorityLetter.authorityLetter.controllers;

import authorityLetter.authorityLetter.models.Request;
import authorityLetter.authorityLetter.models.Response;
import authorityLetter.authorityLetter.security.SecurityUtils;
import authorityLetter.authorityLetter.services.executeprocess_AuthorityLetter;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

@RestController
@RequestMapping("/v1/authority-letter")
public class AuthorityLetterController {

    private executeprocess_AuthorityLetter executeAsyncAuthorityLetter;

    public AuthorityLetterController(executeprocess_AuthorityLetter executeAsyncAuthorityLetter){
        this.executeAsyncAuthorityLetter = executeAsyncAuthorityLetter;
    }
    @PostMapping(path = "/",
            consumes = "application/json",
            produces = "application/json")
    public Response massivelyProcess(@RequestBody Request request,  @RequestHeader("x-api-key") String key) {
        Response resp = new Response();
        if(key==null || key.equals("")){
            resp.setOperationCode(401);
            resp.setOperationMsg("Not Authorized");
            return resp;
        }
        else{
            try{
                SecurityUtils Sec = new SecurityUtils();
                key = Sec.decrypt(key);
                if(key != null && !key.equals("nZr4u7x!z%C*F-JaNdRgUkXp2s5v8y/B?D(G+KbPeShVmYq3t6w9z$C&F)H@McQfTjWnZr4u7x!A%D*G-KaNdRgUkXp2s5v8y/B?E(H+MbQeShVmYq3t6w9z$C&F)J@N")){
                    resp.setOperationCode(401);
                    resp.setOperationMsg("Not Authorized");
                    return resp;
                }
            }
            catch (Exception ex){
                resp.setOperationCode(401);
                resp.setOperationMsg("Not Authorized");
                return resp;
            }

        }


        try{
            resp.setOperationCode(200);
            byte[] file= null;
            String FileName = "";
            //Validate File structure
            if (validateinfoFile(request)){
                //readFile
                file = Base64.getDecoder().decode(request.getBaseFile());
                FileName = request.getFileName() + '.' + request.getFileExtention();
                InputStream is = new ByteArrayInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                //Execute process async
                this.executeAsyncAuthorityLetter.setProcessAsyncAuthorityLetter(br,FileName, request.getEmailSended());
                resp.setOperationMsg("Requerimiento Finalizado");
            }
            else{
                resp.setOperationCode(500);
                resp.setOperationMsg("Faltan datos en el requerimietno.");
            }

        }
        catch (Exception ex){
            resp.setOperationCode(500);
            resp.setOperationMsg(ex.getMessage());
            System.out.println(ex.toString());
        }

        return resp;
    }

    public boolean validateinfoFile(Request req){
        return req.getBaseFile() != null && !req.getBaseFile().equals("") && req.getFileName() != null && !req.getFileName().equals("") && req.getFileExtention() != null && !req.getFileExtention().equals("") && req.getEmailSended() != null && !req.getEmailSended().equals("");
    }
}
