package ro.msg.learning.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.msg.learning.shop.strategies.LocationSelectionStrategy;
import ro.msg.learning.shop.strategies.MostAbundantLocationStrategy;
import ro.msg.learning.shop.strategies.SingleLocationStrategy;
import ro.msg.learning.shop.utils.StrategyType;

@Configuration
@RequiredArgsConstructor
public class LocationSelectionStrategyConfig {

    @Bean
    public LocationSelectionStrategy chooseLocationSelectionStrategy(
            @Value("${locationSelectionStrategy}") StrategyType strategy) {

        if (strategy == StrategyType.SINGLE) {
            return new SingleLocationStrategy();
        } else if (strategy == StrategyType.MOST_ABUNDANT) {
            return new MostAbundantLocationStrategy();
        }
        return null;
    }

}
