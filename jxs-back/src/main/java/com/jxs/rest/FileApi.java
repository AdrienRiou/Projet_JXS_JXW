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
    public static final String DROPBOX_BASE_URI = "https://api.dropboxapi.com/2";

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
        String token = Redirect.loginDatabase.getTokenFromService(cookie, service) ;
        if ( token == null || token == "none") {
            JSONObject json = new JSONObject();
            json.put("errorMessage" , "token invalid");
            res = json.toString();
        } else {
            if (service.equalsIgnoreCase("google")) {
                try {
                    res = HttpRequest.get(GOOGLE_BASE_URI + "/files", "?fields=*&access_token=" + Redirect.loginDatabase.getTokenFromService(cookie, service) + "&q=%27root%27%20in%20parents", null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                res = this.universalizeGoogleJsonFile(res);

            } else if (service.equalsIgnoreCase("dropbox")) {
                try {
                    String params = "{\"path\": \"\",\"recursive\": false,\"include_media_info\": true,\"include_deleted\": false,\"include_has_explicit_shared_members\": false}";
                    Map<String, String> properties = new HashMap();
                    properties.put("Content-Length", params.getBytes().length + "");
                    properties.put("Content-Type", "application/json");
                    properties.put("Accept", "application/json");
                    properties.put("Authorization", "Bearer " + Redirect.loginDatabase.getTokenFromService(cookie, service));
                    res = HttpRequest.post(DROPBOX_BASE_URI + "/files/list_folder", params, properties, false);
                    res = this.universalizeDropboxJsonFile(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Response.ok(res, MediaType.APPLICATION_JSON)
                .build();


    }

    /**
     *
     * @param service
     * @param cookie
     * @return the list of the files inside the folder ( {id} being its ID OR path for dropbox )
     */
    @GET
    @Path("/{service}/parent")
    @Produces("application/json")
    public Response getFolderFiles(@PathParam("service") String service, @QueryParam("id") String id, @CookieParam("pseudo") String cookie) {
        String res = "";
        if ( service.equalsIgnoreCase("google")) {
            try {
                res = HttpRequest.get(GOOGLE_BASE_URI+"/files", "?fields=*&access_token="+Redirect.loginDatabase.getTokenFromService(cookie, "google")+"&q=%27"+id+"%27%20in%20parents", null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            res = this.universalizeGoogleJsonFile(res);

        } else if (service.equalsIgnoreCase("dropbox")) {
            try {
                String params = "{\"path\": \"" + id + "\",\"recursive\": false,\"include_media_info\": true,\"include_deleted\": false,\"include_has_explicit_shared_members\": false}";
                System.out.println(params);
                Map<String,String> properties = new HashMap();
                properties.put("Content-Length", params.getBytes().length+"");
                properties.put("Content-Type", "application/json");
                properties.put("Accept", "application/json");
                System.out.println( Redirect.loginDatabase.getTokenFromService(cookie, "dropbox"));
                properties.put("Authorization", "Bearer " + Redirect.loginDatabase.getTokenFromService(cookie, "dropbox"));
                res = HttpRequest.post(DROPBOX_BASE_URI+"/files/list_folder", params, properties,false);
                res = this.universalizeDropboxJsonFile(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Response.ok(res , MediaType.APPLICATION_JSON)
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

    @GET
    @Path("/{service}/remove")
    public Response removeFile(@PathParam("service") String service, @QueryParam("id") String id, @CookieParam("pseudo") String cookie) {
        JSONObject json = new JSONObject();
        if(service.equalsIgnoreCase("google")) {
            try {
                HttpRequest.delete(GOOGLE_BASE_URI+"/files/"+id, "?access_token="+Redirect.loginDatabase.getTokenFromService(cookie, "google"));
                json.put("error", 0);
            } catch (IOException e) {
                json.put("error", 1);
                e.printStackTrace();
            }
        } else if (service.equalsIgnoreCase("dropbox")) {
            try {
                String params = "{ \"entries\" : [" +
                        "{ \"path\" : \"" + id + "\"" +
                        "}" +
                        "]" +
                        "}";
                HashMap<String, String> props = new HashMap<>();
                props.put("Content-Length", params.getBytes().length+"");
                props.put("Content-Type", "application/json");
                props.put("Accept", "application/json");
                props.put("Authorization", "Bearer " + Redirect.loginDatabase.getTokenFromService(cookie, "dropbox"));
                HttpRequest.post(DROPBOX_BASE_URI+"/files/delete_batch", params, props, false);
                json.put("error", 0);
            } catch (IOException e) {
                json.put("error", 1);
                e.printStackTrace();
            }
        }
        return Response.ok(json.toString(), MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{service}/rename/{id}/{new}")
    public Response renameFile(@PathParam("new") String new_name, @PathParam("service") String service, @PathParam("id") String id, @CookieParam("pseudo") String cookie) {
        JSONObject json = new JSONObject();
        if(service.equalsIgnoreCase("google")) {
            try {
                String params = "{\"name\": \"" + new_name + "\"}";
                Map<String,String> properties = new HashMap();
                properties.put("Content-Length", params.getBytes().length+"");
                properties.put("Content-Type", "application/json");
                properties.put("Accept", "application/json");
                System.out.println(Redirect.loginDatabase.getTokenFromService(cookie, "google"));
                properties.put("Authorization", "Bearer " + Redirect.loginDatabase.getTokenFromService(cookie, "google"));
                 HttpRequest.post(GOOGLE_BASE_URI+"/files/" + id, params, properties,true);
                json.put("error", 0);
            } catch (IOException e) {
                json.put("error", 1);
                e.printStackTrace();
            }
        } else if (service.equalsIgnoreCase("dropbox") ) {
            try {
                // GET THE FILE PATH
                String params = "{\"path\": \"" + id + "\",\"recursive\": false,\"include_media_info\": true,\"include_deleted\": false,\"include_has_explicit_shared_members\": false}";
                System.out.println(params);
                Map<String,String> properties = new HashMap();
                properties.put("Content-Type", "application/json");
                properties.put("Accept", "application/json");
                String token = Redirect.loginDatabase.getTokenFromService(cookie, "dropbox");
                System.out.println(token);
                properties.put("Authorization", "Bearer " + token);
                String res = HttpRequest.post(DROPBOX_BASE_URI+"/files/get_metadata", params, properties,false);
                /*
                // RENAME THE FILE
                String params = "{\"title\": \"" + new_name + "\", \"id\": \"" + id + "\"}";
                System.out.println(params);
                Map<String,String> properties = new HashMap();
                properties.put("Content-Length", params.getBytes().length+"");
                properties.put("Content-Type", "application/json");
                properties.put("Accept", "application/json");
                properties.put("Authorization", "Bearer " + Redirect.loginDatabase.getTokenFromService(cookie, "dropbox"));
                String res = HttpRequest.post(DROPBOX_BASE_URI+"/files/move_v2", params, properties,false);*/
                System.out.println(res);
                json.put("error", 0);
            } catch (IOException e) {
                json.put("error", 1);
                e.printStackTrace();
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
            tmp.put("service", "google");
            JSONArray authors = current.getJSONArray("owners");
            for (int j = 0; j < authors.length() ; j++ ) {
                tmp.getJSONArray("authors").put(authors.getJSONObject(j).get("displayName"));
            }
            // creation date
            tmp.put("creationDate", current.get("createdTime"));
            // last edit date
            tmp.put("lastEditDate", current.get("modifiedTime"));

            if ( current.get("mimeType").toString().equalsIgnoreCase("application/vnd.google-apps.folder")) {
                tmp.put("isFolder", true);
            } else {
                tmp.put("isFolder", false);
            }

            // add the file to the array
            json_res.getJSONArray("files").put(tmp);


        }


        res = json_res.toString();
        return res;
    }

    /**
     * Transform the google REST API JSON response to our universal format, easier to process by the frontend
     * @param raw the json data to transform
     * @return the universal JSON data
     */
    private String universalizeDropboxJsonFile(String raw) {
        String res = "";
        JSONObject json = new JSONObject(raw);
        JSONObject json_res = new JSONObject();
        json_res.put("files", new JSONArray());
        for (int i = 0; i < json.getJSONArray("entries").length() ; i++ ) {
            JSONObject tmp = new JSONObject();
            JSONObject current = (JSONObject) json.getJSONArray("entries").get(i);
            // ID
            tmp.put("id", current.get("id").toString().split(":")[1]);
            // name
            tmp.put("name", current.get("name"));
            // no way to get authors with dropbox
            tmp.put("authors", new JSONArray());
            // path
            // same
            tmp.put("creationDate", "");
            tmp.put("service", "dropbox");
            if ( current.get(".tag").toString().equalsIgnoreCase("folder")) {
                tmp.put("isFolder", true);
                //size
                tmp.put("size", 0);
                // last edit date
                tmp.put("lastEditDate", "");
            } else {
                tmp.put("isFolder", false);
                //size
                tmp.put("size", current.get("size"));
                // last edit date
                tmp.put("lastEditDate", current.get("server_modified"));
            }
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
        NewCookie cookie = new NewCookie("pseudo", pseudo, "/","" , 1, "", -1, false);
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
    public Response disconnect(@CookieParam("pseudo") Cookie cookie_login ) {
        if ( cookie_login != null ) {
            Redirect.loginDatabase.unlogUser(cookie_login.getValue());

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


    @GET
    @Path("/isconnected")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isConnected(@CookieParam("pseudo") String cookie) {
        JSONObject res = new JSONObject();
        if (cookie == null) {
            res.put("isConnected", false);
            return Response.ok(res.toString()).build();
        } else {
            res.put("isConnected", true);
            res.put("pseudo", cookie);
            return Response.ok(res.toString()).build();
        }


    }
}
