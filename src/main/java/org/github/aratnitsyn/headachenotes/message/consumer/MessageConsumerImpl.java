package org.github.aratnitsyn.headachenotes.message.consumer;

import lombok.RequiredArgsConstructor;
import org.github.aratnitsyn.headachenotes.message.handler.MessageHandler;
import org.github.aratnitsyn.headachenotes.strategy.StrategyRegistry;
import org.github.aratnitsyn.headachenotes.strategy.Urn;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class MessageConsumerImpl implements MessageConsumer {
    private final StrategyRegistry strategyRegistry;

    @Override
    public void consume(Message message) {
        if (message.hasText()) {
            Urn urn = formUrn(message.getText());
            strategyRegistry.getStrategy(MessageHandler.STRATEGY_CODE, urn, MessageHandler.class).handler(message);
        }
    }

    private Urn formUrn(String text) {
        String typeText = MessageHandler.STRATEGY_TYPE + ":" + text;
        return Urn.builder().object(MessageHandler.STRATEGY_OBJECT).type(typeText).build();
    }
}
