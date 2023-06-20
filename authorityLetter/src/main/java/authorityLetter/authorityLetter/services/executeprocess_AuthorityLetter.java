package authorityLetter.authorityLetter.services;

import authorityLetter.authorityLetter.models.*;
import authorityLetter.authorityLetter.security.SecurityUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


@Service
@Async("getAuthorityLetter_Executor")
public class executeprocess_AuthorityLetter {
    private final getAuthoritiesLettersLambda getAuthoritiesLetters;
    private final emailServiceAutentic sendEmail;

    public executeprocess_AuthorityLetter(getAuthoritiesLettersLambda getAuthoritiesLetters, emailServiceAutentic sendEmail) {
        this.getAuthoritiesLetters = getAuthoritiesLetters;
        this.sendEmail = sendEmail;
    }

    public void setProcessAsyncAuthorityLetter(BufferedReader br, String FileName, String emailSended){
            try{
                // Use for asyn Test
                // Thread.sleep(20000);
                String line = null;
                String uuid = String.valueOf(UUID.randomUUID());
                List<String[]> clientList = new ArrayList<>();
                String tempDir = System.getProperty("java.io.tmpdir");
                //create Temp Dir
                File directorio = new File(tempDir+ uuid);
                List<String> filesToBeZipped = new ArrayList<>();
                if (!directorio.exists()) {
                    if (directorio.mkdirs()) {
                        System.out.println("Directorio creado");
                    } else {
                        System.out.println("Error al crear directorio");
                    }
                }
                while((line = br.readLine()) != null){
                    String[] lineSub = line.split(",");
                    String getDocumentId = lineSub[0].substring(2);
                    directAuthorityRequest req =new directAuthorityRequest();
                    req.setDoccp(getDocumentId);
                    try{
                        responseAuthorityLetterLAmbda resp = this.getAuthoritiesLetters.getAuthorityLetter(req);
                        if(resp.getDocumentBase()!= null && !resp.getDocumentBase().equals("")){
                            //download file to temp dir
                            String fileName = resp.getDocumentBase().split("/")[resp.getDocumentBase().split("/").length -1];
                            clientList.add(new String[] {line + "," + fileName});
                            File file = new File(tempDir + uuid + "/" + fileName);
                            URLConnection conn = new URL(resp.getDocumentBase()).openConnection();
                            conn.connect();
                            System.out.println("\nempezando descarga: \n");
                            System.out.println(">> tamaño: " + conn.getContentLength() + " bytes");
                            InputStream in = conn.getInputStream();
                            OutputStream out = new FileOutputStream(file);
                            int b = 0;
                            while (b != -1) {
                                b = in.read();
                                if (b != -1)
                                    out.write(b);
                            }
                            out.close();
                            in.close();
                            filesToBeZipped.add(tempDir + uuid + "/" + fileName);
                        }
                        else{
                            clientList.add(new String[] {line + ",No se encuentra Carta poder"});
                        }
                    }
                    catch(Exception ex){
                        System.out.println(ex);
                        clientList.add(new String[] {line+",No se encuentra Carta poder"});
                    }
                }
                // save CSV guide
                File csvOutputFile = new File(tempDir + uuid  + "/" + FileName);
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
                bos.close();
                fis.close();
                filesToBeZipped.add(tempDir + uuid + "/" + FileName );
                //Ziped file
                byte[] bytesFinal = null;
                FileOutputStream fos = new FileOutputStream(tempDir + uuid + ".zip");
                //ZipOutputStream zos = new ZipOutputStream(fos);
                ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
                ZipOutputStream zos = new ZipOutputStream(bos2);
                for (String filePath : filesToBeZipped)
                {
                    File fileToBeZipped = new File(filePath);
                    fis = new FileInputStream(fileToBeZipped);
                    ZipEntry zipEntry = new ZipEntry(fileToBeZipped.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] bytes = new byte[2048];
                    while (fis.read(bytes) >= 0)
                    {
                        zos.write(bytes, 0, bytes.length);
                    }
                    zos.closeEntry();
                    fis.close();
                }
                zos.close();
                fos.close();

                //prepare send Email
                AttachedFileModel File = new AttachedFileModel();
                File.setFileName(uuid + ".zip");
                File.setFileContent(bos2.toByteArray());

                //send email
                List<AttachedFileModel> files = new ArrayList<>();
                files.add(File);
                sendEmail(files,emailSended);
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
    public String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    public boolean sendEmail(List<AttachedFileModel> files, String emailSend) throws ParseException {
        String Template = SecurityUtils.getHtmlTemplate("emailAuthorityLetter.html");
        SendEmailRequest request =  buildEmailRequest(emailSend,null,"Autentic te informa que ya se generaron las cartas poder solicitadas",Template,null,files,EmailTypeEnum.INFO);
        this.sendEmail.sendEmail(request);
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
}
