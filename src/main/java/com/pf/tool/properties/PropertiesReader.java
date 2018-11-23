package com.pf.tool.properties;

import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import static java.lang.Integer.parseInt;

public class PropertiesReader {

    private final static Logger logger = Logger.getLogger(PropertiesReader.class);

    private String url;
    private String login;
    private String pass;
    private ArrayList<Integer> route = new ArrayList<Integer>();
    private ArrayList<Integer> symbol = new ArrayList<Integer>();
    private ArrayList<Integer> parameter = new ArrayList<Integer>();
    private String endtime, starttime;
    private String botname;
    private String bottoken;
    private ArrayList<String> chatid = new ArrayList<String>();

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public ArrayList getRoute() {
        return route;
    }

    public ArrayList getSymbol() {
        return symbol;
    }

    public String getStarttime() {
        return starttime;
    }

    public ArrayList<Integer> getParameter() {
        return parameter;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getBotname() {
        return botname;
    }

    public String getBottoken() {
        return bottoken;
    }


    public ArrayList getChatid() {
        return chatid;
    }


    public void fileReader (){


        try{
            Properties property = new Properties();
            FileInputStream fstream = new FileInputStream("app.properties");
            property.load(fstream);

            url = property.getProperty("server.url");
            login = property.getProperty("login");
            pass = property.getProperty("password");

            if (!property.getProperty("symbol.id1").equals("")
                    && property.getProperty("route.id1") != null
                    && property.getProperty("delay.parameter1") != null) {
                symbol.add(parseInt(property.getProperty("symbol.id1")));
                route.add(parseInt(property.getProperty("route.id1")));
                parameter.add(parseInt(property.getProperty("delay.parameter1")));
            }

            if (!property.getProperty("symbol.id2").equals("")
                    && property.getProperty("route.id2") != null
                    && property.getProperty("delay.parameter2") != null) {
                symbol.add(parseInt(property.getProperty("symbol.id2")));
                route.add(parseInt(property.getProperty("route.id2")));
                parameter.add(parseInt(property.getProperty("delay.parameter2")));
            }


            if (!property.getProperty("symbol.id3").equals("")
                    && property.getProperty("route.id3") != null
                    && property.getProperty("delay.parameter3") != null){
                symbol.add(parseInt(property.getProperty("symbol.id3")));
                route.add(parseInt(property.getProperty("route.id3")));
                parameter.add(parseInt(property.getProperty("delay.parameter3")));
            }

            starttime = property.getProperty("start.time");
            endtime = property.getProperty("end.time");
            botname = property.getProperty("bot.name");
            bottoken = property.getProperty("bot.token");
            chatid.add(property.getProperty("chat.id"));
            chatid.add(property.getProperty("chat2.id"));
            fstream.close();

        }catch (IOException e){
            logger.error("Cannot open file", e);
        }
    }





}
