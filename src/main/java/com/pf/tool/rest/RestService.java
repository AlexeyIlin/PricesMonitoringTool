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
    //private String refreshToken;

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
                //refreshToken = obj.getString("refreshToken");

            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            logger.error(e);

        } catch (IOException e) {

            logger.error(e);

        }

        return token;
    }


    /*public void refreshToken(String url) {

        try {
            URL u = new URL(url + "/rest/auth/token/refreshh?refreshToken=" + refreshToken);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

        } catch (MalformedURLException e) {

            logger.error(e);

        } catch (IOException e) {

            logger.error(e);

        }
    }*/


    public Boolean getIsExpired(String url, String token){

        Boolean isExpired = null;
        try {
            URL u = new URL(url + "/rest/auth/token/verify/");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+token);

            if (conn.getResponseCode() != 200) {
                return true;
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
        URL u = new URL(url +  "/rest/instruments/"+tradeinstId+"/routes/"+routeId+"/quotes/tradeTick");
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "Bearer "+token);

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

    public ArrayList<Double> getLevel2(String url, String token, Object tradeinstId, Object routeId){

        ArrayList<Double> PriceLevel0 = new ArrayList<Double>();

        try{
            URL u = new URL(url + "/rest/instruments/"+tradeinstId+"/routes/"+routeId+"/quotes/level2?levelTo=0");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer "+token);

            if (conn.getResponseCode() == 200 ) {

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                while ((output = br.readLine()) != null) {

                    JSONObject obj = new JSONObject(output);
                    JSONObject obj_array_bids = obj.getJSONArray("bids").getJSONObject(0);
                    PriceLevel0.add(obj_array_bids.getDouble("price"));

                    JSONObject obj_array_offers = obj.getJSONArray("offers").getJSONObject(0);
                    PriceLevel0.add(obj_array_offers.getDouble("price"));


                }

                conn.disconnect();
            }

            else {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
        }catch (MalformedURLException e){
            logger.error(e);
        }catch (IOException e) {

            logger.error(e);
        }

        return PriceLevel0;
    }

}


