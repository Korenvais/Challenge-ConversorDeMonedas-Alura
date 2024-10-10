import apis.CurrencyAPI;
import apis.QueryCurrencyApi;
import models.Exchanger;
import models.Screens;
import utils.OutputFileGenerator;
import utils.Validator;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        Scanner myScanner = new Scanner(System.in);

        CurrencyAPI currentApi = new QueryCurrencyApi();

        Exchanger currencyExchanger = new Exchanger(currentApi);

        List<String> historyOfExchange = new ArrayList<>();
        String outputFileName = "historial_de_conversiones.json";

        boolean invalidOption = false;
        boolean exitProgram = false;

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        //Screens.printCatalog(currencyExchanger.getNameCatalog());
        Screens.screenWelcome();

        while (!exitProgram) {

            if(!invalidOption)
                Screens.screenOption();

            invalidOption = false;

            var selectedOption = myScanner.nextLine();

            switch (selectedOption) {
                case "1":
                    Screens.printCatalog(currencyExchanger.getNameCatalog());
                    break;

                case "2":

                    Screens.screenOriginConvert();
                    var codeOrig = myScanner.nextLine();
                    if(codeOrig.equalsIgnoreCase("salir")) {
                        Screens.screenOption();
                        break;
                    }

                    boolean exitFlag = false;

                    // For getting the origin currency.
                    while (true){
                        codeOrig = codeOrig.toUpperCase();
                        if(!Validator.validCode(codeOrig, currencyExchanger.getNameCatalog())) {
                            Screens.screenInvalidInput();
                            codeOrig = myScanner.nextLine();
                            if(codeOrig.equalsIgnoreCase("salir")) {
                                exitFlag = true;
                                break;
                            }
                        }
                        else
                            break;
                    }

                    if (exitFlag){
                        Screens.screenOption();
                        break;
                    }

                    Screens.screenDestinyConvert();
                    var codeDest = myScanner.nextLine();

                    // For getting the destiny currency.
                    while (true){
                        codeDest = codeDest.toUpperCase();
                        if(!Validator.validCode(codeDest, currencyExchanger.getNameCatalog())) {
                            Screens.screenInvalidInput();
                            codeDest = myScanner.nextLine();
                            if(codeDest.equalsIgnoreCase("salir")) {
                                exitFlag = true;
                                break;
                            }
                        }
                        else
                            break;
                    }

                    if (exitFlag){
                        Screens.screenOption();
                        break;
                    }

                    Screens.screenAmountToConvert();
                    String amountString = myScanner.nextLine();
                    double amount;

                    // For getting the amount to exchange.
                    while (true) {
                        if (Validator.isDouble(amountString) &&
                                Validator.nonNegativeNumber(Double.parseDouble(amountString))){
                            amount = Double.parseDouble(amountString);
                            break;
                        }
                        else {
                            Screens.screenInvalidAmount();
                            amountString = myScanner.nextLine();
                        }
                    }

                    double exchangedValue = currencyExchanger.convertAmount(codeOrig, codeDest, amount);
                    String nomOrigin = currencyExchanger.getNameCatalog().get(codeOrig);
                    String nomDestiny = currencyExchanger.getNameCatalog().get(codeDest);

                    Screens.screenConvertMessage(codeOrig, codeDest, nomOrigin, nomDestiny,
                            numberFormat.format(amount), numberFormat.format(exchangedValue));


                    String timeStamp = LocalDateTime.now().format(formatter);

                    String recordedExchange = String.format("[%s] %s %s (%s) -> %s %s (%s)",
                            timeStamp, numberFormat.format(amount), codeOrig, nomOrigin,
                            numberFormat.format(exchangedValue), codeDest, nomDestiny);

                    historyOfExchange.add(recordedExchange);

                    System.out.println("Presione enter para volver al menu inicial");
                    myScanner.nextLine();

                    break;

                case "3":
                    Screens.screenHistory(historyOfExchange);
                    break;

                case "4":
                    exitProgram = true;

                    try {
                        OutputFileGenerator.saveAsJsonFile(historyOfExchange, outputFileName);
                    } catch (IOException e) {
                        System.out.println("Error al guardar el historial en archivo JSON: " + e.getMessage());
                    }

                    myScanner.close();

                    Screens.screenGoodBye();

                    break;

                default:
                    Screens.screenInvalidOption();
                    invalidOption = true;
            }

        }

        }
    }
