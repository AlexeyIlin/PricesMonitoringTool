package com.pf.tool.logic;

import com.pf.tool.bot.TelegramBot;
import com.pf.tool.properties.PropertiesReader;

import java.util.ArrayList;

public class Level2Checker {

    public String checkLevel2(ArrayList<Double> level2price, String symbolName){

        String result = null;
        double bid = level2price.get(0);
        double ask = level2price.get(1);

        PropertiesReader propertiesReader = new PropertiesReader();
        propertiesReader.fileReader();
        ArrayList chatid = propertiesReader.getChatid();

        if ((ask - bid ) < 0 ){

            result = "Wrong BID/ASK in level2. "
                    + "\n" + "Bid price: " + bid + "  Ask price: " + ask
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
