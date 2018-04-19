package com.jxs.rest;

import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.*;
import java.util.Map;

/**
 * Created by marom on 27/09/16.
 */
@Path("redirect")
public class Redirect {

    public static final String REDIRECT_URI = "http://localhost:8080/rest/redirect?service=google";
    public static final String CLIENT_URL = "http://localhost:4200";

    private String google_token;

    @GET
    public Response getIt(@QueryParam("code") String code, @QueryParam("service") String service) {
        Response response = null;
        String CLIENT_ID = "";
        String CLIENT_SECRET = "";
        String res = "";
        try {
            URL url = new URL("http://dummy.com");
            if (service.equalsIgnoreCase("google")) {
                CLIENT_ID = "752520275648-e65rp9l865vctpj0535kpoh0bfo5pkd0.apps.googleusercontent.com";
                CLIENT_SECRET = "DJ_Ju7W9iF5HL8BYO32mwnxA";
                url = new URL("https://www.googleapis.com/oauth2/v4/token");
            }
            // TODO: Add other services

            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("POST");
            connect.setDoOutput(true);

            String data = URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");
            data += "&" + URLEncoder.encode("client_id", "UTF-8") + "=" + URLEncoder.encode(CLIENT_ID, "UTF-8");
            data += "&" + URLEncoder.encode("client_secret", "UTF-8") + "=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8");
            data += "&" + URLEncoder.encode("redirect_uri", "UTF-8") + "=" + URLEncoder.encode(REDIRECT_URI, "UTF-8");
            data += "&" + URLEncoder.encode("grant_type", "UTF-8") + "=" + URLEncoder.encode("authorization_code", "UTF-8");

            System.out.println(data);

            OutputStreamWriter wr = new OutputStreamWriter(connect.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            String line;
            String result = "";
            while ((line = rd.readLine()) != null) {
                result+=line;
            }
            JSONObject jsonToken = new JSONObject(result);

            this.google_token = jsonToken.getString("access_token");
            wr.close();
            rd.close();

            response = Response.temporaryRedirect(new URI(CLIENT_URL + "?google_auth=true")).build();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return response ;
    }
}