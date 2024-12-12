package org.github.aratnitsyn.headachenotes.message.consumer;

import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface MessageConsumer {
    void consume(Message message);
}
