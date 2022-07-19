package com.co.autentic.RTDDataSearch.users;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.co.autentic.RTDDataSearch.common.utils.GenUtils;
import com.co.autentic.RTDDataSearch.users.aws.DynamoClient;
import com.co.autentic.RTDDataSearch.users.aws.models.TransactionItem;
import com.co.autentic.RTDDataSearch.users.models.*;
import com.co.autentic.RTDDataSearch.users.services.users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;


public class Launcher implements RequestStreamHandler {

    private static ObjectMapper objectMapper;
    public Launcher() {
        objectMapper = (objectMapper == null) ? new ObjectMapper() : objectMapper;
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream,Context context) throws JsonProcessingException {

        ObjectNode replyProxy = objectMapper.createObjectNode();
        ObjectNode replyHeaders = objectMapper.createObjectNode();

        JsonNode totalInputData;
        replyHeaders.put("Access-Control-Allow-Origin", "*");
        replyHeaders.put("Content-Type", "application/json");
        replyProxy.put("headers", replyHeaders);

        try{
            totalInputData = objectMapper.readTree(inputStream);

            context.getLogger().log("Proxy object: " + totalInputData.toString());
            context.getLogger().log("Body: " + totalInputData.get("body").asText());
            context.getLogger().log("HTTP Method: " + totalInputData.get("httpMethod").asText());
            users user = new users();
            switch (totalInputData.get("httpMethod").asText()) {
                case "GET":
                    String dataValueGet = GenUtils.GetParamValue(totalInputData.get("queryStringParameters").get("email"));
                    String dataValueGetDoc = GenUtils.GetParamValue(totalInputData.get("queryStringParameters").get("doc"));

                    if(dataValueGet != null){
                        ResponseUserModel resp = user.getUsers(dataValueGet);
                        replyProxy.put("body",objectMapper.writeValueAsString(resp));
                        replyProxy.put("statusCode", resp.getOperationCode());
                    }
                    else if(dataValueGetDoc != null){
                        ResponseDocuments respdoc = user.getDocuments(dataValueGetDoc);
                        replyProxy.put("body",objectMapper.writeValueAsString(respdoc));
                        replyProxy.put("statusCode", respdoc.getOperationCode());
                    }

                    break;
                case "POST":
                    UserExistsRequestModel body = objectMapper.readValue(totalInputData.get("body").asText(), UserExistsRequestModel.class);

                    if(body.isProposal())
                    {
                        ResponseProposal respExists = user.setProposal(body);
                        replyProxy.put("body",objectMapper.writeValueAsString(respExists));
                        replyProxy.put("statusCode", respExists.getOperationCode());
                    }
                    else{
                        UserExistsResponse respExists = user.verifyByCC(body);
                        replyProxy.put("body",objectMapper.writeValueAsString(respExists));
                        replyProxy.put("statusCode", respExists.getOperationCode());
                    }
                    break;
                case "PUT":
                    UserExistsRequestModel bodyMulti = objectMapper.readValue(totalInputData.get("body").asText(), UserExistsRequestModel.class);
                        if(bodyMulti.isProposal())
                        {
                            ResponseProposal respExists = user.setProposalMulti(bodyMulti);
                            replyProxy.put("body",objectMapper.writeValueAsString(respExists));
                            replyProxy.put("statusCode", respExists.getOperationCode());
                        }
                        else{
                            ResponseListProposal respList = user.getAllProposal(bodyMulti);
                            replyProxy.put("body",objectMapper.writeValueAsString(respList));
                            replyProxy.put("statusCode", respList.getOperationCode());
                        }
                    break;
                default:
                    Responsemodel resperror = new Responsemodel();
                    resperror.setOperationCode(405);
                    resperror.setOperationMessage("Método no válido");
                    replyProxy.put("statusCode", 405);
                    replyProxy.put("body",objectMapper.writeValueAsString(resperror));
                    break;
            }
        }
        catch (Exception ex){
            Responsemodel resperror = new Responsemodel();
            resperror.setOperationCode(500);
            resperror.setOperationMessage(ex.getMessage());
            context.getLogger().log("ERROR ===> " + ex.getMessage());
            context.getLogger().log(ex.getMessage());

            replyProxy.put("statusCode", 500);
            replyProxy.put("body", objectMapper.writeValueAsString(resperror));

        }
        OutputStreamWriter writer;
        try {
            writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            writer.write(objectMapper.writeValueAsString(replyProxy));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
