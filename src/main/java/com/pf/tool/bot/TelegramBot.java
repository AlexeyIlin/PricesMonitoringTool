package com.pf.tool.bot;

import com.pf.tool.properties.PropertiesReader;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    private final static Logger logger = Logger.getLogger(TelegramBot.class);
    private PropertiesReader propertiesReader = new PropertiesReader();

    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

        public synchronized void sendMsg(String chatId, String s) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(chatId);
            sendMessage.setText(s);
            try {
                sendMessage(sendMessage);
            } catch (TelegramApiException e) {
                logger.error("Cannot send msg", e);
            }
        }

        public String getBotUsername() {
            propertiesReader.fileReader();
            return propertiesReader.getBotname();
        }

        @Override
        public String getBotToken() {
            propertiesReader.fileReader();
            return propertiesReader.getBottoken();
        }

    }


