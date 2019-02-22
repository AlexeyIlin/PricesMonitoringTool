package com.pf.tool.logic;

import com.pf.tool.bot.TelegramBot;
import com.pf.tool.properties.PropertiesReader;
import java.util.ArrayList;
import java.util.Calendar;

public class Level1_3Checker {

    public String checkTime(long time, double parameter, String symbolName){

        String result = null;

        Calendar rightNow = Calendar.getInstance();
        long currentTime = rightNow.getTimeInMillis();

        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.readFile();
        ArrayList chatid = propertiesReader.getChatid();

        if (time == 0) {
            return "No prices";
        }
            if (time + parameter < currentTime) {
                result = "Prices delay is" + ":  " + (currentTime - time )/1000 + " secs"
                        + "\n" + "Symbol = "
                        + symbolName;
                TelegramBot telegramBot = new TelegramBot();
                for (int i = 0 ; i< 2 ; i++) {
                    if (chatid.get(i).equals(""))
                        break;
                        else
                            telegramBot.sendMsg((String) chatid.get(i), result);
                }
            }

        return result;
    }

}
