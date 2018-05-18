package com.jxs.rest;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * file manipulation class, list all or one file, add, delete, update... 
 */
@Path("api")
public class FileApi {

    public static final String GOOGLE_BASE_URI = "https://www.googleapis.com/drive/v3";

    @GET
    @Path("/{service}/all")
    @Produces("application/json")
    public Response getAllFiles(@PathParam("service") String service, @CookieParam("pseudo") Cookie cookie) {
        String res = "";
        if ( service.equalsIgnoreCase("google")) {
            try {
                res = HttpRequest.get(GOOGLE_BASE_URI+"/files", "?fields=*&access_token="+Redirect.loginDatabase.getTokenFromService(cookie.getValue()
                        , "google"), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return Response.ok(this.universalizeGoogleJsonFile(res), MediaType.APPLICATION_JSON)
                .build();


    }

    @GET
    @Path("/{service}/root")
    @Produces("application/json")
    public Response getRootFiles(@PathParam("service") String service, @CookieParam("pseudo") String cookie) {
        String res = "";
        if ( service.equalsIgnoreCase("google")) {
            try {
                res = HttpRequest.get(GOOGLE_BASE_URI+"/files", "?fields=*&access_token="+Redirect.loginDatabase.getTokenFromService(cookie, "google")+"&q=%27root%27%20in%20parents", null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return Response.ok(this.universalizeGoogleJsonFile(res), MediaType.APPLICATION_JSON)
                .build();


    }

    @GET
    @Path("/{service}/file/{id}")
    @Produces("application/json")
    public Response getFile(@PathParam("service") String service, @PathParam("id") String id, @CookieParam("pseudo") String cookie) {
        String res = "";
        if(service.equalsIgnoreCase("google")) {
            try {
            res = HttpRequest.get(GOOGLE_BASE_URI+"/files/"+id, "?access_token="+Redirect.loginDatabase.getTokenFromService(cookie, "google"), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Response.ok(res, MediaType.APPLICATION_JSON).build();
    }


    // not working yet
    @POST
    @Path("/{service}/add/{name}")
    @Produces("application/json")
    public Response addFile(@PathParam("service") String service, @PathParam("name") String name, @CookieParam("pseudo") String cookie) {
        String res = "";
        if ( service.equalsIgnoreCase("google")) {
            try {
                String params = "?access_token="+Redirect.loginDatabase.getTokenFromService(cookie, "google")+"&name="+name;

                Map<String,String> properties = new HashMap<String,String>();
                properties.put("Content-Length", params.getBytes().length+"");
                properties.put("Content-Type", "application/x-www-form-urlencoded");

                res = HttpRequest.post(GOOGLE_BASE_URI+"/files", params, properties);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return Response.ok(res, MediaType.APPLICATION_JSON).build();
    }


    @GET
    @Path("/{service}/remove/{id}")
    public Response removeFile(@PathParam("service") String service, @PathParam("id") String id, @CookieParam("pseudo") String cookie) {
        JSONObject json = new JSONObject();
        if(service.equalsIgnoreCase("google")) {
            try {
                HttpRequest.delete(GOOGLE_BASE_URI+"/files/"+id, "?access_token="+Redirect.loginDatabase.getTokenFromService(cookie, "google"));
                json.put("error", 0);
            } catch (IOException e) {
                json.put("error", 1);
            }
        }
        return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    }



    /**
     * Transform the google REST API JSON response to our universal format, easier to process by the frontend
     * @param raw the json data to transform
     * @return the universal JSON data
     */
    private String universalizeGoogleJsonFile(String raw) {
        String res = "";
        JSONObject json = new JSONObject(raw);
        JSONObject json_res = new JSONObject();
        json_res.put("files", new JSONArray());
        for (int i = 0; i < json.getJSONArray("files").length() ; i++ ) {
            JSONObject tmp = new JSONObject();
            JSONObject current = (JSONObject) json.getJSONArray("files").get(i);
            // ID
            tmp.put("id", current.get("id"));
            // name
            tmp.put("name", current.get("name"));
            //size
            tmp.put("size", current.get("quotaBytesUsed"));
            // authors
            tmp.put("authors", new JSONArray());
            JSONArray authors = current.getJSONArray("owners");
            for (int j = 0; j < authors.length() ; j++ ) {
                tmp.getJSONArray("authors").put(authors.getJSONObject(j).get("displayName"));
            }
            // creation date
            tmp.put("creationDate", current.get("createdTime"));
            // last edit date
            tmp.put("lastEditDate", current.get("modifiedTime"));


            // add the file to the array
            json_res.getJSONArray("files").put(tmp);


        }


        res = json_res.toString();
        return res;
    }


    @GET
    @Path("/connect")
    @Produces(MediaType.APPLICATION_JSON)
    public Response connect(@QueryParam("pseudo") String pseudo) {
        NewCookie cookie = new NewCookie("pseudo", pseudo, "/   ","" , 1, "", -1, false);
        JSONObject json = new JSONObject();


        json.put("pseudo", pseudo);
        Redirect.loginDatabase.addUser(pseudo);
        if ( Redirect.loginDatabase.getLoggedUsers().contains(pseudo) ) {
            json.put("error", 1);
            json.put("errorMessage" ,  "User already connected");
            return Response.ok(json.toString()).build();
        } else {
            json.put("error", 0);
            Redirect.loginDatabase.addLoggedUser(pseudo);
        }


        return Response.ok(json.toString()).cookie(cookie).build();
    }


    @GET
    @Path("/disconnect")
    @Produces(MediaType.TEXT_PLAIN)
    public Response disconnect(@CookieParam("pseudo") Cookie cookie ) {
        if ( cookie != null ) {
            Redirect.loginDatabase.unlogUser(cookie.getValue());

            return Response.ok("Disconnected").header(
                    "Set-Cookie",
                    "pseudo=deleted;Domain=;Path=/;Expires=Thu, 01-Jan-1970 00:00:01 GMT"
            ).build();
        }
        return  Response.ok("Not connected").build();

    }
    @GET
    @Path("/testcookie")
    @Produces(MediaType.TEXT_PLAIN)
    public Response foo(@CookieParam("pseudo") String cookie) {
        if (cookie == null) {
            return Response.serverError().entity("ERROR COOKIE NOT FOUND").build();
        } else {
            return Response.ok(cookie + " VALUE : " + Redirect.loginDatabase.getTokenFromService(cookie, "google")).build();
        }
    }
}
