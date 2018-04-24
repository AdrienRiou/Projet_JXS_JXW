package com.jxs.rest;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Path("redirect")
public class Redirect {

    public static final String REDIRECT_URI = "http://localhost:8080/rest/redirect/google";
    public static final String CLIENT_URL = "http://localhost:4200";

    private String google_token;

    @GET
    @Path("google")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(@QueryParam("code") String code) {
        String CLIENT_ID = "";
        String CLIENT_SECRET = "";
        String res = "";
        try {
            URL url =  new URL("https://www.googleapis.com/oauth2/v4/token");
            CLIENT_ID = "752520275648-e65rp9l865vctpj0535kpoh0bfo5pkd0.apps.googleusercontent.com";
            CLIENT_SECRET = "DJ_Ju7W9iF5HL8BYO32mwnxA";


            JSONObject paramJson = new JSONObject();
            paramJson.put("code", code);
            paramJson.put("client_id", CLIENT_ID);
            paramJson.put("client_secret", CLIENT_SECRET);
            paramJson.put("redirect_uri", REDIRECT_URI);
            paramJson.put("grant_type", "authorization_code");

            Map<String,String> properties = new HashMap<String,String>();
            properties.put("Content-Type", "application/json");

            String result = HttpRequest.post("https://api.dropboxapi.com/2/file_requests/get",  paramJson.toString(), properties);


            JSONObject jsonToken = new JSONObject(result);

            this.google_token = jsonToken.getString("access_token");
            System.out.println("GOT TOKEN : " + this.google_token);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "Done" ;
    }

}