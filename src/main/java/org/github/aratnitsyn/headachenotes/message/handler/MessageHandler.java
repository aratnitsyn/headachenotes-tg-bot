package org.github.aratnitsyn.headachenotes.message.handler;

import org.github.aratnitsyn.headachenotes.handler.Handler;
import org.github.aratnitsyn.headachenotes.strategy.Strategy;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface MessageHandler extends Handler<Message>, Strategy {
    String STRATEGY_CODE = "MESSAGE_HANDLER_STRATEGY";
    String STRATEGY_OBJECT = "message";
    String STRATEGY_TYPE = "text";
}
