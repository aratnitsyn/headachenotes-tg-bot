package org.github.aratnitsyn.headachenotes.configuration;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@RequiredArgsConstructor
public class HeadacheNotesLongPollingBot implements SpringLongPollingBot {
    private final String botToken;
    private final LongPollingUpdateConsumer longPollingUpdateConsumer;

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this.longPollingUpdateConsumer;
    }
}
