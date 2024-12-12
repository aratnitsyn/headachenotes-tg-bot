package org.github.aratnitsyn.headachenotes.configuration;

import org.github.aratnitsyn.headachenotes.consumer.HeadacheNotesBotConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.starter.TelegramBotStarterConfiguration;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@Import({TelegramBotStarterConfiguration.class})
public class ApplicationConfiguration {
    @Value("${headachenotes.telegram-bot.token}")
    private String botToken;

    @Bean
    TelegramClient telegramClient() {
        return new OkHttpTelegramClient(botToken);
    }

    @Bean
    @ConditionalOnBean(HeadacheNotesBotConsumer.class)
    SpringLongPollingBot headacheNotesLongPollingBot(HeadacheNotesBotConsumer botConsumer) {
        return new HeadacheNotesLongPollingBot(botToken, botConsumer);
    }
}
