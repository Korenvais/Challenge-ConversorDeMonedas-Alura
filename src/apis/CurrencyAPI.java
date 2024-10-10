package apis;

import models.Currency;
import java.util.Map;

public interface CurrencyAPI {

    Currency seachCurrency(String currencyCode);
    Map<String, String> loadCurrencyCatalog();
}
