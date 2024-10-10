package models;

import java.util.List;
import java.util.Map;

public class Screens {

    public static void printCatalog(Map<String, String> nameCatalog) {

        screenSeparation();
        System.out.println("Monedas válidas para realizar conversiones (codigo - descripción):");
        for (String code : nameCatalog.keySet()) {
            System.out.printf("%s - %s\n", code, nameCatalog.get(code));
        }
    }

    public static void screenSeparation() {
        System.out.println("---------------------------------------------------------------------------");
    }

    public static void screenWelcome() {
        System.out.println("Bienvenido a Alura Challenge Exchange");
        System.out.println();
        System.out.println();
    }

    public static void screenOption() {

        screenSeparation();
        System.out.println("Por favor escriba la opción deseada: ");
        System.out.println();
        System.out.println("1 - Ver monedas disponibles.");
        System.out.println("2 - Realizar conversion.");
        System.out.println("3 - Ver historial de conversiones.");
        System.out.println("4 - Salir.");

    }

    public static void screenInvalidInput() {

        System.out.println();
        System.out.println("Ingreso un valor no valido. Por favor ingrese un valor valido o escriba 'salir' para volver al menu inicial: ");
        System.out.println();

    }

    public static void screenInvalidOption() {
        System.out.println();
        System.out.println("Opción no valida. Por favor ingrese una opción valido. ");
        System.out.println();
    }

    public static void screenOriginConvert() {

        screenSeparation();
        System.out.println("Escribe el codigo de la moneda que deseas convertir o 'salir' para volver al menu inicial: ");
        System.out.println();

    }

    public static void screenDestinyConvert() {

        System.out.println();
        System.out.println("Escribe el codigo de la moneda a la que deseas convertir: ");
        System.out.println();

    }

    public static void screenAmountToConvert() {

        System.out.println();
        System.out.println("Escribe el monto que deseas convertir: ");
        System.out.println();

    }

    public static void screenInvalidAmount() {

        System.out.println();
        System.out.println("El monto ingresado no es valido. Recuerde que debe ingresar un valor numerico mayor o igual a cero: ");
        System.out.println();

    }

    public static void screenConvertMessage(String codeOrig, String codeDest, String nomOrg,
                                            String nomDest, String amountOrg, String amountDes) {

        System.out.println();
        System.out.printf("La cantidad de %s %s (%s) equivale a %s %s (%s)\n",
                amountOrg, codeOrig, nomOrg,
                amountDes, codeDest, nomDest);
        System.out.println();

    }

    public static void screenHistory(List<String> historyOfExchange){
        screenSeparation();

        for (String record : historyOfExchange){
            System.out.println(record);
        }
    }

    public static void screenGoodBye(){
        System.out.println();
        System.out.println("¡Gracias por usar nuestro conversor de monedas!");
        System.out.println();
    }

}
