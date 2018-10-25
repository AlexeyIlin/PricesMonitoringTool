package com.pf.tool.rest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.*;


public class RestService {

    private final static Logger logger = Logger.getLogger(RestService.class);

    public String getToken(String url, String login, String pass) {

        String token = null;
        try {

            URL u = new URL(url+"/rest/auth/token");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"login\":"+login+",\"password\":"+pass+"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {

                JSONObject obj = new JSONObject(output);
                token = obj.getString("accessToken");

            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            logger.error(e);

        } catch (IOException e) {

            logger.error(e);

        }

        return token;
    }



    public boolean getIsExpired(String url, String token){

        boolean isExpired = false;
        try {
            URL u = new URL(url + "/rest/auth/token/verify/access?token=" + token);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                return isExpired;
            }


            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {

                JSONObject obj = new JSONObject(output);
                isExpired = obj.getBoolean("isExpired");
            }

            conn.disconnect();




        } catch (MalformedURLException e) {

            logger.error(e);

        } catch (IOException e) {

            logger.error(e);

        }

        return isExpired;
    }



    public long getTime(String url, String token, Object tradeinstId, Object routeId){

        long time = 0;
        try {
        URL u = new URL(url +  "/rest/instruments/"+tradeinstId+"/routes/"+routeId+"/quotes/tradeTick?token="+token);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() == 200 ) {

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {

                JSONObject obj = new JSONObject(output);
                time = obj.getLong("time");
            }

            conn.disconnect();
        }

        else if (conn.getResponseCode() == 204 ){
            return time;
        }

        else
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());

    } catch (MalformedURLException e) {

        logger.error(e);

    } catch (IOException e) {

        logger.error(e);

    }
        return time;
    }



}


