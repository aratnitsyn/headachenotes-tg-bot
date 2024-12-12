package org.github.aratnitsyn.headachenotes.handler;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;

/**
 * Обработка объекта от Бота
 *
 * @param <T> тип обрабатываемого объекта от Бота
 */
public interface Handler<T extends BotApiObject> {
    void handler(T object);
}
