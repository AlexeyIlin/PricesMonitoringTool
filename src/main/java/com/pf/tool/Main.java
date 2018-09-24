package com.pf.tool;

import com.pf.tool.logic.PricesChecker;
import com.pf.tool.properties.PropertiesReader;
import com.pf.tool.rest.RestService;
import org.apache.log4j.Logger;
import java.util.Calendar;

public class Main{

    private final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        String token = null;
        long time;
        String result;
        boolean isExpired;
        int hour, day;

        PricesChecker pricesChecker = new PricesChecker();
        PropertiesReader propertiesReader = new PropertiesReader();
        RestService restService = new RestService();

        propertiesReader.fileReader();
        Calendar rightNow = Calendar.getInstance();


            while (true) {
                try {
                hour = rightNow.get(Calendar.HOUR_OF_DAY);
                day = rightNow.get(Calendar.DAY_OF_WEEK);
                long millis = System.currentTimeMillis();


                if ((propertiesReader.getStarttime() <= hour
                        && hour <= propertiesReader.getEndtime())
                        && Calendar.MONDAY <= day
                        && day <= Calendar.FRIDAY ) {

                    isExpired = restService.getIsExpired(propertiesReader.getUrl(), token);

                    if (token == null || isExpired) {
                        token = restService.getToken(propertiesReader.getUrl(), propertiesReader.getLogin(), propertiesReader.getPass());
                    }

                    time = restService.getTime(propertiesReader.getUrl(), token, propertiesReader.getSymbol(), propertiesReader.getRoute());
                    result = pricesChecker.checkTime(time, propertiesReader.getParameter());
                    if (result != null) logger.error(result);
                        else logger.debug("Ok");
                }
                    Thread.sleep(propertiesReader.getParameter() - millis % propertiesReader.getParameter());
                } catch (Exception e) {
                    logger.error("Error", e);
            }
        }

    }
}
