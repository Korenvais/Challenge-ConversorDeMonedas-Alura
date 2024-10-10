package models;

import apis.CurrencyAPI;

import java.util.Map;
import java.util.TreeMap;

public class Exchanger {

    private final Map<String, String> nameCatalog;

    // This is important if we want to reduce the calls to the API
    // due requesting the same currency
    private final Map<String, Currency> ratesPerCurrency;

    private final CurrencyAPI myCurrencyAPI;
    private boolean fullCatalog = false;

    public Exchanger(CurrencyAPI myCurrencyAPI) {
        this.nameCatalog = new TreeMap<>();
        this.ratesPerCurrency = new TreeMap<>();
        this.myCurrencyAPI = myCurrencyAPI;

        this.updateNameCatalog();

    }

    public Map<String, String> getNameCatalog() {
        return nameCatalog;
    }

    public boolean isFullCatalog() {
        return fullCatalog;
    }

    public void updateNameCatalog(){

        // This is to avoid unnecessary calls to the API
        if(!this.isFullCatalog()) {
            Map<String, String> completeCatalog = this.myCurrencyAPI.loadCurrencyCatalog();
            this.nameCatalog.putAll(completeCatalog);
            this.fullCatalog = true;
        }
    }

    private void updateRatesPerCurrency(Currency newCurrency) {
        this.ratesPerCurrency.put(newCurrency.getCurrencyCode(), newCurrency);
    }

    public Currency getCurrency(String code) {
        Currency output = this.ratesPerCurrency.get(code);

        // Check if we have the rates in the system
        if (output == null) {

            //Search the rates in the API and update the ones in the system
            output = this.getCurrencyFromAPI(code);
            this.updateRatesPerCurrency(output);
        }
        return output;
    }

    // Main Bussines function
    public Double convertAmount(String codeOrigen, String codeDestiny, Double amountOrigin) {
        Currency originCurrency = this.getCurrency(codeOrigen);
        return originCurrency.convertAmountTo(codeDestiny, amountOrigin);
    }

    private Currency getCurrencyFromAPI(String code) {
        return this.myCurrencyAPI.seachCurrency(code);
    }
}
