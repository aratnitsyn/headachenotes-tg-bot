package org.github.aratnitsyn.headachenotes.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.aop.support.AopUtils.getTargetClass;

@Component
public class StrategyRegistryImpl implements StrategyRegistry {
    private final Map<String, List<Strategy>> customStrategies;

    private final Map<String, Strategy> defaultStrategies;

    public StrategyRegistryImpl(Collection<Strategy> strategies) {
        strategies.forEach(this::validateStrategy);

        this.defaultStrategies = strategies
                .stream()
                .filter(strategy -> getStrategyInfo(strategy).defaultStrategy())
                .collect(Collectors.toMap(strategy -> getStrategyInfo(strategy).code(), Function.identity()));

        this.customStrategies = strategies
                .stream()
                .filter(strategy -> !getStrategyInfo(strategy).defaultStrategy())
                .collect(Collectors.groupingBy(strategy -> getStrategyInfo(strategy).code(), Collectors.toList()));
    }

    @Override
    public <T extends Strategy> T getStrategy(String code, Urn urn, Class<T> expectedClass) {
        List<StrategyRule> strategyRules = Optional.ofNullable(customStrategies.get(code))
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(expectedClass::isInstance)
                .map(expectedClass::cast)
                .flatMap(this::toStrategyInfos)
                .sorted(Comparator.comparing(StrategyRule::getBaseUrn, Comparator.reverseOrder()))
                .toList();

        for (StrategyRule strategyRule : strategyRules) {
            if (strategyRule.getBaseUrn().equals(urn.baseUrn())) {
                return expectedClass.cast(strategyRule.getStrategy());
            }
        }

        return getDefaultStrategyClass(code, expectedClass)
                .orElseThrow(() -> new RuntimeException(String.format(
                        "Error: not found strategy (default). Parameters: code=%s, urn=%s, expectedClass=%s",
                        code, urn, expectedClass
                )));
    }

    private void validateStrategy(Strategy strategy) {
        StrategyInfo strategyInfo = getStrategyInfo(strategy);

        if (strategyInfo == null) {
            throw new IllegalStateException("Strategy must have @StrategyInfo annotation");
        }

        if (!strategyInfo.defaultStrategy() && strategyInfo.conditions().length == 0) {
            throw new IllegalStateException("Strategy is not default, but has not condition");
        }

        if (strategyInfo.defaultStrategy() && strategyInfo.conditions().length != 0) {
            throw new IllegalStateException("Strategy is default, but has conditions");
        }
    }

    private Stream<StrategyRule> toStrategyInfos(Strategy strategy) {
        return Arrays.stream(getStrategyInfo(strategy).conditions())
                .map(str -> new StrategyRule(strategy, str.baseUrn()));
    }

    private StrategyInfo getStrategyInfo(Strategy strategy) {
        return getTargetClass(strategy).getAnnotation(StrategyInfo.class);
    }

    private <T extends Strategy> Optional<T> getDefaultStrategyClass(String code, Class<T> expectedClass) {
        return Optional.ofNullable(defaultStrategies.get(code))
                .filter(expectedClass::isInstance)
                .map(expectedClass::cast);
    }

    @Getter
    @AllArgsConstructor
    private static class StrategyRule {
        private Strategy strategy;
        private String baseUrn;
    }
}
