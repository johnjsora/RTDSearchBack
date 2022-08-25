package com.co.autentic.RTDDataSearch.users;

import com.co.autentic.RTDDataSearch.users.aws.DynamoClient;
import com.co.autentic.RTDDataSearch.users.aws.models.TransactionItem;
import com.co.autentic.RTDDataSearch.users.aws.models.proposalmoldel;
import com.co.autentic.RTDDataSearch.users.aws.models.usermodel;
import com.co.autentic.RTDDataSearch.users.models.*;
import com.co.autentic.RTDDataSearch.users.services.users;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

public class Test {
    private static Properties envConfig;

    public static void main(String[] args) throws IOException, ParseException {
        users user = new users();
        DynamoClient<TransactionItem> db = new DynamoClient<>("", TransactionItem.class);

        //UserExistsRequestModel requestModel = new UserExistsRequestModel();
        //requestModel.setUserId("CC1018510706");
        //requestModel.setEntity("Bancolombia");
        //UserExistsResponse userExistsResponse = user.verifyByCC(requestModel);
        //System.out.println(userExistsResponse);

        //System.out.println(userExistsResponse.getOperationCode());
        //TransactionItem newTI = new TransactionItem("CC0000000000", "RTD", "");
        //newTI.setEmailSended("andres.giraldo@resuelvetudeuda.co");
        //boolean exis = db.addRow(newTI);
        //TransactionItem search = db.getItemRange("CC0000000000", "RTD");
        //System.out.println(search);

        //String key = "Files/john.sora@autentic.com.co/b9a02d30/Prueba.csv";
        //ResponseDocuments respdoc = user.getDocuments(key);
        //System.out.println(respdoc);

        //DynamoClient<TransactionItem> db = new DynamoClient<>("", TransactionItem.class);
        //Long valie1 = Long.parseLong("1639186547165");
        //Long valie2 = Long.parseLong("1639186547169");
        //db.getAllItem(null,10);

        //ResponseUserModel resp = user.getUsers("andres.giraldo@resuelvetudeuda.co");
        //System.out.println(resp.getOperationContent());


        //UserExistsRequestModel request = new UserExistsRequestModel();
        //request.setUserId("000000000");
        //request.setTypeDocument("CONFIG");
        //UserExistsResponse resp = user.verifyByCC(request);
        //System.out.println(resp);

        UserExistsRequestModel request = new UserExistsRequestModel();
        //request.setProposal(false);

        //request.setSendBy("Andres Giraldo");
        //request.setSendByEmail("andres.giraldo@resuelvetudeuda.co");
        //request.setReference("1");
        //request.setTypeProposal("Individual");
        //request.setAmount(2000);
        //request.setEntity("Bancolombia");
        //request.setUserId("CC1018510706");
        //request.setBank("Bancolombia");
        //request.setDescription("Negociacion");

        //ResponseProposal response = user.setProposal(request);
        //System.out.println(response.getOperationCode()+"---"+response.getOperationMessage());
        //request.setFileBase64("ZG9jdW1lbnRvLGVudGlkYWQsbW9udG8sZGVzY3JpcGNpb24scmVmZXJlbmNpYSBkZSBkZXVkYQpDQzEwMTg1MTA3MDYsQmFuY29sb21iaWEsMjUwMDAwMCxMYSBkZXVkYSBzZSBsbGV2YSBhIGNhYm8gZW4gLi4uLiwyCg==");
        //request.setFileName("Prueba");
        //request.setFileExtention("csv");

        //ResponseProposal response = user.setProposalMulti(request);
        //System.out.println(response.getOperationCode()+"----"+response.getOperationMessage());
        /*ResponseListProposal resp = user.getAllProposal(request);
        System.out.println(resp.getProposals());

        DynamoClient<proposalmoldel> dbProposal = new DynamoClient<>("td-Clients-Proposal", proposalmoldel.class);
        for(proposalmoldel proposal: resp.getProposals()){
            if(proposal.getSendByEmail().equals("andres.giraldo@resuelvetudeuda.co")) {
                boolean wasDelete = dbProposal.deleteRow(proposal);
                System.out.println("Was delete: " + wasDelete + "--" + proposal);
            }
        }*/

        //UserExistsRequestModel request = new UserExistsRequestModel();
        //request.setProposal(false);
        //request.setEntity("Bancolombia");
        //request.setFileExtention("csv");
        //request.setFileName("Clientes_Bancolombia");
        //ResponseProposal resp = user.getClientsByEntityAsCSV(request);
        //System.out.println(resp.getOperationCode() + " " + resp.getOperationMessage() + " " + resp.getBase64FileCSV());
    }

}
