package org.github.aratnitsyn.headachenotes.strategy;

/**
 * Реестр {@link Strategy стратегий}
 */
public interface StrategyRegistry {
    /**
     * Получить стратегию по её коду, urn
     * @param code код стратегии
     * @param urn urn
     * @param expectedClass ожидаемый класс стратегии
     * @return стратегия
     * @param <T> тип ожидаемого класса стратегии
     */
    <T extends Strategy> T getStrategy(String code, Urn urn, Class<T> expectedClass);
}
