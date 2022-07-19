package com.co.autentic.RTDDataSearch.importData;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import com.co.autentic.RTDDataSearch.importData.services.getDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class Launcher implements RequestHandler<Map<String,String>, String> {

    private static final Logger LOGGER = Logger.getLogger(Launcher.class.getName());

    private static Properties envConfig;
    private static ObjectMapper objectMapper;

    private static getDataService importData;

    public Launcher() {

    }

    @Override
    public String  handleRequest(Map<String,String> event, Context context) {

        try {
            String Response ="";
            try {
                getDataService get = new getDataService();
                get.getFileS3();

            } catch (Exception e) {
                context.getLogger().log(e.getMessage());
                Response = "Error: " + e.getMessage();
            }

            return Response;

        } catch (Exception e) {

            context.getLogger().log("ERROR ===> " + e.getMessage());
            context.getLogger().log(e.getMessage());
            return e.getMessage();
        }
    }
}
