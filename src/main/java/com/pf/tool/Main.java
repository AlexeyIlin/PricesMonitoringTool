package com.pf.tool;

import com.pf.tool.logic.Level2Checker;
import com.pf.tool.logic.Level3Checker;
import com.pf.tool.properties.Mode;
import com.pf.tool.properties.PropertiesReader;
import com.pf.tool.rest.RestService;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main{

    private final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        String token = null;
        long time;
        String result;
        boolean isExpired = false;
        int weekDay, curDay, year, month;
        Date curTime, stime, etime;
        ArrayList<Double> level2price;

        Level3Checker level3Checker = new Level3Checker();
        Level2Checker level2Checker = new Level2Checker();
        PropertiesReader propertiesReader = new PropertiesReader();
        RestService restService = new RestService();
        propertiesReader.fileReader();

            while (true) {
                try {

                Calendar rightNow = Calendar.getInstance();
                curTime = rightNow.getTime();
                weekDay = rightNow.get(Calendar.DAY_OF_WEEK);
                year = rightNow.get(Calendar.YEAR);
                month = rightNow.get(Calendar.MONTH ) + 1;
                curDay = rightNow.get(Calendar.DAY_OF_MONTH);

                long millis = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy.MM.dd HH:mm");

                stime = sdf.parse(year + "." + month + "." + curDay + " " + propertiesReader.getStarttime());
                etime = sdf.parse(year + "." + month + "." + curDay + " " + propertiesReader.getEndtime());

                if (curTime.after(stime)
                        && curTime.before(etime)
                        && Calendar.MONDAY <= weekDay
                        && weekDay <= Calendar.FRIDAY ) {

                    if (token != null)
                        isExpired = restService.getIsExpired(propertiesReader.getUrl(), token);
                        else
                            token = restService.getToken(propertiesReader.getUrl(), propertiesReader.getLogin(), propertiesReader.getPass());
                        if (isExpired)
                            token = restService.getToken(propertiesReader.getUrl(), propertiesReader.getLogin(), propertiesReader.getPass());


                    for (int i = 0 ; i < propertiesReader.getSymbol().size() ; i ++) {
                        if (propertiesReader.getMode() == Mode.Level3.getMode() || propertiesReader.getMode() == Mode.Level2and3.getMode()) {
                            time = restService.getTime(propertiesReader.getUrl(), token, propertiesReader.getSymbol().get(i), propertiesReader.getRoute().get(i));
                            result = level3Checker.checkTime(time, propertiesReader.getParameter().get(i),propertiesReader.getSymbolName().get(i));
                            if (result != null) logger.error(result);
                            else logger.debug("Ok");
                        }

                        if (propertiesReader.getMode() == Mode.Level2.getMode() || propertiesReader.getMode() == Mode.Level2and3.getMode()){
                            level2price = restService.getLevel2(propertiesReader.getUrl(), token, propertiesReader.getSymbol().get(i), propertiesReader.getRoute().get(i));
                            result = level2Checker.checkLevel2(level2price,propertiesReader.getSymbolName().get(i));
                            if (result != null) logger.error(result);
                            else logger.debug("Ok");
                        }

                    }
                }
                    Thread.sleep(propertiesReader.getParameter().get(0) - millis % propertiesReader.getParameter().get(0));
                } catch (Exception e) {
                    logger.error("Error", e);
            }
        }

    }
}
