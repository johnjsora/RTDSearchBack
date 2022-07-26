package com.co.autentic.RTDDataSearch.users;

import com.co.autentic.RTDDataSearch.users.aws.DynamoClient;
import com.co.autentic.RTDDataSearch.users.aws.models.TransactionItem;
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
        //String key = "Files/john.sora@autentic.com.co/b9a02d30/Prueba.csv";
        //ResponseDocuments respdoc = user.getDocuments(key);
        //System.out.println(respdoc);

        //DynamoClient<TransactionItem> db = new DynamoClient<>("", TransactionItem.class);
        //Long valie1 = Long.parseLong("1639186547165");
        //Long valie2 = Long.parseLong("1639186547169");
        //db.getAllItem(null,10);




        //ResponseUserModel resp = user.getUsers("john.sora@autentic.com.co");
        //System.out.println(resp);


        //UserExistsRequestModel request = new UserExistsRequestModel();
        //request.setUserId("000000000");
        //request.setTypeDocument("CONFIG");
        //UserExistsResponse resp = user.verifyByCC(request);
        //System.out.println(resp);

        UserExistsRequestModel request = new UserExistsRequestModel();
        request.setProposal(false);

        request.setSendBy("John Sora");
        request.setSendByEmail("john.sora@autentic.com.co");
        //request.setReference("Prueba");
        request.setTypeProposal("Multiple");

        request.setFileBase64("ZG9jdW1lbnQsdHlwZWRvY3VtZW50LGFtb3VudCxkZXNjcmlwdGlvbixyZWZlcmVuY2UsYmFuaw0KMTAxNTQwMzA1NCxDQywyNDAwMDAwLjQzLExhIGRldWRhIHNlIGxsZXZhIGEgY2FibyBlbiAuLi4uLFBhZ29zLEJhbmNvbG9tYmlhDQo3MjEzNjA2OSxDQywyNTAwMDAwLExhIGRldWRhIHNlIGxsZXZhIGEgY2FibyBlbiAuLi4uLFBhZ29zLEJhbmNvbG9tYmlhDQozMzQ4MDI0OSxDQywyMTAwMDAwLExhIGRldWRhIHNlIGxsZXZhIGEgY2FibyBlbiAuLi4uLFBhZ29zLEJhbmNvbG9tYmlhDQoxMTEzNTIyODEyLENDLDkwMDAwMCxMYSBkZXVkYSBzZSBsbGV2YSBhIGNhYm8gZW4gLi4uLixQYWdvcyxCYW5jb2xvbWJpYQ==");
        request.setFileName("Prueba");
        request.setFileExtention("csv");

        ResponseListProposal resp = user.getAllProposal(request);
        System.out.println(resp);

    }

}
