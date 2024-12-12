package org.github.aratnitsyn.headachenotes.strategy;

import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
public class Urn {
    /**
     * Объект Бота
     */
    private String object;

    /**
     * Тип объекта Бота
     */
    private String type;

    /**
     * Получить базовый Urn вида {@link Urn#object}:{@link Urn#type}
     *
     * @return базовый Urn
     */
    public String baseUrn() {
        return Stream.of(object, type)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(":"));
    }

    @Override
    public String toString() {
        return baseUrn();
    }
}
