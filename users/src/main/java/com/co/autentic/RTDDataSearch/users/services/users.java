package com.co.autentic.RTDDataSearch.users.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.co.autentic.RTDDataSearch.common.utils.GenUtils;
import com.co.autentic.RTDDataSearch.users.aws.AWSS3Connection;
import com.co.autentic.RTDDataSearch.users.aws.DynamoClient;
import com.co.autentic.RTDDataSearch.users.aws.models.*;
import com.co.autentic.RTDDataSearch.users.models.*;


import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class users {
    private String TableData ="";
    private String TableDataProposal ="";
    private String TableDataTransaction ="";
    private final emailServiceAutentic emailService;
    private String docConfig;
    public users(){
        Properties properties = GenUtils.loadPropertiesObj();
        TableData = properties.getProperty("aws.dynamo.tableData");
        TableDataTransaction = properties.getProperty("aws.dynamo.tableDataTransaction");
        TableDataProposal = properties.getProperty("aws.dynamo.tableDataProposal");
        this.emailService = new emailServiceAutentic();
        docConfig = properties.getProperty("aws.dynamo.conficDoc");
    }

    public ResponseUserModel getUsers (String email){
        ResponseUserModel Resp = new ResponseUserModel();
        try{
            DynamoClient<usermodel> db = new DynamoClient<>(TableData, usermodel.class);
            Resp.setOperationContent(db.getItem(email));
            if(Resp.getOperationContent() == null){
                Resp.setOperationCode(402);
                Resp.setOperationMessage("No se encuentra el usuario.");
                return Resp;
            }
            Resp.setOperationCode(200);
            Resp.setOperationMessage("ok.");
            return Resp;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            Resp.setOperationCode(500);
            Resp.setOperationMessage(ex.getMessage());
            return Resp;
        }
    }

    public ResponseDocuments getDocuments(String fileKey){
        ResponseDocuments data = new ResponseDocuments();
        try{
            AWSS3Connection awss3Connection = new AWSS3Connection();
            String bucket = "rtd-autentic-clientsintegration";
            data = awss3Connection.getFileBase64FromKey(bucket, fileKey);
            String[] namedivide = fileKey.split("/");
            String[] nameFinal = namedivide[namedivide.length -1].split("\\.");
            data.setOperationCode(200);
            data.setOperationMessage("ok");
            data.setDocumentName(nameFinal[0]);
            data.setDocumentExtention(nameFinal[1]);
            return data;
        }
        catch (Exception ex){
            data.setOperationCode(500);
            data.setOperationMessage(ex.getMessage());
            return data;
        }

    }

    public ResponseDocuments getAuthorityLetter(String documentId){
        ResponseDocuments data = new ResponseDocuments();
        try{
            //find process ids
            DynamoClient<TransactionItem> db = new DynamoClient<>("urlAccess99", TransactionItem.class);
            List<urlAccessmodel> resplistIds = db.getURLAccessIndex(documentId);
            List<presignermodel> names = new ArrayList<>();
            if(resplistIds.size() > 0){
                for (urlAccessmodel resplistId : resplistIds) {
                    //find presigner
                    db = new DynamoClient<>("preSigner99", TransactionItem.class);
                    presignermodel pre = db.getDocumentsNames(resplistId.getCaseNumber());
                    if (pre.getDocumentName() != null) {
                        names.add(pre);
                    }
                }
                if (names.size()>0){
                    //find signeddocs
                    signeddocsmodel signedFinal = null;
                    for(presignermodel pre:names){
                        db = new DynamoClient<>("signedDocs98", TransactionItem.class);
                        signeddocsmodel tempSigned = db.getDocumentsSigneds(pre.getProccessId(),pre.getDocumentId());
                        if(tempSigned.getDateSigned() != null && tempSigned.getUrl() != null){
                            if(signedFinal == null){
                                signedFinal = tempSigned;
                            }
                            else{
                                try{
                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssssss");
                                    Date date= df.parse(signedFinal.getDateSigned()
                                            .replace("T"," ")
                                            .replace("-05:00",""));
                                    long epoch1 = date.getTime();
                                    date = df.parse(tempSigned.getDateSigned()
                                            .replace("T"," ")
                                            .replace("-05:00",""));

                                    long epoch2 = date.getTime();
                                    if(epoch1 < epoch2){
                                        signedFinal = tempSigned;
                                    }
                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }

                            }
                        }

                    }
                    if(signedFinal.getUrl() != null){
                        data.setOperationCode(200);
                        data.setOperationMessage("Find URL");
                        data.setDocumentBase(signedFinal.getUrl());
                        data.setDocumentExtention("URL");
                        data.setDocumentName("Carta Poder");
                        return data;
                    }
                    else{
                        data.setOperationCode(400);
                        data.setOperationMessage("No se encuentran Procesos");
                    }

                }
                else{
                    data.setOperationCode(400);
                    data.setOperationMessage("No se encuentran Procesos");
                }
            }
            else{
                data.setOperationCode(400);
                data.setOperationMessage("No se encuentran Procesos");
            }

            return data;
        }
        catch (Exception ex){
            data.setOperationCode(500);
            data.setOperationMessage(ex.getMessage());
            return data;
        }

    }

    public UserExistsResponse verifyByCC(UserExistsRequestModel request){
        UserExistsResponse resp = new UserExistsResponse();
        try{
            DynamoClient<TransactionItem> db = new DynamoClient<>(TableDataTransaction, TransactionItem.class);
            TransactionItem TR = db.getItemRange(request.getUserId(), request.getEntity());
            if(TR != null){
                resp.setOperationCode(200);
                resp.setOperationMessage("Ok");
                resp.setExists(TR.getEntity().equals(request.getEntity()));
            }
            else{
                resp.setOperationCode(404);
                resp.setOperationMessage("No existe");
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
           resp.setOperationCode(500);
           resp.setOperationMessage(ex.getMessage());
        }
        return resp;
    }

    public ResponseProposal setProposal (UserExistsRequestModel request){
        ResponseProposal resp = new ResponseProposal();
        try{
            String uuid = UUID.randomUUID().toString().split("-")[0];
            String key="N/A";
            String bucket="";
            byte[] file = null;
            String FileName="N/A";
            //sendFiles
            if (validateinfoFile(request)){
                key = "Files/" + request.getSendByEmail() + '/' + uuid + '/' + request.getFileName() + '.' + request.getFileExtention();
                FileName = request.getFileName() + '.' + request.getFileExtention();
                bucket = "rtd-autentic-clientsintegration";
                file = Base64.getDecoder().decode(request.getFileBase64());
                AWSS3Connection awss3Connection = new AWSS3Connection();
                awss3Connection.createKeyFromByteArray(bucket,key,file);
            }

            Date currentDate = Calendar.getInstance(TimeZone.getTimeZone("America/Bogota")).getTime();
            long epoch = currentDate.getTime();

            proposalmoldel proposal = new proposalmoldel(uuid,epoch,request.getSendBy(),request.getSendByEmail(),"Active",request.getReference(),request.getAmount(),request.getTypeProposal(),key,1, false,request.getDescription(), request.getBank(), request.getUserId(), request.getEntity());
            setDataProposal(proposal);

            resp.setOperationCode(200);
            resp.setOperationMessage("ok");
            resp.setProposal(proposal);
            List<AttachedFileModel> files = new ArrayList<>();
            if (FileName != ""){
                files.add(new AttachedFileModel(file,FileName));
            }
            else{
                files = null;
            }
            sendEmailProposal(resp,files);
        }
        catch (Exception ex){
            resp.setOperationCode(500);
            System.out.println(ex.toString());
            resp.setOperationMessage(ex.getMessage());
        }
        return resp;
    }
    public ResponseProposal setProposalMulti (UserExistsRequestModel request){
        ResponseProposal resp = new ResponseProposal();
        try{
            String uuid = UUID.randomUUID().toString().split("-")[0];
            String key="";
            String bucket="";
            byte[] file= null;
            byte[] bytesFinal = null;
            String FileName = "";
            List<proposalmoldel> emailProposals = new ArrayList<>();
            Date currentDate = Calendar.getInstance(TimeZone.getTimeZone("America/Bogota")).getTime();
            long epoch = currentDate.getTime();
            //sendFiles
            if (validateinfoFile(request)){
                key = "Files/" + request.getSendByEmail() + '/' + uuid + '/' + request.getFileName() + '.' + request.getFileExtention();
                bucket = "rtd-autentic-clientsintegration";
                file = Base64.getDecoder().decode(request.getFileBase64());
                FileName = request.getFileName() + '.' + request.getFileExtention();
                //Read CVS
                InputStream is = new ByteArrayInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line = null;
                List<String[]>  newList = new ArrayList<>();
                double totalAmount = 0;
                int usercount = 0;
                boolean inexists = false;
                while((line = br.readLine()) != null){
                    if(usercount > 0){
                        String[] values = line.split(",");
                        if (values.length < 2){
                            resp.setOperationCode(403);
                            resp.setOperationMessage("Información incompleta en la línea: " + (usercount + 1));
                            return resp;
                        }
                        UserExistsRequestModel userExists = new UserExistsRequestModel();
                        userExists.setUserId(values[0]);
                        userExists.setEntity(values[1]);
                        UserExistsResponse responseExists = verifyByCC(userExists);
                        try{
                            totalAmount += Double.parseDouble(values[2]);
                        }
                        catch(Exception e){
                            resp.setOperationCode(404);
                            resp.setOperationMessage("El monto debe ser un valor numérico, linea: " + (usercount + 1));
                            return resp;
                        }
                        double amount = 0;
                        String description = "";
                        String Reference = "";
                        String bank = "";
                        if (responseExists.isExists()) {
                            uuid = UUID.randomUUID().toString().split("-")[0];

                            switch (values.length){
                                case 3:
                                    amount = Double.parseDouble(values[2]);
                                    break;
                                case 4:
                                    description = values[3];
                                    amount = Double.parseDouble(values[2]);
                                    break;
                                case 5:
                                    Reference = values[4];
                                    description = values[3];
                                    amount = Double.parseDouble(values[2]);
                                    break;
                                case 6:
                                    bank = values[5];
                                    Reference = values[4];
                                    description = values[3];
                                    amount = Double.parseDouble(values[2]);
                                    break;
                            }

                            newList.add(new String[] {values[0] , values[1] ,Double.toString(amount),description ,Reference ,bank , "Usuario existente"});
                            proposalmoldel proposal = new proposalmoldel(uuid,epoch,request.getSendBy(),request.getSendByEmail(),"Active",Reference,amount,request.getTypeProposal(),key, 1,inexists, description ,bank ,values[0] ,values[1] );
                            emailProposals.add(proposal);
                            setDataProposal(proposal);
                        } else if (!(responseExists.isExists())) {
                            newList.add(new String[] {values[0] , values[1] , Double.toString(amount)  ,description ,Reference ,bank , "El Usuario no existe en nuestra base de datos"});
                            inexists = true;
                        }

                    }
                    usercount++;
                }
                String tempDir = System.getProperty("java.io.tmpdir");

                File csvOutputFile = new File(tempDir + '/' + request.getFileName() + '.' + request.getFileExtention());
                try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                    newList.stream()
                            .map(this::convertToCSV)
                            .forEach(pw::println);
                }
                FileInputStream fis = new FileInputStream(csvOutputFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[2048];
                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
                        bos.write(buf, 0, readNum);
                    }
                bytesFinal = bos.toByteArray();
                bos.close();
                fis.close();

                AWSS3Connection awss3Connection = new AWSS3Connection();
                awss3Connection.createKeyFromByteArray(bucket,key,bytesFinal);
                csvOutputFile.deleteOnExit();                     //returns Boolean value

                resp.setOperationCode(200);
                resp.setOperationMessage("ok");
                resp.setBase64FileCSV(Base64.getEncoder().encodeToString(bytesFinal));

                resp.setProposal(null);
                List<AttachedFileModel> files = new ArrayList<>();
                if (FileName != ""){
                    files.add(new AttachedFileModel(bytesFinal,FileName));
                }
                else{
                    files = null;
                }
                sendEmailMultiple(emailProposals,files);
            }
            else{
                resp.setOperationCode(402);
                resp.setOperationMessage("Se requiere archivo csv.");
                return resp;
            }


        }
        catch (Exception ex){
            resp.setOperationCode(500);
            resp.setOperationMessage(ex.getMessage());
            System.out.println(ex.toString());
        }
        return resp;
    }
    public boolean validateinfoFile(UserExistsRequestModel req){
        return req.getFileBase64() != null && !req.getFileBase64().equals("") && req.getFileName() != null && !req.getFileName().equals("") && req.getFileExtention() != null && !req.getFileExtention().equals("");
    }
    public void setDataProposal(proposalmoldel item){
        DynamoClient<proposalmoldel> db = new DynamoClient<>(TableDataProposal, proposalmoldel.class);
        db.addRow(item);
    }

    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    public boolean sendEmailProposal(ResponseProposal resp, List<AttachedFileModel> files) throws ParseException {

        TransactionItem config = getconfig();
        List<String> recips = Arrays.asList(config.getEmailSended().split(";"));
        String Template = GenUtils.getHtmlTemplate("emailTemplate.html");
        Template = Template
                .replace("#idProp",resp.getProposal().getIdProp())
                .replace("#sendBy",resp.getProposal().getSendBy())
                .replace("#reference",resp.getProposal().getReference())
                .replace("#amount",String.valueOf(resp.getProposal().getAmount()))
                .replace("#typeProposal",resp.getProposal().getTypeProposal())
                .replace("#url","http://rtdpropuestas.autentic.com.co/")
                .replace("#sendByEmail", resp.getProposal().getSendByEmail());



        SendEmailRequest request =  buildEmailRequest(null,recips,"Autentic te informa que Ha llegado una propuesta nueva.",Template,null,files,EmailTypeEnum.INFO);
        this.emailService.sendEmail(request);
        return true;
    }
    public boolean sendEmailMultiple(List<proposalmoldel> resp, List<AttachedFileModel> files) throws ParseException {

        TransactionItem config = getconfig();
        List<String> recips = Arrays.asList(config.getEmailSended().split(";"));
        String Template = GenUtils.getHtmlTemplate("emailMulti.html");
        String Templateprop = "";
        for (proposalmoldel prop:resp) {
            String TemplatepropT = GenUtils.getHtmlTemplate("proposals.html");
            Templateprop += TemplatepropT
                    .replace("#idProp",prop.getIdProp())
                    .replace("#reference",prop.getReference())
                    .replace("#amount",String.valueOf(prop.getAmount()));
        }

        Template = Template
                .replace("#sendBy",resp.get(0).getSendBy())
                .replace("#typeProposal",resp.get(0).getTypeProposal())
                .replace("#url","https://rtdpropuestas.autentic.com.co/")
                .replace("#proposals",Templateprop)
                .replace("#sendByEmail", resp.get(0).getSendByEmail());



        SendEmailRequest request =  buildEmailRequest(null,recips,"Autentic te informa que Ha llegado una propuesta nueva.",Template,null,files,EmailTypeEnum.INFO);
        this.emailService.sendEmail(request);
        return true;
    }
    private SendEmailRequest buildEmailRequest(String recipient, List<String> recipients, String subject, String body,
                                               List<S3FileModel> s3Files, List<AttachedFileModel> attachedFiles, EmailTypeEnum emailType) {

        EmailModel emailModel = new EmailModel();
        emailModel.setRecipientEmail(recipient);
        emailModel.setRecipientEmails(recipients);
        emailModel.setSubject(subject);
        emailModel.setBody(body);
        emailModel.setFiles(s3Files);
        emailModel.setAttachedFiles(attachedFiles);
        emailModel.setEnterprise("RTD");

        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setEmail(emailModel);
        emailRequest.setEmailType(emailType);

        return emailRequest;
    }
    public TransactionItem getconfig() throws ParseException {
        TransactionItem response = new TransactionItem();
        DynamoClient<TransactionItem> db = new DynamoClient<>(TableData, TransactionItem.class);
        response =db.getItem(docConfig);
        return response;
    }
    public ResponseListProposal getAllProposal (UserExistsRequestModel request){
        ResponseListProposal Resp = new ResponseListProposal();
        try{
            DynamoClient<TransactionItem> db = new DynamoClient<>("", TransactionItem.class);
            //Long value1 = Long.parseLong(request.getDateIni());
            //Long value2 = Long.parseLong(request.getDateEnd());
            //Resp = db.getAllItem(request.getLastKeyEvaluated(), request.getLimitScann());
            Resp = db.getAllItem();
            Resp.setOperationCode(200);Resp.setOperationMessage("ok");
        }
          catch (Exception ex){
              Resp.setOperationCode(500);
              Resp.setOperationMessage(ex.getMessage());
              System.out.println(ex.toString());
        }

        return Resp;
    }

    public ResponseListClient getAllClient (UserExistsRequestModel request){
        ResponseListClient Resp = new ResponseListClient();
        try{
            DynamoClient<TransactionItem> db = new DynamoClient<>("", TransactionItem.class);
            Resp = db.getClientsByEntity(request.getEntity());
            Resp.setOperationCode(200);Resp.setOperationMessage("ok");
        }
        catch (Exception ex){
            Resp.setOperationCode(500);
            Resp.setOperationMessage(ex.getMessage());
            System.out.println(ex.toString());
        }

        return Resp;
    }

    public ResponseProposal getClientsByEntityAsCSV(UserExistsRequestModel request){
        ResponseProposal response = new ResponseProposal();
        try{
            ResponseListClient clientsByEntity = this.getAllClient(request);
            List<String[]>  clientList = new ArrayList<>();
            byte[] bytesFinal = null;

            for(TransactionItem client: clientsByEntity.getClients()){
                clientList.add(new String[] {client.getDocumentcustomerId(), client.getEntity()});
            }

            String tempDir = System.getProperty("java.io.tmpdir");

            File csvOutputFile = new File(tempDir + '/' + request.getFileName() + '.' + request.getFileExtention());
            try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                clientList.stream()
                        .map(this::convertToCSV)
                        .forEach(pw::println);
            }
            FileInputStream fis = new FileInputStream(csvOutputFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[2048];
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            bytesFinal = bos.toByteArray();
            bos.close();
            fis.close();

            response.setOperationCode(200);
            response.setOperationMessage("ok");
            response.setBase64FileCSV(Base64.getEncoder().encodeToString(bytesFinal));

            response.setProposal(null);
        }
        catch (Exception ex){
            response.setOperationCode(500);
            response.setOperationMessage(ex.getMessage());
            System.out.println(ex.toString());
        }

        return response;
    }

}
