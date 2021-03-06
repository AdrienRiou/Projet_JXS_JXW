package com.jxs.rest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class HttpRequest {
    public static String get(String url, String parameters, Map<String,String> properties) throws IOException{
        URL u = new URL(url+parameters);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        checkProperties(properties, con);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        con.getInputStream())
        );
        String inputLine;

        String source="";
        while ((inputLine = in.readLine()) != null)
            source +=inputLine;

        in.close();
        return source;
    }

    private static void checkProperties(Map<String, String> properties, HttpURLConnection con) {
        if(properties != null){
            Iterator<Entry<String, String>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> pair = it.next();
                con.setRequestProperty(pair.getKey(), pair.getValue());
            }
        }
    }

    public static String delete(String url, String parameters) throws IOException{
        URL u = new URL(url+parameters);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        con.setRequestMethod("DELETE");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        con.getInputStream())
        );
        String inputLine;

        String source="";
        while ((inputLine = in.readLine()) != null)
            source +=inputLine;

        in.close();
        return source;
    }

    public static String patch(String url, String parameters) throws IOException{
        URL u = new URL(url+parameters);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
            methodsField.setAccessible(true);
            // get the methods field modifiers
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            // bypass the "private" modifier
            modifiersField.setAccessible(true);

            // remove the "final" modifier
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            /* valid HTTP methods */
            String[] methods = {
                    "GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE", "PATCH"
            };
            // set the new methods - including patch
            methodsField.set(null, methods);

        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        con.setRequestMethod("PATCH");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        con.getInputStream())
        );
        String inputLine;

        String source="";
        while ((inputLine = in.readLine()) != null)
            source +=inputLine;

        in.close();
        return source;
    }


    public static String post(String url, String parameters, Map<String,String> properties, boolean patch) throws IOException{
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL u = new URL(url);
            connection = (HttpURLConnection)u.openConnection();
            if ( patch) {
                connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            }
            connection.setRequestMethod("POST");

            checkProperties(properties, connection);

            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
            wr.writeBytes (parameters);
            wr.flush ();
            if (wr != null)
                wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return response.toString();
        } catch (MalformedURLException e) {

            e.printStackTrace();
            return e.getMessage();

        } catch (IOException e) {

            e.printStackTrace();
            return e.getMessage();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
