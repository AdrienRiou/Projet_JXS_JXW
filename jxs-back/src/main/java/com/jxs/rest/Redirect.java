package com.jxs.rest;


import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Path("redirect")
public class Redirect {

    public static final String REDIRECT_URI = "http://localhost:8080/rest/redirect/google";
    public static final String REDIRECT_URI_DROPBOX = "http://localhost:8080/rest/redirect/dropbox";
    public static final String CLIENT_URL = "http://localhost:4200";
    public static Login loginDatabase = new Login();

    @GET
    @Path("google")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt( @QueryParam("code") String code, @CookieParam("pseudo") String cookie) {
        String CLIENT_ID = "";
        String CLIENT_SECRET = "";
        String google_token;
        URI client = null;
        try {
            String url =  "https://www.googleapis.com/oauth2/v4/token";
            CLIENT_ID = "752520275648-e65rp9l865vctpj0535kpoh0bfo5pkd0.apps.googleusercontent.com";
            CLIENT_SECRET = "DJ_Ju7W9iF5HL8BYO32mwnxA";


            String params = "code="+code
                    +"&client_id="+CLIENT_ID
                    +"&client_secret="+CLIENT_SECRET
                    +"&redirect_uri="+REDIRECT_URI
                    +"&grant_type=authorization_code";

            Map<String,String> properties = new HashMap<String,String>();
            properties.put("Content-Length", params.getBytes().length+"");
            properties.put("Content-Type", "application/x-www-form-urlencoded");

            String result = HttpRequest.post("https://www.googleapis.com/oauth2/v4/token",  params, properties, false);

            JSONObject jsonToken = new JSONObject(result);
            google_token = jsonToken.getString("access_token");
            Redirect.loginDatabase.addTokenFromService(cookie, "google", google_token);

            client = new URI(CLIENT_URL);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return Response.temporaryRedirect(client).build();
    }


    @GET
    @Path("dropbox")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDropboxToken( @QueryParam("code") String code, @CookieParam("pseudo") String cookie) {


         // curl https://api.dropbox.com/1/oauth2/token -d code=<authorization code> -d grant_type=authorization_code -d redirect_uri=<redirect URI> -u <app key>:<app secret>
        String CLIENT_ID = "ltfpmsjovjpg6fz";
        String CLIENT_SECRET = "dqcgwho6yisngcq";
        String token;
        URI client = null;
        try {
            String params = "code="+code
                    +"&redirect_uri="+ REDIRECT_URI_DROPBOX
                    +"&grant_type=authorization_code"
                    +"&client_id=" + CLIENT_ID
                    +"&client_secret=" + CLIENT_SECRET;

            Map<String,String> properties = new HashMap();
            properties.put("Content-Length", params.getBytes().length+"");
            properties.put("Content-Type", "application/x-www-form-urlencoded");

            String result = HttpRequest.post("https://api.dropbox.com/1/oauth2/token",  params.toString(), properties, false);
            JSONObject jsonToken = new JSONObject(result);
            token = jsonToken.getString("access_token");
            Redirect.loginDatabase.addTokenFromService(cookie, "dropbox", token);

            client = new URI(CLIENT_URL);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return Response.temporaryRedirect(client).build();
    }

}
