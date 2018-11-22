package com.pf.tool;

import com.pf.tool.logic.PricesChecker;
import com.pf.tool.properties.PropertiesReader;
import com.pf.tool.rest.RestService;
import org.apache.log4j.Logger;
import java.util.Calendar;
import java.util.Date;

public class Main{

    private final static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        String token = null;
        long time;
        String result;
        boolean isExpired = false;
        int day;
        Date curTime;

        PricesChecker pricesChecker = new PricesChecker();
        PropertiesReader propertiesReader = new PropertiesReader();
        RestService restService = new RestService();

        propertiesReader.fileReader();

            while (true) {
                try {

                Calendar rightNow = Calendar.getInstance();
                curTime = rightNow.getTime();
                day = rightNow.get(Calendar.DAY_OF_WEEK);
                long millis = System.currentTimeMillis();


                if (curTime.after(propertiesReader.getStarttime())
                        && curTime.before(propertiesReader.getEndtime())
                        && Calendar.MONDAY <= day
                        && day <= Calendar.FRIDAY ) {

                    if (token != null)
                        isExpired = restService.getIsExpired(propertiesReader.getUrl(), token);
                        else
                            token = restService.getToken(propertiesReader.getUrl(), propertiesReader.getLogin(), propertiesReader.getPass());
                        if (isExpired)
                            token = restService.getToken(propertiesReader.getUrl(), propertiesReader.getLogin(), propertiesReader.getPass());


                    for (int i = 0 ; i < propertiesReader.getSymbol().size() ; i ++) {
                        time = restService.getTime(propertiesReader.getUrl(), token, propertiesReader.getSymbol().get(i), propertiesReader.getRoute().get(i));
                        result = pricesChecker.checkTime(time, propertiesReader.getParameter().get(i));
                        if (result != null) logger.error(result);
                        else logger.debug("Ok");
                    }
                }
                    Thread.sleep(propertiesReader.getParameter().get(0) - millis % propertiesReader.getParameter().get(0));
                } catch (Exception e) {
                    logger.error("Error", e);
            }
        }

    }
}
