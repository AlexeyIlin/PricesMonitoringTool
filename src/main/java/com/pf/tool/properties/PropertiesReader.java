package com.pf.tool.properties;

import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import static java.lang.Integer.parseInt;

public class PropertiesReader {

    private final static Logger logger = Logger.getLogger(PropertiesReader.class);

    private String url;
    private String login;
    private String pass;
    private int route;
    private int symbol;
    private int parameter;
    private int starttime;
    private int endtime;
    private String botname;
    private String bottoken;
    private String chatid;

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public int getRoute() {
        return route;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public String getBotname() {
        return botname;
    }

    public void setBotname(String botname) {
        this.botname = botname;
    }

    public String getBottoken() {
        return bottoken;
    }

    public void setBottoken(String bottoken) {
        this.bottoken = bottoken;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public void fileReader (){


        try{
            Properties property = new Properties();
            FileInputStream fstream = new FileInputStream("app.properties");
            property.load(fstream);

            url = property.getProperty("server.url");
            login = property.getProperty("login");
            pass = property.getProperty("password");
            symbol = parseInt(property.getProperty("symbol.id"));
            route = parseInt(property.getProperty("route.id"));
            parameter = parseInt(property.getProperty("delay.parameter"));
            starttime = parseInt(property.getProperty("start.time"));
            endtime = parseInt(property.getProperty("end.time"));
            botname = property.getProperty("bot.name");
            bottoken = property.getProperty("bot.token");
            chatid = property.getProperty("chat.id");

            fstream.close();

        }catch (IOException e){
            logger.error("Cannot open file", e);
        }
    }





}
