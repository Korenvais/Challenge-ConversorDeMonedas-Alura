package apis;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import models.Currency;
import models.CurrencyExchangerRateApi;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.TreeMap;

public class QueryCurrencyApi implements CurrencyAPI{

    //Note: Replace the value of "API_KEY" with your own API KEY
    private final String API_KEY = "REPLACE_WITH_YOUR_API_KEY";

    private final String DOMAIN_URL = "https://v6.exchangerate-api.com/v6/";

    public Currency seachCurrency(String currencyCode) {

        String queryUrl = this.DOMAIN_URL + this.API_KEY + "/latest/" + currencyCode;

        URI url = URI.create(queryUrl);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            CurrencyExchangerRateApi currencyRecord = new Gson().fromJson(response.body(),
                                                                        CurrencyExchangerRateApi.class);

            return new Currency(currencyRecord);
        } catch (Exception e) {
            throw new RuntimeException("No se encontró esa la moneda!");
        }
    }

    public Map<String, String> loadCurrencyCatalog(){

        String queryUrl = this.DOMAIN_URL + this.API_KEY + "/codes";

        URI url = URI.create(queryUrl);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(url).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonResponse = new Gson().fromJson(response.body(), JsonObject.class);
            JsonArray listOfCodes = jsonResponse.getAsJsonArray("supported_codes");

            Map<String, String> currencyCatalog = new TreeMap<>();
            for (var item: listOfCodes) {
                JsonArray codePair = item.getAsJsonArray();
                String currencyCode = codePair.get(0).getAsString();
                String currencyName = codePair.get(1).getAsString();
                currencyCatalog.put(currencyCode, currencyName);
            }

            return currencyCatalog;
        } catch (Exception e) {
            System.out.println("Error al cargar el catalogo de monedas: " + e.getMessage());
            return null;
        }

    }


}
