package com.pf.tool.logic;

import com.pf.tool.bot.TelegramBot;
import com.pf.tool.properties.PropertiesReader;

import java.util.Calendar;
import java.util.Date;

public class PricesChecker {

    public String checkTime(long time, double parameter){

        String result;
        //Date date = new Date();
        //long currentTime = date.getTime();

        Calendar rightNow = Calendar.getInstance();
        long currentTime = rightNow.getTimeInMillis();

        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.fileReader();
        String chatid = propertiesReader.getChatid();

            if (time + parameter < currentTime) {
                result = "Prices delay is" + ": " + (currentTime - time )/1000 + " secs";
                TelegramBot telegramBot = new TelegramBot();
                telegramBot.sendMsg(chatid, result);
                return result;
            }

        return null;
    }

}
