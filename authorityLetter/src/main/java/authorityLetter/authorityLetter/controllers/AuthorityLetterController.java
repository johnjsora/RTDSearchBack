package authorityLetter.authorityLetter.controllers;

import authorityLetter.authorityLetter.models.Request;
import authorityLetter.authorityLetter.models.Response;
import authorityLetter.authorityLetter.services.executeprocess_AuthorityLetter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Response massivelyProcess(@RequestBody Request request) {

        Response resp = new Response();
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
                this.executeAsyncAuthorityLetter.setProcessAsyncAuthorityLetter(br,FileName);
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
        return req.getBaseFile() != null && !req.getBaseFile().equals("") && req.getFileName() != null && !req.getFileName().equals("") && req.getFileExtention() != null && !req.getFileExtention().equals("");
    }
}
