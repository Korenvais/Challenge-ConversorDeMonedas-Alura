package models;

import java.util.Map;

public record CurrencyExchangerRateApi(String base_code,
                                       Map<String, Double> conversion_rates) {
}
