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
    private ArrayList<String> symbolName = new ArrayList<String>();
    private ArrayList<Integer> symbol = new ArrayList<Integer>();
    private ArrayList<Integer> parameter = new ArrayList<Integer>();
    private String endtime, starttime;
    private String botname;
    private String bottoken;
    private ArrayList<String> chatid = new ArrayList<String>();
    private String mode;

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

    public ArrayList<String> getSymbolName() {
        return symbolName;
    }

    public String getMode() {
        return mode;
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


    public void readFile (){


        try{
            Properties property = new Properties();
            FileInputStream fstream = new FileInputStream("app.properties");
            property.load(fstream);

            url = property.getProperty("server.url");
            login = property.getProperty("login");
            pass = property.getProperty("password");

            int i= 1;
            boolean empty = false;
            while (!empty) {
                try {
                    if (property.getProperty("symbol.id"+i) != null
                            || property.getProperty("symbol.name"+i) != null
                            || property.getProperty("route.id"+i) != null
                            || property.getProperty("delay.parameter"+i) != null) {
                        symbol.add(parseInt(property.getProperty("symbol.id" + i)));
                        symbolName.add(property.getProperty("symbol.name" + i));
                        route.add(parseInt(property.getProperty("route.id" + i)));
                        parameter.add(parseInt(property.getProperty("delay.parameter" + i)));
                        i++;
                    }
                    else
                        empty= true;
                }catch (NullPointerException e) {
                    logger.debug("There are no more parameters");
                }

            }

            mode = property.getProperty("mode.type");
            starttime = property.getProperty("start.time");
            endtime = property.getProperty("end.time");
            botname = property.getProperty("bot.name");
            bottoken = property.getProperty("bot.token");
            chatid.add(property.getProperty("chat.id"));
            chatid.add(property.getProperty("chat2.id"));
            fstream.close();

        }catch (IOException e){
            logger.error("Cannot open file or wrong settings", e);
        }
    }





}
