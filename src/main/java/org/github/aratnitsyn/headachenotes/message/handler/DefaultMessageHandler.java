package org.github.aratnitsyn.headachenotes.message.handler;

import lombok.RequiredArgsConstructor;
import org.github.aratnitsyn.headachenotes.strategy.StrategyInfo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@StrategyInfo(
        code = MessageHandler.STRATEGY_CODE,
        defaultStrategy = true
)
@Component
@RequiredArgsConstructor
public class DefaultMessageHandler implements MessageHandler {
    private final TelegramClient telegramClient;

    @Override
    public void handler(Message object) {
        SendMessage sendMessage = new SendMessage(
                object.getChatId().toString(),
                "otvet"
        );

        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
