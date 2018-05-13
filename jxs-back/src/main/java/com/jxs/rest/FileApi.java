package com.jxs.rest;


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
                res = HttpRequest.get(GOOGLE_BASE_URI+"/files", "?access_token="+Redirect.google_token, null);
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

}
