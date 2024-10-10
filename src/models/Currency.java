package models;

import java.time.LocalDateTime;
import java.util.Map;

public class Currency {

    private final String currencyCode;
    private final Map<String, Double> ratesOfExchange;

    private final LocalDateTime timeStamp;

    public Currency(CurrencyExchangerRateApi currencyRecord) {
        this.currencyCode = currencyRecord.base_code();
        this.ratesOfExchange = currencyRecord.conversion_rates();
        this.timeStamp = LocalDateTime.now();
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public Map<String, Double> getRatesOfExchange() {
        return this.ratesOfExchange;
    }


    public Double convertAmountTo(String code, Double amountToConvert) {
        return this.ratesOfExchange.get(code) * amountToConvert;
    }
}
