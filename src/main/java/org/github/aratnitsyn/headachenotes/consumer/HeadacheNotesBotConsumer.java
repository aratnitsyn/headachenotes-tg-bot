package org.github.aratnitsyn.headachenotes.consumer;

import lombok.RequiredArgsConstructor;
import org.github.aratnitsyn.headachenotes.message.consumer.MessageConsumer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HeadacheNotesBotConsumer implements LongPollingUpdateConsumer {
    private final MessageConsumer messageConsumer;

    @Override
    public void consume(List<Update> updates) {
        updates.forEach(update -> {
            if (update.hasMessage()) {
                messageConsumer.consume(update.getMessage());
            }
        });
    }
}
