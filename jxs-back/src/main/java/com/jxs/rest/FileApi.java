package com.jxs.rest;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public Response getAllFiles(@PathParam("service") String service) {
        String res = "";
        if ( service.equalsIgnoreCase("google")) {
            try {
                res = HttpRequest.get(GOOGLE_BASE_URI+"/files", "?fields=*&access_token="+Redirect.google_token, null);
                System.out.println(this.universalizeGoogleJsonFile(res));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return Response.ok(res, MediaType.APPLICATION_JSON).build();


    }

    @GET
    @Path("/{service}/file/{id}")
    @Produces("application/json")
    public Response getFile(@PathParam("service") String service, @PathParam("id") String id) {
        String res = "";
        if(service.equalsIgnoreCase("google")) {
            try {
            res = HttpRequest.get(GOOGLE_BASE_URI+"/files/"+id, "?access_token="+Redirect.google_token, null);
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
    public Response addFile(@PathParam("service") String service, @PathParam("name") String name) {
        String res = "";
        if ( service.equalsIgnoreCase("google")) {
            try {
                String params = "?access_token="+Redirect.google_token+"&name="+name;

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
    @Produces("application/json")
    public Response removeFile(@PathParam("service") String service, @PathParam("id") String id) {
        String res = "";
        if(service.equalsIgnoreCase("google")) {
            try {
                res = HttpRequest.delete(GOOGLE_BASE_URI+"/files/"+id, "?access_token="+Redirect.google_token);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Response.ok(res, MediaType.APPLICATION_JSON).build();
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

}
