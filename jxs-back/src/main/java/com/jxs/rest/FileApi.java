package com.jxs.rest;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;

@Path("api")
public class FileApi {

    public static final String GOOGLE_BASE_URI = "https://www.googleapis.com/drive/v3";

    @GET
    @Path("/{service}/all")
    public String getAllFiles(@PathParam("service") String service) {
        String res = "";
        if ( service.equalsIgnoreCase("google")) {
            try {https://www.mozilla.org/fr/firefox/central/
                res = HttpRequest.get(GOOGLE_BASE_URI+"/files", "?access_token="+Redirect.google_token, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return res;
    }
}
